package com.example.demo.excel;

import com.example.demo.entity.Person;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import java.util.List;
import java.util.Map;

public class PersonDataExcelExport extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, org.apache.poi.ss.usermodel.Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // define excel file name to be exported
        response.addHeader("Content-Disposition", "attachment;fileName=PersonData.xlsx");

        // read data provided by controller
        @SuppressWarnings("unchecked")
        List<Person> list = (List<Person>) model.get("list");

        // create one sheet
        Sheet sheet = workbook.createSheet("Person");

        // create row0 as a header
        Row row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("ID");
        row0.createCell(1).setCellValue("FIRST NAME");
        row0.createCell(2).setCellValue("LAST NAME");
        row0.createCell(3).setCellValue("NATIONAL ID");

        // create row1 onwards from List<T>
        int rowNum = 1;
        for (Person spec : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(spec.getId());
            row.createCell(1).setCellValue(spec.getFirstName());
            row.createCell(2).setCellValue(spec.getLastName());
            row.createCell(3).setCellValue(spec.getNationalID());
        }
    }
}
