package com.logic;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;




import com.test.ExcelUtil;

public class writeExcel extends ExcelUtil {
	 /**
     * 往已有的sheet写入一列内容
     * @param li得到的测试结果LIST
     */
	@SuppressWarnings("deprecation")
	public static void WriteFirstColumn(Map<String,String> li){
		@SuppressWarnings("unused")
		String sheet="login";
		Workbook wb=getWorkbook(paths);
//		System.out.println(wb.getSheetIndex(sheet));
		Sheet sh=wb.getSheetAt(0);
		FileOutputStream out=outStream(paths);
		int i=0;	
		for(String str:li.keySet()){
			Row fr=sh.getRow(i+1);
			for(int j=1;j<=li.size();j++){
			sh.autoSizeColumn(i+1);
		Cell cells=fr.createCell(6);
		CellStyle cellstyle=wb.createCellStyle();
		if(li.get(str)=="P"){
			cellstyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}else{
			cellstyle.setFillForegroundColor(IndexedColors.RED.getIndex());
			cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		cells.setCellStyle(cellstyle);
		cells.setCellValue(li.get(str));
			}
		i++;	
		}
		try {
			wb.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * Report中第一列的内容
     * @return
     */
	public static Map<String,String> FirstColumn(){
		Map<String,String> maps=new HashMap<String, String>();
    	
    	String[] str={"F","P","F","F","P","F","P","F","F","P","P"};
//    	List<String> li=new ArrayList<String>();
    	System.out.println(str[0]);
    	for(int i=1;i<=11;i++){
    		maps.put(Integer.toString(i),str[i-1]);
    	}
		return maps;	
    }
	/**
	 * 返回结果
	 * @param args
	 */
	public static String res(String s){
		if(isPlay(s)){
			return "P";
		}else{
			return "F";
		}
	}
	/**
	 * 写入数据前清理原有数据
	 * @param args
	 */
	public static void cleardata(){
		Sheet sheet=getSheet(0);
		int j=sheet.getLastRowNum();
		for(int i=1;i<=j;i++){
            Row r=sheet.getRow(i);
            Cell cell1=r.getCell(6);
            cell1.getRichStringCellValue();
            }
	}
	
	
	public static void main(String[] args) {
		WriteFirstColumn(FirstColumn());
//		cleardata();
	}

}
