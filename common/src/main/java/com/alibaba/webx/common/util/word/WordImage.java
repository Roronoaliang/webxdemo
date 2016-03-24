package com.alibaba.webx.common.util.word;

import java.io.File;

/**
 * word中的图片
 * 
 * @author xiaoMzjm
 *
 */
public class WordImage {

	private int width;
	private int height;
	private ImageType type;
	private File image;
	public WordImage(int width, int height, ImageType type, File image) {
		super();
		this.width = width;
		this.height = height;
		this.type = type;
		this.image = image;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public ImageType getType() {
		return type;
	}
	public void setType(ImageType type) {
		this.type = type;
	}
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	
}