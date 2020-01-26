package com.techwells.wumei.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContextException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64编码的工具类
 * 
 * @author 陈加兵
 *
 */
@SuppressWarnings("restriction")
public class Base64Util {

	// 截取视频的插件位置
	private static String ffmpegLocation = "C:\\Users\\Administrator\\Downloads\\ffmpeg-4.0-win64-static\\bin\\ffmpeg.exe";

	public static void main(String[] args) throws Exception {
		// String
		// videoPath="C:\\Users\\Administrator\\Videos\\01e7b8b10c2a7e1c80e9b25f1fc3e303.mp4";
		// String targetPath="C:\\Users\\Administrator\\Videos\\Proecess\\";
		// byte[] data=GenerateImage(GetBase64(videoPath));
		// Map<String, Object> map=saveVideoToLocal(data,targetPath);
		// System.out.println(map.get("videoLength"));
		// System.out.println(map.get("firstFrame"));
		String imagePath = "C:\\Users\\Administrator\\Pictures\\1533695522337.jpg";
		String target = "C:\\Users\\Administrator\\Pictures\\thum\\";
		System.out.println(GetBase64(imagePath));
		saveImageToLocal(GenerateImage(GetBase64(imagePath)), target);

	}

	/**
	 * 将指定位置的图片或者视频编码成base64
	 * 
	 * @param filePath
	 * @return 返回编码后的字符串
	 */
	public static String GetBase64(String filePath) {
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(filePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符
	}

	/**
	 * 将编码之后字符串生成byte
	 * 
	 * @param imgStr
	 *            base编码的字符串
	 * @return
	 */
	public static byte[] GenerateImage(String imgStr) throws Exception {
		// 对字节数组字符串进行Base64解码并生成图
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b = null;
		try {
			b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("error");
		}
		return b;
	}

	/**
	 * 保存图片到本地
	 * 
	 * @param data
	 *            编码转换后的byte数组
	 * @param path
	 *            图片存储的路径
	 * @return Map : 结果集
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map saveImageToLocal(byte[] data, String path)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>(); // 用于存储返回的结果集
		String fileName = System.currentTimeMillis() + ".jpg"; // 图片的名称
		File file = new File(path, fileName);
		// 创建文件夹
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		// 创建输出流，保存图片到本地
		FileOutputStream outputStream = new FileOutputStream(file);
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();

		// 生成缩略图
		String showImage = UploadImageUtil.thumbnailUploadImage(fileName,
				new FileInputStream(file), 100, 100, "www.baid", path);
		map.put("showImage", showImage);
		map.put("imageRealPath", file.getAbsolutePath());
		return map;
	}

	@SuppressWarnings("rawtypes")
	public static Map saveVideoToLocal(byte[] data, String path)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>(); // 用于存储返回的结果集
		String fileName = System.currentTimeMillis() + ".mp4"; // 图片的名称
		File file = new File(path, fileName);
		// 创建文件夹
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		// 创建输出流，保存图片到本地
		FileOutputStream outputStream = new FileOutputStream(file);
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();

		// 获取视频的时长
		String videoLength = VideoUtil.getVideoLength(file);

		// 截取视频第一帧
		String firstFrame = VideoUtil
				.processImg(file.getPath(), ffmpegLocation);

		map.put("videoLength", videoLength);
		map.put("firstFrame", firstFrame);
		return map;
	}

}