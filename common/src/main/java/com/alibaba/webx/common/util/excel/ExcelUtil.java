package com.alibaba.webx.common.util.excel;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;

/**
 * Excel操作组件
 * 
 * 功能：
 * 1、新建Excel并插入字符串容
 * 2、修改Excel并插入字符串容
 * 3、读取行
 * 4、读取列
 * 5、读取某个单元格
 * 6、合并指定范围单元格
 * 
 * @author xiaoMzjm
 * 
 */
public class ExcelUtil {

	private ExcelFormat myExcelFormat = null;

	// 用户定义的属性
	private String fileURL;									// 文件路径
	private String sheetName;								// sheet名字
	private Integer sheetNumber;							// sheet为book的第几个sheet

	private Workbook workBook = null;						// 读Book
	private WritableWorkbook writableWorkbook = null;		// 写Book
	
	private Sheet sheet = null;								// 读Sheet
	private WritableSheet writableSheet = null;				// 写Sheet
	
	private WritableCellFormat writableCellFormat = null;	// 写单元格样式

	private WritableFont writableFont = null;				// 写文字样式
	
	private Label label = null;
	private File excelFile = null;							// 文件
	private boolean ifExcelHaveExist = false;				// Excel表是否已存在
	private boolean ifWriteInit = false;					// 是否做好写入操作的准备
	private boolean ifReadInit = false; 					// 是否做好读的准备
	
	// 构造方法
	public ExcelUtil(String fileURL,String sheetName,Integer sheetNumber){
		this.fileURL = fileURL;
		this.sheetName = sheetName;
		this.sheetNumber = sheetNumber;
		this.myExcelFormat = new ExcelFormat();
	}
	
	// 构造方法
	public ExcelUtil(String fileURL,String sheetName,Integer sheetNumber,ExcelFormat myExcelFormat){
		this.fileURL = fileURL;
		this.sheetName = sheetName;
		this.sheetNumber = sheetNumber;
		this.myExcelFormat = myExcelFormat;
	}
	
	
	/****************************************************************************************************/
	/********************************************public method*******************************************/
	/****************************************************************************************************/
	
	/**
	 * 写入excel，若excel已存在，则相当于修改，修改时不清除原有样式。
	 * 
	 * 写入完毕需手动调用commit()和close()方法。
	 * 
	 * @param contents
	 * @param x
	 * @param y
	 * @return
	 * @throws ExcelException
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @throws IOException
	 */
	public boolean writeOrUpdate(String contents, int x, int y) throws ExcelException, RowsExceededException, WriteException, IOException {
		return writeOrUpdate(contents,x,y,false);
	}
	
	/**
	 * 写入excel，若excel已存在，则相当于修改，修改时清除原有样式。
	 * 
	 * 写入完毕需手动调用commit()和close()方法。
	 * 
	 * @param contents
	 * @param x
	 * @param y
	 * @param isClearOldFormat
	 * @return
	 * @throws ExcelException
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @throws IOException
	 */
	public boolean writeOrUpdate(String contents, int x, int y, boolean isClearOldFormat) throws ExcelException, RowsExceededException, WriteException, IOException {

		// 参数检查
		if( StringUtils.isBlank(contents) || x < 0 || y < 0) {
			throw new ExcelException("传入的参数不正确!");
		}
		
		// 初始化
		if(ifWriteInit == false) {
			writeInit();
		}

		// 写入
		if (ifExcelHaveExist && !isClearOldFormat) {
			label = new Label(x, y, contents);
			label.setCellFormat(writableSheet.getWritableCell(x, y).getCellFormat());
		} else {
			label = new Label(x, y, contents, writableCellFormat);
		}
		writableSheet.addCell(label);
		return true;
	}
	
	
	/**
	 * 合并单元格
	 * 
	 * 写入完毕需手动调用commit()和close()方法。
	 * 
	 * @param mergeList
	 * @return
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @throws ExcelException 
	 * @throws IOException 
	 */
	public boolean mergeCell(int col1, int row1, int col2, int row2) throws RowsExceededException, WriteException, ExcelException, IOException{
		if(col1 < 0 || row1 < 0 || col2 < 0 || row2 < 0){
			throw new ExcelException("参数不正确！");
		}
		// 初始化
		if(ifWriteInit == false) {
			writeInit();
		}
		writableSheet.mergeCells(col1, row1, col2, row2);
		return true;
	}
	
	/**
	 * 提交修改
	 * @throws IOException 
	 */
	public void commit() throws IOException{
		if(writableWorkbook != null) {
			writableWorkbook.write();
		}
	}
	
