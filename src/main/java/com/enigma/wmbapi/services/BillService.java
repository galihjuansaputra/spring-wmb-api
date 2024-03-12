package com.enigma.wmbapi.services;


import com.enigma.wmbapi.dto.request.BillRequest;
import com.enigma.wmbapi.dto.request.SearchBillRequest;
import com.enigma.wmbapi.dto.request.UpdateBillStatusRequest;
import com.enigma.wmbapi.dto.response.BillResponse;
import com.enigma.wmbapi.dto.response.ReportResponse;
import org.springframework.data.domain.Page;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface BillService {
    BillResponse create(BillRequest request);

    Page<BillResponse> getAll(SearchBillRequest request);

    void updateStatus(UpdateBillStatusRequest request);

    List<ReportResponse> getAllReport();

    ByteArrayInputStream getReportDownloaded() throws IOException;
}
