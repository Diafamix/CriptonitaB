package com.cryptonita.app.core.utils;

import com.cryptonita.app.dto.data.response.HistoryResponseDTO;
import com.cryptonita.app.dto.integration.HistoryInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.Cell;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;




@Service
@Data
public class ExcelGenerator {


    private List<HistoryResponseDTO> historyResponseDTO;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;


    public ExcelGenerator(List < HistoryResponseDTO > historyResponseDTO) {
        this.historyResponseDTO = historyResponseDTO;
        workbook = new XSSFWorkbook();
    }

        private void WriteHeader(){

        sheet = workbook.createSheet("History");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Date", style);
        createCell(row, 2, "Destiny", style);
        createCell(row, 3, "Origin", style);
        createCell(row, 4, "Portfolio", style);
        createCell(row, 5, "Quantity", style);
        createCell(row, 6, "User_ID", style);

    }


    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (HistoryResponseDTO record : historyResponseDTO) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getId(), style);
            createCell(row, columnCount++, record.getDate(), style);
            createCell(row, columnCount++, record.getDestiny(), style);
            createCell(row, columnCount++, record.getOrigin(), style);
            createCell(row, columnCount++, record.getPortfolio(), style);
            createCell(row, columnCount++, record.getQuantity(), style);
            createCell(row, columnCount++, record.getUser(), style);

        }
    }

    public void generateExcelFile(HttpServletResponse response) throws IOException {
        WriteHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

}
