package com.techwells.wumei.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装WangEditor的结果集
 * @author 陈加兵
 */
public class WangEditorDomin {
	private String errno="0";  //状态码，上传成功返回0，否则返回任意即可
	private List<String> data=new ArrayList<String>();   //封装返回图片的地址
	
	public String getErrno() {
		return errno;
	}
	public void setErrno(String errno) {
		this.errno = errno;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "WangEditorDomin [errno=" + errno + ", data=" + data + "]";
	}
	
	
}
