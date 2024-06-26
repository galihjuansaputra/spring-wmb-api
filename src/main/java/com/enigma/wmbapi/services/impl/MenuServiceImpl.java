package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.constant.APIUrl;
import com.enigma.wmbapi.constant.ResponseMessage;
import com.enigma.wmbapi.dto.request.NewMenuRequest;
import com.enigma.wmbapi.dto.request.SearchMenuRequest;
import com.enigma.wmbapi.dto.request.UpdateMenuRequest;
import com.enigma.wmbapi.dto.response.CustomerResponse;
import com.enigma.wmbapi.dto.response.ImageResponse;
import com.enigma.wmbapi.dto.response.MenuResponse;
import com.enigma.wmbapi.entity.Customer;
import com.enigma.wmbapi.entity.Image;
import com.enigma.wmbapi.entity.Menu;
import com.enigma.wmbapi.repository.CustomerRepository;
import com.enigma.wmbapi.repository.MenuRepository;
import com.enigma.wmbapi.services.ImageService;
import com.enigma.wmbapi.services.MenuService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ImageService imageService;

    @Override
    public MenuResponse create(NewMenuRequest request) {
        if (request.getImage().isEmpty()) throw new ConstraintViolationException("image is required", null);
        Image image = imageService.create(request.getImage());
        Menu menu = Menu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .image(image)
                .build();
        menuRepository.saveAndFlush(menu);
        return convertMenuToMenuResponse(menu);
    }

    @Override
    public Menu getById(String id) {
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,ResponseMessage.ERROR_NOT_FOUND));
    }

    @Override
    public Page<MenuResponse> getAll(SearchMenuRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);

        if (request.getName() != null) {
            Page<MenuResponse> result = menuRepository.findAllByNameContainingIgnoreCase(request.getName(), pageable);

            if (result.getTotalPages() == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
            if (request.getPage() > result.getTotalPages()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.ERROR_PAGE_LIMIT);

            return result;
        }

        Page<Menu> menu = menuRepository.findAll(pageable);

        List<MenuResponse> menuResponses = menu.getContent()
                .stream()
                .map(this::convertMenuToMenuResponse)
                .toList();

        Page<MenuResponse> result = new PageImpl<>(menuResponses, pageable, menu.getTotalElements());
        if (result.getTotalPages() == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_EMPTY_DATA);
        if (request.getPage() > result.getTotalPages()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.ERROR_PAGE_LIMIT);

        return result;
    }

    @Override
    public Menu update(Menu menu) {
        return menuRepository.saveAndFlush(menu);
    }

    @Override
    public MenuResponse update(UpdateMenuRequest request) {
        Menu currentMenu = getById(request.getId());
        String currentImageId = currentMenu.getImage().getId();

        if(request.getImage() != null){
            Image image = imageService.create(request.getImage());
            currentMenu.setImage(image);
            imageService.deleteById(currentImageId);
        }

        currentMenu.setName(request.getName());
        currentMenu.setPrice(request.getPrice());
        menuRepository.saveAndFlush(currentMenu);

        return convertMenuToMenuResponse(currentMenu);
    }

    @Override
    public void delete(String id) {
        Menu currentMenu = getById(id);
        menuRepository.delete(currentMenu);
    }

    public MenuResponse convertMenuToMenuResponse(Menu menu) {
        return  MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .image(ImageResponse.builder()
                        .url(APIUrl.PRODUCT_IMAGE_DOWNLOAD_API + menu.getImage().getId())
                        .name(menu.getImage().getName())
                        .build())
                .build();
    }
}
