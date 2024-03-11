package com.enigma.wmbapi.services;

import com.enigma.wmbapi.dto.request.NewMenuRequest;
import com.enigma.wmbapi.dto.request.SearchMenuRequest;
import com.enigma.wmbapi.dto.request.UpdateMenuRequest;
import com.enigma.wmbapi.dto.response.MenuResponse;
import com.enigma.wmbapi.entity.Menu;
import org.springframework.data.domain.Page;

public interface MenuService {

    Menu create(NewMenuRequest request);
    Menu getById(String id);

    Page<MenuResponse> getAll(SearchMenuRequest request);

    Menu update(Menu menu);

    MenuResponse update(UpdateMenuRequest request);
    void delete(String id);
}
