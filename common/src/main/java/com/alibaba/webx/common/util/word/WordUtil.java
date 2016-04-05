package com.alibaba.webx.common.util.word;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.jsoup.Jsoup;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.slf4j.Logger;
import org.w3c.dom.Document;

import com.alibaba.webx.common.factory.log.LoggerFactory;
import com.alibaba.webx.common.util.date.MyDateUtil;
import com.alibaba.webx.common.util.date.MyDateUtil.MySimpleDateFormatException;
import com.alibaba.webx.common.util.uuid.UUIDUtil;

/**
 * word2007 操作组件
 * 
 * @author xiaoMzjm
 *
 */
public class WordUtil {
	
	private static Logger log = LoggerFactory.getLogger(WordUtil.class);

	// word 文档
	private CustomXWPFDocument xdoc;

	/**
	 * 创建一个新的文档
	 */
	public void createNewDocument() {
		xdoc = new CustomXWPFDocument();
	}

	/**
	 * 打开一个文档
	 * 
	 * @param xdocPath
	 *            文档路径
	 * @throws IOException
	 */
	public void openDocument(String xdocPath) throws IOException {
		InputStream is = new FileInputStream(xdocPath);
		xdoc = new CustomXWPFDocument(is);
	}

	/**
	 * 在末尾追加一段纯文本
	 * 
	 * @param content
	 *            文本内容
	 */
	public void appendText(String content) {
		// 创建一个段落
		XWPFParagraph paragraph = xdoc.createParagraph();
		// 一个XWPFRun代表具有相同属性的一个区域
		XWPFRun run = paragraph.createRun();
		run.setText(content);
	}

	/**
	 * 在末尾追加一张图片
	 * 
	 * @param file
	 *            图片文件
	 * @throws FileNotFoundException
	 * @throws InvalidFormatException
	 */
	public void appendImage(WordImage image) throws InvalidFormatException,
			FileNotFoundException {
		// 创建一个段落
		XWPFParagraph paragraph = xdoc.createParagraph();
		// 为段落加入图片
		String picId = paragraph.getDocument().addPictureData(
				new FileInputStream(image.getImage()),
				image.getType().getType());
		// 把图片加到文档中
		xdoc.createPicture(picId,
				xdoc.getNextPicNameNumber(image.getType().getType()),
				image.getWidth(), image.getHeight(), paragraph, null);
	}

	/**
	 * 保存文档
	 * 
	 * @param docPath
	 * @throws IOException
	 */
	public void save(String xdocPath) throws IOException {
		FileOutputStream fos = new FileOutputStream(xdocPath);
		xdoc.write(fos);
		fos.close();
	}

