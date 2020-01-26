package com.techwells.wumei.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil {
	/**
	 * 文件上传
	 * 
	 * @param file
	 *            待上传文件，非空
	 * @param filterName
	 *            上传服务器指定文件夹名称，非空
	 * @return url
	 */
	public static String uploadFile(MultipartFile file, String filterName)
			throws IllegalStateException, IOException {
		String path = WuMeiConstants.UPLOAD_PATH + filterName;
		String fileName = file.getOriginalFilename();
		String filePath = System.currentTimeMillis() + fileName;
		File targetFile = new File(path);
		if (!targetFile.isDirectory()) {
			targetFile.mkdirs();
		}
		file.transferTo(new File(targetFile, filePath));
		return WuMeiConstants.UPLOAD_URL + filterName + "/"
				+ filePath;
	}

}
