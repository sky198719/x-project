/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UpLoadUtil {
	
    static Logger log = LoggerFactory.getLogger(UpLoadUtil.class);

	public static final String  upLoadPath = "/static/image/uploadFile";	//上传路径
	public static final Long limitSize = 1024 * 1024 * 5l;	//最大大小限制5M

	//上传多个文件 @author aiden at 2016-10-31 14:26:40
	public  JSONObject uploadFile(HttpServletRequest request, String newFileName) throws IOException {
		JSONObject resultJson = new JSONObject();
		List<String> filePathList = new ArrayList<String>();

		String realPath = request.getSession().getServletContext().getRealPath(upLoadPath);
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
		List<MultipartFile> files = multiValueMap.get("file");//与form表单中name保持一致
		for (int i = 0; i < files.size(); i++) {
			// 重命名文件
			String fName = files.get(i).getOriginalFilename();
			String fileFix = fName.substring(fName.lastIndexOf('.') + 1, fName.length());
			String postfix = "jpg,gif,png,bmp,jpeg,JPG,GIF,PNG,BMP,JPEG";
			if (postfix.indexOf(fileFix) == -1) {
				resultJson.put("msg", "不能上传图片后缀为" + fileFix + "的图片文件，请重新上传");
				resultJson.put("resultCode", -1);
				return resultJson;
			}
			// 检查文件大小
			if (files.get(i).getSize() > limitSize) {
				resultJson.put("msg", "上传文件的大小超出限制,最大不能超过5M");
				resultJson.put("resultCode", -1);
				return resultJson;
			}
			//如果没有指定用户名，使用uuid作为用户名
			String fileName;
			if(newFileName == null){
				String uuid = UUID.randomUUID().toString();
				fileName = uuid + "." + fileFix;
			}else{
				fileName = newFileName + "." + fileFix;
			}
			File newFile = new File(realPath, fileName);
			if (newFile.exists()) {
				newFile.delete();
			}
			FileUtils.copyInputStreamToFile(files.get(i).getInputStream(), newFile);
			filePathList.add(fileName);
		}
		resultJson.put("resultCode", 0);
		resultJson.put("data", filePathList);
		resultJson.put("msg", "上传成功");
		return resultJson;
	}
	
}