	/**
	 * 关闭文档
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		xdoc.close();
	}

	/**
	 * 替换
	 * 
	 * 注意！替换符一定要跟左右的字体（字体、大小、斜粗等等）不一样，这样的话才能提高替换成功率！！！
	 * 
	 * @param templatePath
	 * @param map
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public void replace(Map<String, Object> map) throws IOException,
			InvalidFormatException {
		// 替换段落中的指定文字
		Iterator<XWPFParagraph> itPara = xdoc.getParagraphsIterator();
		while (itPara.hasNext()) {
			XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
			replaceParagraph(paragraph, map);
		}

		// 替换表格中的指定文字
		Iterator<XWPFTable> itTable = xdoc.getTablesIterator();
		while (itTable.hasNext()) {
			XWPFTable table = (XWPFTable) itTable.next();
			int rcount = table.getNumberOfRows();
			for (int i = 0; i < rcount; i++) {
				XWPFTableRow row = table.getRow(i);
				List<XWPFTableCell> cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					List<XWPFParagraph> paragraphs = cell.getParagraphs();
					for (XWPFParagraph paragraph : paragraphs) {
						replaceParagraph(paragraph, map);
					}
				}
			}
		}
	}

	// 替换段落中的kv对
	private void replaceParagraph(XWPFParagraph paragraph,
			Map<String, Object> map) throws InvalidFormatException, IOException {
		String text = "";
		Set<String> set = map.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			List<XWPFRun> runs = paragraph.getRuns();
			for (int i = 0; i < runs.size(); i++) {
				if (StringUtils.isNotBlank(runs.get(i).getText(
						runs.get(i).getTextPosition()))) {
					text = runs.get(i).getText(runs.get(i).getTextPosition());
					if (text.indexOf(key) != -1) {
						// 文字
						if (map.get(key) instanceof String) {
							text = text.replace(key, (String) map.get(key));
							runs.get(i).setText(text, 0);

						}
						// 图片
						else if (map.get(key) instanceof WordImage) {
							text = text.replace(key, "");
							runs.get(i).setText(text, 0);
							WordImage image = (WordImage) map.get(key);
							int width = image.getWidth();
							int height = image.getHeight();
							int type = image.getType().getType();
							String picId = paragraph.getDocument()
									.addPictureData(
											new FileInputStream(
													image.getImage()), type);
							xdoc.createPicture(picId,
									xdoc.getNextPicNameNumber(type), width,
									height, paragraph, runs.get(i));
						}
					}
				}
			}
		}
	}
	
	/**
	 * word 2007 .docx 的文档转成HTML
	 * 
	 * 例子：
	 * imageSaveRootPath：	./src/main/webapp/images/app/word/
	 * serverRootUrl：		../images/app/word/
	 * 
	 * 那么图片将会存储在：	./src/main/webapp/images/app/word/wordpic/uuid/word/media/image1.png
	 * html中图片的src将会：	../images/app/word/wordpic/uuid/word/media/image1.png
	 * 
	 * 所以请尝试两个路径，以便让HTML能显示图片
	 * 
	 * @param xdocPath			文档路径
	 * @param imageSaveRootPath	文档中图片存储根路径，注意，图片名字和具体的路径不可控，我们只能控制图片存储的根路径
	 * @param serverRootUrl		生成的HTML中图片的src的根路径，注意，图片名字和具体的路径不可控，我们只能控制图片的根路径
	 * @return
	 */
	public String xdocToHtml(String xdocPath , String imageSaveRootPath , String imageSrcRootPath){
		ByteArrayOutputStream out = new ByteArrayOutputStream();  
		String nowDate = null;
		try {
			nowDate = MyDateUtil.getNowStringDate("yyyyMMdd");
		} catch (MySimpleDateFormatException e1) {
			log.error("ERROR:",e1);
		}
		String randomName = nowDate+"/"+UUIDUtil.getUUID();
		String imageSrcPath = imageSrcRootPath+"wordpic/"+randomName+"/";
		String imageSavePath = imageSaveRootPath+"/wordpic/"+randomName+"/";
		
		// 创建文件夹
		File dirF = new File(imageSavePath);
		if(!dirF.exists()||!dirF.isDirectory()){
			dirF.mkdir();
		}
		
		// xdoc2HTML
		try{
			XWPFDocument wordDocument = new XWPFDocument(new FileInputStream(xdocPath));
			XHTMLOptions options = XHTMLOptions.create().URIResolver(new BasicURIResolver(imageSrcPath));
			File imageFolderFile = new File(imageSavePath);
			options.setExtractor(new FileImageExtractor(imageFolderFile));
			XHTMLConverter.getInstance().convert(wordDocument, out, options);
	        out.close();  
		}catch(Exception e){
			e.printStackTrace();
		}
        return Jsoup.parse(new String(out.toByteArray())).html();
	}
	
	/**
	 * word2003 .doc 的文档转化成HTML
	 * 
	 * 例子：
	 * imageSaveRootPath：	./src/main/webapp/images/app/word/
	 * serverRootUrl：		../images/app/word/
	 * 
	 * 那么图片将会存储在：	./src/main/webapp/images/app/word/wordpic/uuid/xxx.png
	 * html中图片的src将会：	../images/app/word/wordpic/uuid/xxx.png
	 * 
	 * 所以请尝试两个路径，以便让HTML能显示图片
	 * 
	 * @param docPath
	 * @param imageSaveRootPath
	 * @param imageSrcRootPath
	 * @return
	 */
	public String doc2Html(String docPath , String imageSaveRootPath , String imageSrcRootPath) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();  
		String nowDate = null;
		try {
			nowDate = MyDateUtil.getNowStringDate("yyyyMMdd");
		} catch (MySimpleDateFormatException e1) {
			log.error("ERROR:",e1);
		}
		String randomName = nowDate+"/"+UUIDUtil.getUUID();
		final String imageSrcPath = imageSrcRootPath+"wordpic/"+randomName+"/";
		String imageSavePath = imageSaveRootPath+"/wordpic/"+randomName+"/";
		
		File dirF = new File(imageSavePath);
		if(!dirF.exists()||!dirF.isDirectory()){
			dirF.mkdir();
		}
		
