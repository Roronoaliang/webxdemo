package com.alibaba.webx.common.util.excel;

import java.io.IOException;

import jxl.Cell;
import jxl.write.WriteException;

import org.junit.Test;

public class UseCase {

	// 测试读具体一个单元格————测试通过
	@Test
	public void test() {
		ExcelUtil excel = null;
		try {
			excel = new ExcelUtil("D:/test.xls", "ggch", 0);
			for(int i = 0 ; i < 300 ; i++) {
				System.out.println(excel.read(1, i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				excel.close();
			} catch (WriteException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 测试读取某一行————测试通过
	@Test
	public void test1() {
		ExcelUtil excel = null;
		try {
			excel = new ExcelUtil("D:/test.xls", "ggch", 0);
			Cell[] cells = excel.readRow(1);
			for(Cell c : cells) {
				System.out.println(c.getContents());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				excel.close();
			} catch (WriteException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 测试读取某一列————测试通过
	@Test
	public void test2()  {
		ExcelUtil excel = null;
		try {
			excel = new ExcelUtil("D:/test.xls", "ggch", 0);
			Cell[] cells = excel.readColumn(1);
			for(Cell c : cells) {
				System.out.println(c.getContents());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				excel.close();
			} catch (WriteException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 测试合并单元格————测试通过
	@Test
	public void test3(){
		ExcelUtil excel = null;
		try {
			excel = new ExcelUtil("D:/test2.xls", "ggch", 0);
			excel.mergeCell(0, 0, 2, 2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				excel.close();
			} catch (WriteException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 测试创建并写入一个新Excel————测试通过
	@Test
	public void test4(){
		ExcelUtil excel = null;
		try {
			excel = new ExcelUtil("D:/test2.xls", "ggch", 0);
			excel.writeOrUpdate("哈哈", 0, 0);
			excel.writeOrUpdate("哈哈2", 1, 1);
			excel.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				excel.close();
			} catch (WriteException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 测试创建并写入一个新Excel，用自定义样式————测试通过
	@Test
	public void test4_2(){
		ExcelUtil excel = null;
		try {
			ExcelFormat excelFormat = new ExcelFormat();
			excelFormat.setBackGroundColour(ExcelFormat.Colour_Blue);
			excel = new ExcelUtil("D:/test2.xls", "ggch", 0 ,excelFormat );
			excel.writeOrUpdate("哈哈", 0, 0);
			excel.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				excel.close();
			} catch (WriteException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 测试修改，不清楚原有样式，只修改文字内容，颜色什么的都不变————测试通过
	@Test
	public void test5(){
		ExcelUtil excel = null;
		try {
			excel = new ExcelUtil("D:/test2.xls", "ggch", 0);
			excel.writeOrUpdate("嘻嘻", 0, 0);
			excel.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				excel.close();
			} catch (WriteException | IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	// 测试清除原有样式地修改————测试通过
	@Test
	public void test6(){
		ExcelUtil excel = null;
		try {
			excel = new ExcelUtil("D:/test2.xls", "ggch", 0);
			excel.writeOrUpdate("嘻嘻", 0, 0 , true);
			excel.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				excel.close();
			} catch (WriteException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// 测试大量写入————测试通过
	@Test
	public void test7(){
		long start = System.currentTimeMillis();
		ExcelUtil excel = null;
		try {
			excel = new ExcelUtil("D:/test2.xls", "ggch", 0);
			for(int y = 0 ; y < 65530 ; y++){
				for(int x = 0 ; x< 10 ; x++) {
					excel.writeOrUpdate("test", x, y);
				}
			}
			excel.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				excel.close();
			} catch (WriteException | IOException e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}