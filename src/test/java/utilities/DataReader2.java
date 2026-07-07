package utilities;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataReader2 {

    public static List<HashMap<String, String>> data(String filePath, String sheetName) {

        List<HashMap<String, String>> myData = new ArrayList<>();

        try {

            FileInputStream fs = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fs);

            XSSFSheet sheet = workbook.getSheet(sheetName);

            Row headerRow = sheet.getRow(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

                Row currentRow = sheet.getRow(i);

                HashMap<String, String> rowData = new HashMap<>();

                for (int j = 0; j < currentRow.getPhysicalNumberOfCells(); j++) {

                    Cell currentCell = currentRow.getCell(j);

                    switch (currentCell.getCellType()) {

                    case STRING:
                        rowData.put(
                                headerRow.getCell(j).getStringCellValue(),
                                currentCell.getStringCellValue());
                        break;

                    default:
                        break;
                    }
                }

                myData.add(rowData);
            }

            workbook.close();
            fs.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return myData;
    }
}