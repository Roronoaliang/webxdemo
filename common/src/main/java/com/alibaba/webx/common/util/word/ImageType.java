package com.alibaba.webx.common.util.word;

/**
 * 图片类型
 * 
 * @author xiaoMzjm
 *
 */
public enum ImageType {

	PNG(6,1) , JPEG(5,2) , JPG(5,2);
	
	// 成员变量  
    private int type;  
    private int index;  
    
    // 构造方法  
    private ImageType(int type, int index) {  
        this.type = type;  
        this.index = index;  
    }

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}  
}
