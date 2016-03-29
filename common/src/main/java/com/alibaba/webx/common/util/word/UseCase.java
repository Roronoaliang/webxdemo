package com.alibaba.webx.common.util.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * word2007 操作组件  使用用例
 * 
 * @author xiaoMzjm
 *
 */
public class UseCase {
	
	WordUtil word = new WordUtil();
	
	@Test
	// 测试创建一个新的文档
	public void test1(){
		word.createNewDocument();
		try {
			word.save("D:/test.docx");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				word.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	// 测试创建一个新的文档，并写入文字
	public void test2(){
		word.createNewDocument();
		word.appendText("写入测试");
		try {
			word.save("D:/test.docx");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				word.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Test
	// 测试创建一个新的文档，并写入文字&插入图片
	public void test4() {
		word.createNewDocument();
		try {
			word.appendText("1、题目题目");
			WordImage image = new WordImage(60, 60, ImageType.PNG, new File("D:/火影头像.png"));
			word.appendImage(image);
			word.save("D:/test.docx");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				word.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	// 测试打开已存在的文件，在末尾换行插入文字
	public void test5(){
		try {
			word.openDocument("D:/test.docx");
			word.appendText("<html><head></head><body><div style=\"color:red\">1 timu</div></body></html>");
			word.save("D:/test2.docx");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				word.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Test
	// 测试打开已存在的文件，在末尾插入文字&图片
	public void test7(){
		try {
			word.openDocument("D:/test.docx");
			word.appendText("3、题目题目");
			WordImage image = new WordImage(60, 60, ImageType.PNG, new File("D:/火影头像.png"));
			word.appendImage(image);
			word.save("D:/test.docx");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				word.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	// 测试替换多处文字
	public void test8() throws FileNotFoundException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("${name}","topview");
		map.put("${phone}", "1111111");
		try {
			word.openDocument("D:/test.docx");
			word.replace(map);
			word.save("D:/test2.docx");
			word.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				word.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	// 测试同时替换多处文字&图片
	// 注意！替换符一定要跟左右的字的字体（字体、大小、斜粗等等选一个）不一样，这样的话才能提高替换成功率！！！
	// 注意！替换符一定要跟左右的字的字体（字体、大小、斜粗等等选一个）不一样，这样的话才能提高替换成功率！！！
	// 注意！替换符一定要跟左右的字的字体（字体、大小、斜粗等等选一个）不一样，这样的话才能提高替换成功率！！！
	// 注意！替换符一定要跟左右的字的字体（字体、大小、斜粗等等选一个）不一样，这样的话才能提高替换成功率！！！
	// 注意！替换符一定要跟左右的字的字体（字体、大小、斜粗等等选一个）不一样，这样的话才能提高替换成功率！！！
	public void test10(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("${name}","topview");
		map.put("${phone}", "1111111");
		WordImage image = new WordImage(60, 60, ImageType.PNG, new File("D:/火影头像.png"));
		map.put("${head}", image);
		try {
			word.openDocument("D:/test.docx");
			word.replace(map);
			word.save("D:/test2.docx");
			word.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				word.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	// xdoc 转 html
	public void test11(){
		try {
//			System.out.println(word.xdocToHtml("D:/test2.docx", "./src/main/webapp/images/app/word/", "../images/app/word/"));
			System.out.println(word.xdocToHtml("D:/test2.docx", "D:/test/piccache/", "../images/app/word/"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	// doc 转 html
	public void test12(){
		try {
			System.out.println(word.doc2Html("D:/test2.doc", "D:/test/piccache/", "../images/app/word/"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}