package com.alibaba.webx.common.util.excel_POI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.webx.common.util.excel.ExcelException;

/**
 * Excel2007操作组件
 * 
 * @author Zhangweifan
 * @version V1.0
 */
public class ExcelPoiUtil {

	//用户定义的属性
	private String fileURL;									//文件路径
	private String sheetName;								//sheet名字
	private Integer sheetNumber;							//sheet为book的第几个sheet
	
	private XSSFWorkbook workbook = null;					
	private XSSFSheet sheet = null;
	
	private File excelFile = null;							//文件
	private boolean ifExcelHaveExist = false;				//excel表是否已经存在
	private boolean ifWriteInit = false;					//是否做好写的准备
	private boolean ifReadInit = false;						//是否做好读的准备
	
	//构造方法
	public ExcelPoiUtil(String fileURL, String sheetName, Integer sheetNumber) {
		this.fileURL = fileURL;
		this.sheetName = sheetName;
		this.sheetNumber = sheetNumber;
	}
	
	/****************************************************************************************************/
	/********************************************public method*******************************************/
	/****************************************************************************************************/
	
	/**
	 * 读取excel某行数据
	 * 
	 * @param x
	 * @return
	 * @throws ExcelException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	public XSSFRow readRow(int x) throws ExcelException, FileNotFoundException, IOException   {
		//参数检测
		if(x < 0) {
			throw new ExcelException("参数不正确！");
		}
		if(ifReadInit == false)
			readInit();
		return sheet.getRow(x);
	}
	
	/**
	 * 读取excel某列数据
	 * 
	 * @param y
	 * @return
	 * @throws ExcelException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public XSSFCell[] readColumn(int y) throws ExcelException, FileNotFoundException, IOException {
		//参数检测
		if(y < 0) {
			throw new ExcelException("参数不正确！");
		}
		if(ifReadInit == false) {
			readInit();
		}
		
		int rowNum = sheet.getLastRowNum();
		XSSFCell[] cells = new XSSFCell[rowNum+1];
		for(int i = 0; i <= rowNum; i++) {
			XSSFRow row = sheet.getRow(i);
			cells[i] = row.getCell(y);
		}
		
		return cells;
	}
	
	/**
	 * 获取具体某个单元格内容
	 * 
	 * @param x	行
	 * @param y	列
	 * @return	
	 * @throws ExcelException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String read(int x, int y) throws ExcelException, FileNotFoundException, IOException {
		//参数检测
		if(x < 0 || y < 0) {
			throw new ExcelException("参数不正确！");
		}
		if(ifReadInit == false) {
			readInit();
		}
		
		return sheet.getRow(x).getCell(y).toString();
	}
	
	/**
	 * 关闭Excel
	 * @throws IOException
	 */
	public void close() throws IOException {
		if(workbook != null) {
			workbook.close();
		}
	}
	
	
	/****************************************************************************************************/
	/**********************************************private method****************************************/
	/****************************************************************************************************/
	
	/**
	 * 读准备
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 
	 */
	private void readInit() throws FileNotFoundException, IOException {
		workbook = new XSSFWorkbook(new FileInputStream(fileURL));
		sheet = workbook.getSheetAt(sheetNumber);
		ifReadInit = true;
	}
}
