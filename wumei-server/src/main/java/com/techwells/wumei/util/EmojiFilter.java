package com.techwells.wumei.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class EmojiFilter {
	/**
     * emoji表情替换
     *
     * @param source 原字符串
     * @param slipStr emoji表情替换成的字符串                
     * @return 过滤后的字符串
     */
	public static String filterEmoji(String source) {
	    if(StringUtils.isNotBlank(source)){
	        return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
	    }else{
	        return source;
	    }
	}
	
	public static String filterEmoji2(String source) {  
	    if(source != null)
	    {
	        Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
	        Matcher emojiMatcher = emoji.matcher(source);
	        if ( emojiMatcher.find()) 
	        {
	            source = emojiMatcher.replaceAll("*");
	            return source ; 
	        }
	    return source;
	   }
	   return source;  
	}
	
	public static void main(String[] args) {
		String s = "<body>口口213这是一个有各种内容的消息,  Hia Hia Hia !!!! xxxx@@@...*)!$&@(&#!)@*)!&$!)@^%@(!&#. 口口口], ";
		System.out.println(filterEmoji(s));
		System.out.println( filterEmoji2(s));
	}
}
