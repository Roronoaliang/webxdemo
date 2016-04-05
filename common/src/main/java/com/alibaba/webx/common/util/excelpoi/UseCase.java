package com.alibaba.webx.common.util.excelpoi;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.junit.Test;

import com.alibaba.webx.common.util.excel.ExcelException;

public class UseCase {

	//测试读具体一个单元格
	@Test
	public void test() throws FileNotFoundException, ExcelException, IOException {
		ExcelPoiUtil excel = new ExcelPoiUtil("F:/test.xlsx", "sheet1", 0);
		for(int i = 0; i < 10; i++) {
			System.out.println(excel.read(i, 0));
		}
		excel.close();
	}
	
	//测试读取某一行
	@Test
	public void test1() throws FileNotFoundException, ExcelException, IOException {
		ExcelPoiUtil excel = new ExcelPoiUtil("F:/test.xlsx", "sheet1", 0);
		XSSFRow row = excel.readRow(1);
		for(int i = 0; i < row.getLastCellNum(); i++) {
			if(row.getCell(i) != null)
				System.out.println(row.getCell(i).toString());
			else 
				System.out.println("");
		}
		excel.close();
	}
	
	//测试读取某一列
	@Test
	public void test2() throws FileNotFoundException, ExcelException, IOException {
		ExcelPoiUtil excel = new ExcelPoiUtil("F:/test.xlsx", "sheet1", 0);
		XSSFCell[] cells = excel.readColumn(0);
		for(int i = 0; i < cells.length; i++) {
			System.out.println(cells[i].toString());
		}
		excel.close();
	}
}
