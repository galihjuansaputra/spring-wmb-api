package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.dto.request.NewMenuRequest;
import com.enigma.wmbapi.entity.Menu;
import com.enigma.wmbapi.repository.CustomerRepository;
import com.enigma.wmbapi.repository.MenuRepository;
import com.enigma.wmbapi.services.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public Menu update(Menu menu) {
        return menuRepository.saveAndFlush(menu);
    }

    @Override
    public void delete(String id) {
        Menu currentMenu = getById(id);
        menuRepository.delete(currentMenu);
    }
}
