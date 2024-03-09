package com.enigma.wmbapi.services.impl;

import com.enigma.wmbapi.dto.request.NewMenuRequest;
import com.enigma.wmbapi.entity.Menu;
import com.enigma.wmbapi.services.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
    @Override
    public Menu create(NewMenuRequest request) {
        return null;
    }

    @Override
    public Menu getById(String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
