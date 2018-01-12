package com.logic;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		String sheet="login";
		Workbook wb=getWorkbook(paths);
//		System.out.println(wb.getSheetIndex(sheet));
		Sheet sh=wb.getSheetAt(wb.getSheetIndex(sheet));
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
    	maps.put("c1", "F");
    	maps.put("c2", "P");
    	maps.put("c3", "F");
    	maps.put("c4", "P");
    	maps.put("c5", "F");
    	String[] str={"F","P","F","F","P"};
    	List<String> li=new ArrayList<String>();
    	System.out.println(str[0]);
    	for(int i=0;i<=4;i++){
    		li.add(str[i]);
    	}
		return maps;	
    }
	

	public static void main(String[] args) {
		WriteFirstColumn(FirstColumn());

	}

}
