package com.example.demo.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.Styles;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.components.table.Row;

@Service
public class ReportService {
	
	public void generateReport() {
		try {
            // Load the Excel data
            String excelFile = "src/main/resources/data/retail_trade_sales.xlsx";
            FileInputStream excelFileInputStream = new FileInputStream(new File(excelFile));
            Workbook workbook = new XSSFWorkbook(excelFileInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // Prepare the data
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (Row row : sheet) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("column1", row.getCell(0).getStringCellValue());
                dataMap.put("column2", row.getCell(1).getNumericCellValue());
                
                // Add more columns as needed
                dataList.add(dataMap);
            }

            // Create the report
            JasperReportBuilder report = DynamicReports.report();
            report
                .columns(
                    Columns.column("Column 1", "column1", DataTypes.stringType()),
                    Columns.column("Column 2", "column2", DataTypes.doubleType())
                    // Add more columns as needed
                )
                .title(
                        DynamicReports.cmp.text("Retail Trade Sales Report")
                            .setStyle(Styles.style().bold().setFontSize(18).setHorizontalAlignment(HorizontalAlignment.CENTER).setPadding(5))
                    )
                .pageFooter(DynamicReports.cmp.pageXofY())
                .setDataSource(dataList);
            // Export the report as a PDF file
            String pdfFile = "src/main/resources/output/output_report.pdf";
            report.toPdf(new FileOutputStream(pdfFile));

            System.out.println("Report generated successfully!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DRException e) {
            e.printStackTrace();
        }
    }
}
	

        

	


