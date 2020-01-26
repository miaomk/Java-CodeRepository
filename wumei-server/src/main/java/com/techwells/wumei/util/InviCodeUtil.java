package com.techwells.wumei.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InviCodeUtil{
	//静态变量存储最大值
	@SuppressWarnings("unused")
	private static final AtomicInteger atomicNum = new AtomicInteger();
    public static List<String> personUtil(){
		 String suffix = "U";
		 String shuju = null;
		 List<String> array = new ArrayList<String>();
		 for(int atomicNum = 0; atomicNum < 10000; atomicNum++ ){
			 String format = String.format("%05d", atomicNum); 
			 shuju = suffix+format;
			 array.add(shuju);
		 }
		return array;
   }
    
    public static List<String> merchUtil(){
		 String suffix = "B";
		 String shuju = null;
		 List<String> array = new ArrayList<String>();
		 for(int atomicNum = 0; atomicNum < 10000; atomicNum++ ){
			 String format = String.format("%05d", atomicNum); 
			 shuju = suffix+format;
			 array.add(shuju);
		 }
		return array;
  }
    
    
}
