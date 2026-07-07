package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {

    public static void writeResult(String filePath, String sheetName, int rowNumber, String result)
            throws IOException {

        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        XSSFSheet sheet = workbook.getSheet(sheetName);

        Row row = sheet.getRow(rowNumber);

        Cell cell = row.getCell(3);

        if (cell == null) {
            cell = row.createCell(3);
        }

        cell.setCellValue(result);

        XSSFCellStyle style = workbook.createCellStyle();

        if (result.equalsIgnoreCase("PASS")) {

            style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());

        } else {

            style.setFillForegroundColor(IndexedColors.RED.getIndex());

        }

        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);

        fis.close();

        FileOutputStream fos = new FileOutputStream(filePath);

        workbook.write(fos);

        fos.close();

        workbook.close();
    }

}