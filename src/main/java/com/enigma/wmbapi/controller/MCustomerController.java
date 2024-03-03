package com.enigma.wmbapi.controller;

import com.enigma.wmbapi.constant.APIUrl;
import com.enigma.wmbapi.dto.request.NewMCustomerRequest;
import com.enigma.wmbapi.dto.request.SearchMCustomerRequest;
import com.enigma.wmbapi.dto.response.CommonResponse;
import com.enigma.wmbapi.dto.response.PagingResponse;
import com.enigma.wmbapi.entity.MCustomer;
import com.enigma.wmbapi.services.MCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class MCustomerController {

    private final MCustomerService mCustomerService;

    @PostMapping
    public ResponseEntity<CommonResponse<MCustomer>> createNewCustomer(@RequestBody NewMCustomerRequest request) {
        MCustomer customer = mCustomerService.create(request);
        CommonResponse<MCustomer> response = CommonResponse.<MCustomer>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully create new customer")
                .data(customer)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<MCustomer>>> getAllCustomer(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        SearchMCustomerRequest request = SearchMCustomerRequest.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();

        Page<MCustomer> customers = mCustomerService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(customers.getTotalPages())
                .totalElement(customers.getTotalElements())
                .page(customers.getPageable().getPageNumber() + 1)
                .size(customers.getPageable().getPageSize())
                .hasNext(customers.hasNext())
                .hasPrevious(customers.hasPrevious())
                .build();

        CommonResponse<List<MCustomer>> response = CommonResponse.<List<MCustomer>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success Get All Product")
                .data(customers.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<MCustomer> getCustomerById(@PathVariable String id){
        MCustomer customer = mCustomerService.getById(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping
    public ResponseEntity<MCustomer> updateCustomer(@RequestBody MCustomer customer){
        MCustomer newCustomer = mCustomerService.update(customer);
        return ResponseEntity.ok(newCustomer);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id){
        mCustomerService.delete(id);
        return ResponseEntity.ok("Deleted.");
    }

}