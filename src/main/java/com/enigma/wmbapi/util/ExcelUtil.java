package com.enigma.wmbapi.util;

import com.enigma.wmbapi.dto.response.ReportResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {

    public static String[] HEADER = {"id", "customer", "transDate", "tableId", "transType", "billDetails", "paymentId", "paymentStatus"};
    public static String SHEET_NAME = "sheetForBillsData";

    public static ByteArrayInputStream dataToExcel(List<ReportResponse> reportResponseList){

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET_NAME);
            Row row = sheet.createRow(0);

            for (int i = 0; i < HEADER.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADER[i]);
            }

            int rowIndex = 1;
            for (ReportResponse r : reportResponseList) {
                Row row1 = sheet.createRow(rowIndex);
                rowIndex++;
                row1.createCell(0).setCellValue(r.getId());
                row1.createCell(1).setCellValue(r.getCustomer());
                row1.createCell(2).setCellValue(r.getTransDate());
                row1.createCell(3).setCellValue(r.getTableId());
                row1.createCell(4).setCellValue(r.getTransType());
                row1.createCell(5).setCellValue(r.getBillDetails().toString());
                row1.createCell(6).setCellValue(r.getPaymentId());
                row1.createCell(7).setCellValue(r.getPaymentStatus());
            }

            workbook.write(byteArrayOutputStream);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
