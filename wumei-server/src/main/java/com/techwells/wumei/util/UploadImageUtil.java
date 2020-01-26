package com.techwells.wumei.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
//import java.io.UnsupportedEncodingException;

import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
//import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import net.coobird.thumbnailator.Thumbnails;
//import net.coobird.thumbnailator.geometry.Position;

public class UploadImageUtil {

	private static String accessKey = "zZfrkIVyE9GGtMjcYxtyIkBA8-0UyBgA9rh3Qbv_"; // 七牛云的密钥
	private static String secretKey = "u4U7YIcRdnuho69r-7r-BN2BBTC-JUHckkCwjH0z";
	private static String bucket = "blog-img"; // 存储库的名称
	// private static String domain = "http://ono60m7tl.bkt.clouddn.com/"; // 域名
	private static Zone location = Zone.zone2(); // 存储空间的地域

	/**
	 * 生成缩略图
	 * 
	 * @param file
	 *            MultipartFile文件类型
	 * @param width
	 *            宽度
	 * @param height
	 *            长度
	 * @param uploadUrl
	 *            图片服务器的url
	 * @param realUploadPath
	 *            文件保存在服务器的地址
	 * @return
	 */
	public static String thumbnailUploadImage(MultipartFile file, int width,
			int height, String uploadUrl, String realUploadPath) {
		String des = realUploadPath + "thum/"; // 在服务器实际存储的文件夹
		String fileName = System.currentTimeMillis()
				+ file.getOriginalFilename();
		File targetFile = new File(des, fileName);
		// 如果文件夹不存在，那么就创建
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs(); // 创建文件夹
		}

		try {
			Thumbnails.of(file.getInputStream()).size(width, height)
					.toFile(targetFile.getPath()); // 将缩略图存储在指定路径
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uploadUrl + "/thum/" + fileName; // 返回的是图片在服务器上的url
	}

	/**
	 * 生成上传图片的缩略图
	 * 
	 * @param fileName
	 *            文件的名称，带后缀
	 * @param inputStream
	 *            文件的输入流
	 * @param width
	 *            缩略图的宽度
	 * @param height
	 *            缩略图的高度
	 * @param uploadUrl
	 *            服务器上的url，即是能够网上访问的链接
	 * @param realUploadPath
	 *            存储在服务器地址，即是缩略图片在服务器的本地存储的位置
	 * @return 返回网上访问的真实路径
	 */
	public static String thumbnailUploadImage(String fileName,
			InputStream inputStream, int width, int height, String uploadUrl,
			String realUploadPath) {
		String des = realUploadPath + "thum/"; // 在服务器实际存储的文件夹
		File targetFile = new File(des, fileName);
		// 如果文件夹不存在，那么就创建
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs(); // 创建文件夹
		}
		try {
			Thumbnails.of(inputStream).size(width, height)
					.toFile(targetFile.getPath()); // 将缩略图存储在指定路径
			System.out.println(targetFile.getPath() + "---");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uploadUrl + "/thum/" + fileName; // 返回的是图片在服务器上的url
	}

	/**
	 * 上传文件到七牛云
	 * 
	 * @param uploadBytes
	 * @param fileName
	 * @throws Exception
	 */
	public static void uploadToQiuNiu(byte[] uploadBytes, String fileName)
			throws Exception {

		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(location);
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		// ...生成上传凭证，然后准备上传
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = fileName; // 文件名称
		// byte[] b = "hello qiniu cloud".getBytes("utf-8");
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		Response response = uploadManager.put(uploadBytes, key, upToken);
		// 解析上传成功的结果
		DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),
				DefaultPutRet.class);
		System.out.println(putRet.key);
		System.out.println(putRet.hash);
	}

	/**
	 * 上传图片方法
	 *
	 * @param dirName 文件夹名
	 * @param files   图片数组
	 * @return String
	 */
	public static String uploadImage(String dirName, MultipartFile[] files) {
		StringBuilder fileUrl = new StringBuilder();
		for (MultipartFile file : files) {
			try {
				fileUrl.append(FileUtil.uploadFile(file, dirName)).append(",");
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		if (!"".equals(fileUrl.toString())) {
			fileUrl = new StringBuilder(fileUrl.substring(0, fileUrl.length() - 1));

		}
		return fileUrl.toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("ce");
		UploadImageUtil.uploadToQiuNiu("chen".getBytes(), "12334455");
	}
}
