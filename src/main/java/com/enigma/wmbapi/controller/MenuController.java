package com.enigma.wmbapi.controller;

import com.enigma.wmbapi.constant.APIUrl;
import com.enigma.wmbapi.constant.ResponseMessage;
import com.enigma.wmbapi.dto.request.SearchMenuRequest;
import com.enigma.wmbapi.dto.response.CommonResponse;
import com.enigma.wmbapi.dto.response.MenuResponse;
import com.enigma.wmbapi.dto.response.PagingResponse;
import com.enigma.wmbapi.services.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.MENU_API)
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<MenuResponse>>> getAllMenu(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        SearchMenuRequest request = SearchMenuRequest.builder()
                .name(name)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();

        Page<MenuResponse> menus = menuService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(menus.getTotalPages())
                .totalElement(menus.getTotalElements())
                .page(menus.getPageable().getPageNumber() + 1)
                .size(menus.getPageable().getPageSize())
                .hasNext(menus.hasNext())
                .hasPrevious(menus.hasPrevious())
                .build();

        CommonResponse<List<MenuResponse>> response = CommonResponse.<List<MenuResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(menus.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}