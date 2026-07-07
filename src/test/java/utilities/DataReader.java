package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataReader {

	public static List<HashMap<String, String>> data(String filePath, String sheetName) {

		List<HashMap<String, String>> dataList = new ArrayList<>();

		try (FileInputStream fileInputStream = new FileInputStream(filePath);
				XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);

			Row headerRow = sheet.getRow(0);

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				Row currentRow = sheet.getRow(i);

				HashMap<String, String> rowData = new HashMap<>();

				for (int j = 0; j < headerRow.getPhysicalNumberOfCells(); j++) {

					String columnName = headerRow.getCell(j).getStringCellValue();

					String cellValue = currentRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()
							.trim();

					rowData.put(columnName, cellValue);
				}

				dataList.add(rowData);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return dataList;
	}
}