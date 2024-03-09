package com.enigma.wmbapi.services;

import com.enigma.wmbapi.dto.request.NewMenuRequest;
import com.enigma.wmbapi.entity.Menu;

public interface MenuService {

    Menu create(NewMenuRequest request);
    Menu getById(String id);
    void delete(String id);
}
