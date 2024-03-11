package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.constant.ResponseMessage;
import com.enigma.wmbapi.dto.request.NewMenuRequest;
import com.enigma.wmbapi.dto.request.SearchMenuRequest;
import com.enigma.wmbapi.dto.response.CustomerResponse;
import com.enigma.wmbapi.dto.response.MenuResponse;
import com.enigma.wmbapi.entity.Customer;
import com.enigma.wmbapi.entity.Menu;
import com.enigma.wmbapi.repository.CustomerRepository;
import com.enigma.wmbapi.repository.MenuRepository;
import com.enigma.wmbapi.services.MenuService;
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

    @Override
    public Menu create(NewMenuRequest request) {
        Menu menu = Menu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
        return menuRepository.saveAndFlush(menu);
    }

    @Override
    public Menu getById(String id) {
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"id not found"));
    }

    @Override
    public Page<MenuResponse> getAll(SearchMenuRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);

        if (request.getName() != null) {
            Page<MenuResponse> result = menuRepository.findAllByNameContainingIgnoreCase(request.getName(), pageable);

            if (result.getTotalPages() == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
            if (request.getPage() > result.getTotalPages()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page exceeding limit");

            return result;
        }

        Page<Menu> menu = menuRepository.findAll(pageable);

        List<MenuResponse> menuResponses = menu.getContent()
                .stream()
                .map(this::convertMenuToMenuResponse)
                .toList();

        Page<MenuResponse> result = new PageImpl<>(menuResponses, pageable, menu.getTotalElements());

        if (request.getPage() > result.getTotalPages()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page exceeding limit");

        return result;
    }

    @Override
    public Menu update(Menu menu) {
        return menuRepository.saveAndFlush(menu);
    }

    @Override
    public void delete(String id) {
        Menu currentMenu = getById(id);
        menuRepository.delete(currentMenu);
    }

    private MenuResponse convertMenuToMenuResponse(Menu menu) {
        return  MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .build();
    }
}