		try{
			HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(docPath));
	        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());  
	        wordToHtmlConverter.setPicturesManager( new PicturesManager()         
	        {  
	             public String savePicture( byte[] content,  
	                     PictureType pictureType, String suggestedName,  
	                     float widthInches, float heightInches )  
	             {  
	                 return imageSrcPath+suggestedName;  
	             }  
	        });  
	        wordToHtmlConverter.processDocument(wordDocument);  
	        List<Picture> pics=wordDocument.getPicturesTable().getAllPictures();  
	        if(pics!=null){  
	            for(int i=0;i<pics.size();i++){  
	                Picture pic = (Picture)pics.get(i);  
	                try {  
	                    pic.writeImageContent(new FileOutputStream(imageSavePath + pic.suggestFullFileName()));  
	                } catch (FileNotFoundException e) {  
	                    e.printStackTrace();  
	                }    
	            }  
	        }  
	        Document htmlDocument = wordToHtmlConverter.getDocument();  
	        
	        DOMSource domSource = new DOMSource(htmlDocument);  
	        StreamResult streamResult = new StreamResult(out);  
	  
	        TransformerFactory tf = TransformerFactory.newInstance();  
	        Transformer serializer = tf.newTransformer();  
	        serializer.setOutputProperty(OutputKeys.ENCODING, "GB2312");  
	        serializer.setOutputProperty(OutputKeys.INDENT, "yes");  
	        serializer.setOutputProperty(OutputKeys.METHOD, "html");  
	        serializer.transform(domSource, streamResult);  
	        out.close();  
		}catch(Exception e){
			e.printStackTrace();
		}
        return Jsoup.parse(new String(out.toByteArray())).html();
	}  
	

	/**
	 * 定制的XWPFDocument
	 * 
	 * 定制了写入图片的功能
	 * 
	 * @author xiaoMzjm
	 *
	 */
	class CustomXWPFDocument extends XWPFDocument {
		public CustomXWPFDocument() {
			super();
		}

		public CustomXWPFDocument(InputStream in) throws IOException {
			super(in);
		}

		public CustomXWPFDocument(OPCPackage pkg) throws IOException {
			super(pkg);
		}

		public void createPicture(String blipId, int id, int width, int height,
				XWPFParagraph paragraph, XWPFRun run) {
			final int EMU = 9525;
			width *= EMU;
			height *= EMU;
			// String blipId =
			// getAllPictures().get(id).getPackageRelationship().getId();
			if (paragraph == null) {
				paragraph = createParagraph();
			}
			CTInline inline = null;
			if (run == null) {
				inline = paragraph.createRun().getCTR().addNewDrawing()
						.addNewInline();
			} else {
				inline = run.getCTR().addNewDrawing().addNewInline();
			}
			String picXml = ""
					+ "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
					+ "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
					+ "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
					+ "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
					+ id
					+ "\" name=\"img_"
					+ id
					+ "\"/>"
					+ "            <pic:cNvPicPr/>"
					+ "         </pic:nvPicPr>"
					+ "         <pic:blipFill>"
					+ "            <a:blip r:embed=\""
					+ blipId
					+ "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
					+ "            <a:stretch>"
					+ "               <a:fillRect/>"
					+ "            </a:stretch>"
					+ "         </pic:blipFill>"
					+ "         <pic:spPr>"
					+ "            <a:xfrm>"
					+ "               <a:off x=\"0\" y=\"0\"/>"
					+ "               <a:ext cx=\""
					+ width
					+ "\" cy=\""
					+ height
					+ "\"/>"
					+ "            </a:xfrm>"
					+ "            <a:prstGeom prst=\"rect\">"
					+ "               <a:avLst/>"
					+ "            </a:prstGeom>"
					+ "         </pic:spPr>"
					+ "      </pic:pic>"
					+ "   </a:graphicData>" + "</a:graphic>";
			// CTGraphicalObjectData graphicData =
			// inline.addNewGraphic().addNewGraphicData();
			XmlToken xmlToken = null;
			try {
				xmlToken = XmlToken.Factory.parse(picXml);
			} catch (XmlException xe) {
				xe.printStackTrace();
			}
			inline.set(xmlToken);
			// graphicData.set(xmlToken);
			inline.setDistT(0);
			inline.setDistB(0);
			inline.setDistL(0);
			inline.setDistR(0);
			CTPositiveSize2D extent = inline.addNewExtent();
			extent.setCx(width);
			extent.setCy(height);
			CTNonVisualDrawingProps docPr = inline.addNewDocPr();
			docPr.setId(id);
			docPr.setName("docx_img_ " + id);
			docPr.setDescr("docx Picture");
		}
	}


}
