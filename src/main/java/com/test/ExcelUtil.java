package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil extends Base {
	protected static String paths="testdata.xlsx";
	private static InputStream is=null;
	private static Workbook wb=null;	
	/**
	 * 需要噢读取excel中的列号
	 */
	public static int i1=3;
	public static int i2=4;
	public static int i3=5;
	/**
	 * 获取工作簿workbook对象
	 */
    public static Workbook getWorkbook(String path){
    	try {
			is=new FileInputStream(new File(path));
			}
    	catch (FileNotFoundException e) {
			System.out.println("获取输入流失败");
			e.printStackTrace();
		}
    	if(path.endsWith(".xlsx")){
    		try {
				wb=new XSSFWorkbook(is);
			} catch (IOException e) {
				System.out.println("获取工作簿失败");
				e.printStackTrace();
			}
    	}else {
			try {
				wb=new HSSFWorkbook(is);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("获取工作簿失败");
			}
		}
    	return wb;
    	
    }
    /**
     * 获取输出流
     */  
	public static FileOutputStream outStream(String path){
		FileOutputStream out=null;
		try {
			out=new FileOutputStream(new File(path));
		} catch (FileNotFoundException e) {
			System.out.println("获取输出流失败");
			e.printStackTrace();
		}
		return out;
	}
	/**
	 * 删除指定sheet
	 * @param args
	 */
	public static void deleteSheet(String path,String sheetName){
		Workbook wb=ExcelUtil.getWorkbook(path);
		FileOutputStream out=null;
		try {
			out=new FileOutputStream(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
//		System.out.println(wb.getSheetIndex(sheetName));
		wb.removeSheetAt(wb.getSheetIndex(sheetName));
		
		try {
			wb.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取Input的sheet
	 * @param args
	 */
	public static Sheet getSheet(int i){
		return getWorkbook(paths).getSheetAt(i);
	}
	/**
	 * 获取cell单元格的数据,转换成String格式
	 * @param cell cell对象
	 */
	@SuppressWarnings("deprecation")
	public static String getCellData(Cell cell){
		String data=null;
		if(cell==null){data="";
		}else if (cell.getCellType()== CellType.NUMERIC.getCode()) {
			BigDecimal bigDecimal = new BigDecimal(cell.getNumericCellValue());  
	        data = bigDecimal.toString();  
	    }else if(cell.getCellType()==CellType.STRING.getCode()){
		data= String.valueOf(cell.getStringCellValue());
	    }else if(cell.getCellType()==Cell.CELL_TYPE_BOOLEAN){
		data=String.valueOf(cell.getBooleanCellValue());
	    }else {
//			print("输入的格式不对");
	    	data="";
//	    	System.out.println("输入有误");
	    }
	return data;
	}
	/**
	 * 获取Input的行数据,根据表和行数
	 * @param args
	 */
	public static Object[] getRowData(int sheet,int rows){
		int i=0;
		int j=getSheet(sheet).getRow(0).getPhysicalNumberOfCells();
		Row r_1=getSheet(sheet).getRow(rows);
		Object[] ob=new Object[j];
		for(Cell cell:r_1){
//			if(i==i1||i==i2||i==i3){
			String s=getCellData(cell);
//			maps.put(i, s);
			ob[i]=s;
			i++;
		}
		for(int k=0;k<j;k++){
			if(ob[k]==null){
				ob[k]="";
			}
		}
		
		return ob;
	}
    /**
     * 获取每一行的数据，第一行的数据作为key
     * @param args
     */
	public static Map<String,String> getRows(int sheet,int rows){
		int i=0;
		Row r_1=getSheet(sheet).getRow(rows);
		Map<String,String> maps=new HashMap<String, String>();
		for(Cell cell:r_1){
			String s1=convert(i);
			String s2=getCellData(cell);
			maps.put(s1, s2);
			i++;
		}
		return maps;
		
	}
	/**
	 * 用数字对应第一行的数据
	 * @param args
	 */
	public static String convert(int i){
		String a="";
//		for(Integer s:getRowData(0,0).keySet()){
//			if(i==s){a=getRowData(0,0).get(s);}
//		}
		return a;
	}
	/**
	 * Map转换成object【】【】
	 * @param args
	 */
	public static Object[][] changeObject(int sheet){
		Row r=getSheet(sheet).getRow(0);
		int k=r.getPhysicalNumberOfCells();
		int i=getSheet(sheet).getLastRowNum();
		Object[][] data= null;
		Object[] str=new Object[k];
		List<Object[]> li=new ArrayList<Object[]>();	
		for(int j=1;j<=i;j++){
			str=getRowData(0,j);
			li.add(str);
		}
		data=new Object[li.size()][];
		for(int si=0;si<=li.size()-1;si++){
			data[si]=li.get(si);
		}
		return data;
	}
	
	
	
	public static void main(String[] args) {
		for(int i=0;i<getRowData(0,49).length;i++){
			System.out.println(getRowData(0,23)[i]);
		}
     System.out.println(changeObject(0)[1][0]);
	}

}
