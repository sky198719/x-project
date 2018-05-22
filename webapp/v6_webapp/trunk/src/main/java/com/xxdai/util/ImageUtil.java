/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImageUtil {
	
    static Logger log = LoggerFactory.getLogger(ImageUtil.class);

	public static final String upLoadPath = "/static/image/uploadFile";	//上传路径
	public static final int scaleRatio = 4;	//压缩比例


	//压缩图片并上传图片，压缩后图片名称默认为：【原图片名_scaled.原格式】  @author aiden at 2016-10-31 14:26:40
	public  JSONObject scaleImage(HttpServletRequest request, String fileName) throws IOException {
		JSONObject resultJson = new JSONObject();

		String realPath = request.getSession().getServletContext().getRealPath(upLoadPath);
		String fileFix = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());//后缀名
		String newFileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_scaled." + fileFix;
		String newUrl = realPath + System.getProperty("file.separator") + newFileName;

		try{
			java.io.File file = new java.io.File(realPath + System.getProperty("file.separator") + fileName);
			Image img = javax.imageio.ImageIO.read(file);
			int height = img.getHeight(null) / scaleRatio;
			int width = height * img.getWidth(null) / img.getHeight(null);//原比例缩放

			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(img, 0, 0, width, height, null);        //绘制缩小后的图
			FileOutputStream newimage = new FileOutputStream(newUrl);           //输出到文件流
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			encoder.encode(tag);                                                //近JPEG编码
			newimage.close();

			resultJson.put("resultCode", 0);
			resultJson.put("data", newFileName);
			resultJson.put("msg", "操作成功!");
		}catch(Exception e){
			log.error(MessageFormat.format("图片压缩失败！file：{0}", fileName));
			resultJson.put("resultCode", -1);
			resultJson.put("msg", "操作失败!");
		}

		return resultJson;
	}
	
}
