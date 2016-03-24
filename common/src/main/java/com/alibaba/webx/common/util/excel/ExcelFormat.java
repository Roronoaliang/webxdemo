package com.alibaba.webx.common.util.excel;

import java.awt.Color;

import jxl.format.Alignment;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableFont;
import jxl.write.WritableFont.FontName;

public class ExcelFormat {

	// 常用单元格格式
	private FontName fontName = WritableFont.createFont("宋体");				// 字体
	private Integer fontSize = 10;											// 字体大小
	private Colour fontColour = Colour.BLACK; 								// 字体颜色
	private Colour backGroundColour = getNearestColour("#FFFFFF");			// 背景色#F0F8FF
	private Alignment textAlignment = Alignment.CENTRE; 					// 水平上的【居中/左/右】
	private VerticalAlignment verticalAlignment = VerticalAlignment.CENTRE; // 垂直上的【居中/左/右】
	private boolean ifItalic = false;										// 是否斜体
	private boolean ifBold = false;											// 是否加粗
	private UnderlineStyle underlineStyle = UnderlineStyle.NO_UNDERLINE; 	// 下划线类型
	private BorderLineStyle borderLineStyle = BorderLineStyle.NONE;			// 边框样式

	// 便于记住而重新设计的名字，只有常用的
	public final static Alignment Text_Left = Alignment.LEFT;						// 居左
	public final static Alignment Text_Right = Alignment.RIGHT;						// 居右
	public final static Alignment Text_Centre = Alignment.CENTRE;					// 居中
	public final static Colour Colour_Blue = getNearestColour("#6699FF");			// 蓝色
	public final static Colour Colour_Green = getNearestColour("#99CC33");			// 绿色
	public final static Colour Colour_Red = getNearestColour("#FF6666");			// 红色
	public final static Colour Colour_Yeelow = getNearestColour("#FFFF66");			// 黄色
	public final static BorderLineStyle BorderLineStyle_XuXian = BorderLineStyle.HAIR;	// 虚线边框
	public final static BorderLineStyle BorderLineStyle_ShiXian = BorderLineStyle.THIN;	// 实线边框
	public final static BorderLineStyle BorderLineStyle_None = BorderLineStyle.NONE;	// 无边框
	
	/**
	 * 自定义颜色
	 * 
	 * @param strColor
	 * @return
	 */
	public static Colour getNearestColour(String strColor) {
		Color cl = Color.decode(strColor);
		Colour color = null;
		Colour[] colors = Colour.getAllColours();
		if ((colors != null) && (colors.length > 0)) {
			Colour crtColor = null;
			int[] rgb = null;
			int diff = 0;
			int minDiff = 999;
			for (int i = 0; i < colors.length; i++) {
				crtColor = colors[i];
				rgb = new int[3];
				rgb[0] = crtColor.getDefaultRGB().getRed();
				rgb[1] = crtColor.getDefaultRGB().getGreen();
				rgb[2] = crtColor.getDefaultRGB().getBlue();

				diff = Math.abs(rgb[0] - cl.getRed())
						+ Math.abs(rgb[1] - cl.getGreen())
						+ Math.abs(rgb[2] - cl.getBlue());
				if (diff < minDiff) {
					minDiff = diff;
					color = crtColor;
				}
			}
		}
		if (color == null)
			color = Colour.BLACK;
		return color;
	}

	public FontName getFontName() {
		return fontName;
	}

	public void setFontName(FontName fontName) {
		this.fontName = fontName;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public Colour getFontColour() {
		return fontColour;
	}

	public void setFontColour(Colour fontColour) {
		this.fontColour = fontColour;
	}

	public Colour getBackGroundColour() {
		return backGroundColour;
	}

	public void setBackGroundColour(Colour backGroundColour) {
		this.backGroundColour = backGroundColour;
	}

	public Alignment getTextAlignment() {
		return textAlignment;
	}

	public void setTextAlignment(Alignment textAlignment) {
		this.textAlignment = textAlignment;
	}

	public VerticalAlignment getVerticalAlignment() {
		return verticalAlignment;
	}

	public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public boolean isIfItalic() {
		return ifItalic;
	}

	public void setIfItalic(boolean ifItalic) {
		this.ifItalic = ifItalic;
	}

	public boolean isIfBold() {
		return ifBold;
	}

	public void setIfBold(boolean ifBold) {
		this.ifBold = ifBold;
	}

	public UnderlineStyle getUnderlineStyle() {
		return underlineStyle;
	}

	public void setUnderlineStyle(UnderlineStyle underlineStyle) {
		this.underlineStyle = underlineStyle;
	}

	public BorderLineStyle getBorderLineStyle() {
		return borderLineStyle;
	}

	public void setBorderLineStyle(BorderLineStyle borderLineStyle) {
		this.borderLineStyle = borderLineStyle;
	}

	
	
}
