package com.enigma.wmbapi.controller;

import com.enigma.wmbapi.constant.APIUrl;
import com.enigma.wmbapi.constant.ResponseMessage;
import com.enigma.wmbapi.dto.request.BillRequest;
import com.enigma.wmbapi.dto.request.SearchBillRequest;
import com.enigma.wmbapi.dto.request.UpdateBillStatusRequest;
import com.enigma.wmbapi.dto.response.BillResponse;
import com.enigma.wmbapi.dto.response.CommonResponse;
import com.enigma.wmbapi.dto.response.PagingResponse;
import com.enigma.wmbapi.dto.response.ReportResponse;
import com.enigma.wmbapi.services.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.BILL_API)
public class BillController {
    private final BillService billService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<BillResponse>> createNewBill(@RequestBody BillRequest request) {
        BillResponse bill = billService.create(request);
        CommonResponse<BillResponse> response = CommonResponse.<BillResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("successfully save data")
                .data(bill)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<BillResponse>>> getAllBill(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        SearchBillRequest request = SearchBillRequest.builder()
                .page(page)
                .size(size)
                .build();
        Page<BillResponse> transactions = billService.getAll(request);
        PagingResponse paging = PagingResponse.builder()
                .page(transactions.getPageable().getPageNumber() + 1)
                .size(transactions.getPageable().getPageSize())
                .totalPages(transactions.getTotalPages())
                .totalElement(transactions.getTotalElements())
                .hasNext(transactions.hasNext())
                .hasPrevious(transactions.hasPrevious())
                .build();
        CommonResponse<List<BillResponse>> response = CommonResponse.<List<BillResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(transactions.getContent())
                .paging(paging)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/status")
    public ResponseEntity<CommonResponse<?>> updateStatus(@RequestBody Map<String, Object> request) {
        UpdateBillStatusRequest updateTransactionStatusRequest = UpdateBillStatusRequest.builder()
                .orderId(request.get("order_id").toString())
                .billStatus(request.get("transaction_status").toString())
                .build();
        billService.updateStatus(updateTransactionStatusRequest);
        return ResponseEntity.ok(CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .build());
    }

    @GetMapping(path = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReportResponse> billReport() {
        return billService.getAllReport();
    }

    @GetMapping(path = "/download")
    public ResponseEntity<InputStreamResource> download() throws IOException {
        String fileName = "bill_reports.xlsx";

        ByteArrayInputStream inputStream = billService.getReportDownloaded();
        InputStreamResource response = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(response);
    }


}
