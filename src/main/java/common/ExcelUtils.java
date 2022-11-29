package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils {


public String[][] getDataFromExcel(String sheetName) throws IOException{
	
 Workbook workbook;
 Sheet sheet;
 Row row;
 Cell cell;
 
	File file= new File("D:\\AutomationTest\\02Projects\\DrunkinDonuts\\TestData\\Test_Data.xlsx");
	FileInputStream inputStream=  new FileInputStream(file);
	workbook=new XSSFWorkbook(inputStream);
	
	//get Sheet
	sheet=workbook.getSheet(sheetName);
	
	//get Row
	int numberOfRow=sheet.getLastRowNum();
	row=sheet.getRow(0); //lấy số cột
	
	//get Cell
	int numberOfColumn=row.getLastCellNum();
	String [][] excelData= new String[numberOfRow][numberOfColumn];
	
	for(int i=0; i<numberOfRow;i++) {
		for (int j=0; j<numberOfColumn; j++) {
			row=sheet.getRow(i+1);
			excelData[i][j]=row.getCell(j).toString();
		}
	}
return excelData;
}
}
