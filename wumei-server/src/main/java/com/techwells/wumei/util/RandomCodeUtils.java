package com.techwells.wumei.util;

import java.util.Random;

/**
 * 随机字符串生成工具类
 * 
 * @author penghy
 * @date 2014-02-27
 */
public abstract class RandomCodeUtils {

	/**
	 * @description 随机生成一个n位验证码,并拼接当前毫秒值
	 * 
	 * @param length
	 *            表示生成多少位
	 * 
	 * */

	public static String getMSRandom(int length) {

		String code = "";
		Random random = new Random();

		// 参数length，表示生成几位随机数
		for (int i = 0; i < length; i++) {

			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				code += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				code += String.valueOf(random.nextInt(10));
			}
		}

		return System.currentTimeMillis() + code;
	}

	/**
	 * @description 随机生成一个n位数字随机码
	 * 
	 * @param length
	 *            表示生成多少位
	 * 
	 * */

	public static String getNumberRandom(int length) {

		String code = "";
		Random random = new Random();

		// 参数length，表示生成几位随机数
		for (int i = 0; i < length; i++) {
			code += random.nextInt(10);
		}

		return code;
	}

	/**
	 * @description 随机生成一个n位随机码（数字加字母）
	 * 
	 * @param length
	 *            表示生成多少位
	 * 
	 * */

	public static String getStringRandom(int length) {

		String code = "";
		Random random = new Random();

		// 参数length，表示生成几位随机数
		for (int i = 0; i < length; i++) {

			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				code += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				code += String.valueOf(random.nextInt(10));
			}
		}

		return code;
	}

	public static void main(String[] args) {
		System.out.println(getStringRandom(4));
		System.out.println(getNumberRandom(4));
		System.out.println(getMSRandom(4));
	}
}