	/**
	 * 读取excel某行数据
	 * 
	 * @param x
	 * @param y
	 * @throws IOException 
	 * @throws BiffException 
	 * @returN String 读取到的内容
	 */
	public Cell[] readRow(int x) throws ExcelException, IOException, BiffException {
		// 参数检测
		if(x < 0 ) {
			throw new ExcelException("参数不正确！");
		}
		if(ifReadInit == false) {
			readInit();
		}
		return sheet.getRow(x);
	}
	
	/**
	 * 读取excel某列数据
	 * 
	 * @param y
	 * @return
	 * @throws ExcelException
	 * @throws BiffException
	 * @throws IOException
	 */
	public Cell[] readColumn(int y) throws ExcelException, BiffException, IOException {
		// 参数检测
		if(y < 0 ) {
			throw new ExcelException("参数不正确！");
		}
		if(ifReadInit == false) {
			readInit();
		}
		return sheet.getColumn(y);
	}
	
	/**
	 * 读取excel的具体某个单元格
	 * 
	 * @param x 写入位置x
	 * @param y 写入位置y
	 * @throws ExcelException 
	 * @throws IOException 
	 * @throws BiffException 
	 * @returN String 读取到的内容
	 */
	public String read(int x, int y) throws ExcelException, BiffException, IOException {
		// 参数检测
		if(x < 0 || y < 0) {
			throw new ExcelException("参数不正确！");
		}
		if(ifReadInit == false) {
			readInit();
		}
		return sheet.getCell(x, y).getContents();
	}

	/**
	 * 关闭Excel，在完成全部的读/写入操作后，应该调用此方法关闭Excel。
	 * @throws WriteException
	 * @throws IOException
	 */
	public void close() throws WriteException, IOException{
		if(writableWorkbook != null) {
			writableWorkbook.close();
		}
		if(workBook != null) {
			workBook.close();
		}
	}
	
	
	/****************************************************************************************************/
	/**********************************************private method****************************************/
	/****************************************************************************************************/
	// 读准备
	private void readInit() throws BiffException, IOException{
		workBook = Workbook.getWorkbook(new File(fileURL));
		sheet = workBook.getSheets()[sheetNumber];
		ifReadInit = true;
	}
	
	
	// 写准备
	private void writeInit() throws IOException, WriteException{
		
		// 获取/创建一个Excel
		try {
			excelFile = new File(fileURL);
			workBook = Workbook.getWorkbook(excelFile);
			writableWorkbook = Workbook.createWorkbook(new File(fileURL), workBook);// 创建workbook的副本
			ifExcelHaveExist = true;
		} catch (Exception e) {
			int count = 0;
			for (int i = 0; i < fileURL.length(); i++) {
				if ('/' == fileURL.charAt(i))
					count++;
			}
			if (count > 1) {
				excelFile = new File(fileURL.substring(0,fileURL.lastIndexOf('/')));
				excelFile.mkdir();
			}
			writableWorkbook = Workbook.createWorkbook(new File(fileURL));
		}
		
		// 获取/创建一个Sheet
		if (ifExcelHaveExist) {
			writableSheet = writableWorkbook.getSheet(sheetNumber);
		} else {
			writableSheet = writableWorkbook.createSheet(sheetName, sheetNumber);
		}
		
		// 创建一个Cell的样式
		writableCellFormat = new WritableCellFormat();
		writableCellFormat.setBackground(myExcelFormat.getBackGroundColour());
		writableCellFormat.setBorder(Border.ALL, myExcelFormat.getBorderLineStyle());
		writableCellFormat.setWrap(true);
		writableCellFormat.setAlignment(myExcelFormat.getTextAlignment());
		writableCellFormat.setVerticalAlignment(myExcelFormat.getVerticalAlignment());
		if (myExcelFormat.isIfBold()) {
			writableFont = new WritableFont(myExcelFormat.getFontName(),myExcelFormat.getFontSize(),WritableFont.NO_BOLD, myExcelFormat.isIfItalic() ,myExcelFormat.getUnderlineStyle(),myExcelFormat.getFontColour());
		} else {
			writableFont = new WritableFont(myExcelFormat.getFontName(),myExcelFormat.getFontSize(), WritableFont.NO_BOLD, myExcelFormat.isIfItalic(),myExcelFormat.getUnderlineStyle(), myExcelFormat.getFontColour());
		}
		writableCellFormat.setFont(writableFont);
		
		ifWriteInit = true;
	}
}