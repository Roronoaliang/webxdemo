package com.alibaba.webx.web.module.screen.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * 验证码【样式】相关类
 * 
 * @author xiaoMzjm
 */
public class MyCaptchaEngine extends ListImageCaptchaEngine{

	protected void buildInitialFactories() {

        int minWordLength = 4;

        int maxWordLength = 4;

        int fontSize = 20;

        int backgroundWidth = 80;

        int backgroundHeight = 40;

        // 随机字符生成器
        WordGenerator wordGenerator = new RandomWordGenerator("0123456789abcdefghijklmnopqrstuvwxyz");

        // 随机颜色生成器
        RandomListColorGenerator randomListColorGenerator = new RandomListColorGenerator(new Color[] {new Color(23, 170, 27), new Color(220, 34, 11),new Color(23, 67, 172) });
        
        // 文本解析器
        TextPaster textPaster = new DecoratedRandomTextPaster(minWordLength, maxWordLength, randomListColorGenerator, new TextDecorator[] {});

        // 背景生成器
        BackgroundGenerator backgroundGenerator = new UniColorBackgroundGenerator(backgroundWidth, backgroundHeight, Color.WHITE);

        // 随机字体样式生成器
        FontGenerator font = new RandomFontGenerator(fontSize, fontSize,new Font[] { new Font("nyala", Font.BOLD, fontSize),new Font("Bell MT", Font.PLAIN, fontSize),new Font("Credit valley", Font.BOLD, fontSize) });

        // 图片变形
        ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] {});
        ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});
        ImageDeformation textDef = new ImageDeformationByFilters( new ImageFilter[] {});

        // 文字转图片
        WordToImage word2image = new DeformedComposedWordToImage(font,backgroundGenerator, textPaster, backDef, textDef, postDef);

        addFactory(new GimpyFactory(wordGenerator, word2image));
    }
}