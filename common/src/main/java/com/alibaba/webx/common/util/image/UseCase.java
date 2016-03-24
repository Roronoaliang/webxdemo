package com.alibaba.webx.common.util.image;

import java.io.IOException;

import org.junit.Test;

/**
 * 图片处理组件 测试用例
 * 
 * @author xiaoMzjm
 *
 */
public class UseCase {
	
	// 对图片裁剪
	@Test
	public void test_cropImage(){
		try {
			ImageUtils.cropImage("d:\\20140305_184252.jpg","d:\\cropImage.jpg",0,0,2488,2500,"jpg","jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 缩小图片
	@Test
	public static void test_reduceImageByRatio(){
		try {
			ImageUtils.reduceImageByRatio("d:\\20140305_184252.jpg","d:\\reduceImageByRatio.jpg",2,2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 长高等比例缩小图片 
	@Test
	public static void test_reduceImageEqualProportion(){
		try {
			ImageUtils.reduceImageEqualProportion("d:\\20140305_184252.jpg","d:\\reduceImageEqualProportion.jpg",3);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 按倍率放大图片 
	@Test
	public static void test_enlargementImageByRatio(){
		try {
			ImageUtils.enlargementImageByRatio("d:\\20140305_184252.jpg","d:\\enlargementImageByRatio.jpg",1,1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 长高等比例放大图片 
	@Test
	public static void test_enlargementImageEqualProportion(){
		try {
			ImageUtils.enlargementImageEqualProportion("d:\\20140305_184252.jpg","d:\\enlargementImageEqualProportion.jpg",1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 后面的要用自己测试，太多了
}
