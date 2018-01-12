package com.logic;

import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import com.test.ExcelUtil;
public class readExcel extends ExcelUtil {

	/**
	 * 读取数据，每一列的数据
	 * @param a 第a列
	 * @return
	 */
	public static Map<String,String> excelData(int a){
		Map<String,String> maps=new HashMap<String, String>();
		Sheet sheet=getSheet(0);
		int j=sheet.getLastRowNum();
		for(int i=1;i<=j;i++){
            Row r=sheet.getRow(i);
            Cell cell1=r.getCell(0);
            Cell cell=r.getCell(a);
            String key=getCellData(cell1);           
            String value=getCellData(cell);
            maps.put(key, value);
//          System.out.println(key+"::"+value);
		}
		return maps;
	} 

	


	
	public static void main(String[] args) {
		
	}

}
