package com.example.medcheckb8.db.utill;

import com.example.medcheckb8.db.dto.response.DoctorExportResponse;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class ExportToExcel {
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<DoctorExportResponse> doctorList;

    public ExportToExcel(List<DoctorExportResponse> doctorList) {
        this.doctorList = doctorList;
        workbook = new XSSFWorkbook();
    }

    private void createCells(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof LocalDateTime) {
            cell.setCellValue((LocalDateTime) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void createHeaderRow() {
        sheet = workbook.createSheet("Doctor Information");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCells(row, 0, "Doctor Information", style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
        font.setFontHeightInPoints((short) 10);

        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCells(row, 0, "ID", style);
        createCells(row, 1, "First Name", style);
        createCells(row, 2, "Last Name", style);
        createCells(row, 3, "Position", style);
        createCells(row, 4, "Date Of Start", style);
        createCells(row, 5, "Date Of Finish", style);
        createCells(row, 6, "Date", style);
        createCells(row, 7, "Time From", style);
        createCells(row, 8, "Time To", style);

    }

    private void writeDoctorData() {
        int rowCount = 2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (DoctorExportResponse doctor : doctorList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCells(row, columnCount++, doctor.id(), style);
            createCells(row, columnCount++, doctor.firstName(), style);
            createCells(row, columnCount++, doctor.lastName(), style);
            createCells(row, columnCount++, doctor.position(), style);
            createCells(row, columnCount++, doctor.dateOfStart().toString(), style);
            createCells(row, columnCount++, doctor.dateOfFinish().toString(), style);
            createCells(row, columnCount++, doctor.date().toString(), style);

            Map<LocalTime, LocalTime> timeMap = doctor.timeFromAndTimeTo();
            int timeRow = 8;
            for (Map.Entry<LocalTime, LocalTime> timeEntry : timeMap.entrySet()) {
                createCells(row, timeRow++, timeEntry.getKey().toString(), style);
                createCells(row, timeRow++, timeEntry.getValue().toString(), style);
            }
        }
    }

    public void exportDataToExcel(HttpServletResponse response) throws IOException {
        createHeaderRow();
        writeDoctorData();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
