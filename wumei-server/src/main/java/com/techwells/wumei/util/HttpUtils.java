package com.techwells.wumei.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;

import com.google.gson.Gson;

public class HttpUtils {

    /**
     * 将get请求返回的json内容封装到Map中返回
     * @param url  请求的url
     * @return   Map
     * @throws Exception
     */
    public static Map<String,Object> doGet(String url, String token)throws  Exception{
        HttpClient client=new DefaultHttpClient();  //创建HttpClient      
        
        HttpGet get=new HttpGet(URI.create(url));  //创建HttpGet
        get.addHeader("Content-Type","application/json");
        get.addHeader("Authorization",token);
        HttpResponse response=client.execute(get);  //执行get请求
        //如果请求成功
        if (response.getStatusLine().getStatusCode()==200){
            HttpEntity entity=response.getEntity();  //获取实体返回的内容
            BufferedReader reader=new BufferedReader(new InputStreamReader(entity.getContent()));  //创建缓存流读取返回的内容
            StringBuilder sb=new StringBuilder();
            //一行一行读取
            for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                sb.append(temp);
            }
            Gson gson=new Gson();  //创建Gosn操作Json
            return gson.fromJson(sb.toString(),Map.class);  //转换Json字符串到Map中
        }
        return null;
    }


    /**
     * 发送Post请求，请求的参数是xml
     * @param url  链接
     * @param requestParamxml  xml参数
     * @return
     * @throws IOException
     */
    public static Map<String,Object> doPost(String url,String requestParamxml, String token) throws IOException {

        HttpClient client=new DefaultHttpClient();
        HttpPost post=new HttpPost(URI.create(url));   //创建Post请求

        //设置请求配置
        RequestConfig config=RequestConfig.custom()
                .setConnectionRequestTimeout(150000)  //连接请求时间
                .setConnectTimeout(150000)      //连接服务器主机超时时间
                .setSocketTimeout(60000)   //设置读取数据的响应时间
                .build();
        post.setConfig(config);
                

        //设置请求实体
        post.setEntity(new StringEntity(requestParamxml,"UTF-8"));

        //添加头信息，告诉浏览器传入的参数是xml格式的
        //post.addHeader(HTTP.CONTENT_TYPE,"text/xml");
        post.addHeader("Content-Type","application/json");
        post.addHeader("Authorization",token);
        
        post.addHeader("Accept","application/json");

        

        //执行请求
        HttpResponse response=client.execute(post);

        //如果响应成功，返回数据
        if (response.getStatusLine().getStatusCode()==200){
            HttpEntity entity=response.getEntity();  //获取响应返回的内容
            BufferedReader reader=new BufferedReader(new InputStreamReader(entity.getContent()));  //创建缓存流读取返回的内容
            StringBuilder sb=new StringBuilder();
            //一行一行读取
            for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                sb.append(temp);
            }
            Gson gson=new Gson();  //创建Gosn操作Json
            return gson.fromJson(sb.toString(),Map.class);  //转换Json字符串到Map中
        }
        return null;
    }
    
    
    
    public static Map<String,Object> doPut(String url,String requestParamxml, String token) throws IOException {

        HttpClient client=new DefaultHttpClient();
        HttpPut put=new HttpPut(URI.create(url));   //创建Post请求

        //设置请求配置
        RequestConfig config=RequestConfig.custom()
                .setConnectionRequestTimeout(150000)  //连接请求时间
                .setConnectTimeout(150000)      //连接服务器主机超时时间
                .setSocketTimeout(60000)   //设置读取数据的响应时间
                .build();
        put.setConfig(config);
                

        //设置请求实体
        put.setEntity(new StringEntity(requestParamxml,"UTF-8"));

        //添加头信息，告诉浏览器传入的参数是xml格式的
        //post.addHeader(HTTP.CONTENT_TYPE,"text/xml");
        put.addHeader("Content-Type","application/json");
        put.addHeader("Authorization",token);
        
        put.addHeader("Accept","application/json");

        //执行请求
        HttpResponse response=client.execute(put);

        //如果响应成功，返回数据
        if (response.getStatusLine().getStatusCode()==200){
            HttpEntity entity=response.getEntity();  //获取响应返回的内容
            BufferedReader reader=new BufferedReader(new InputStreamReader(entity.getContent()));  //创建缓存流读取返回的内容
            StringBuilder sb=new StringBuilder();
            //一行一行读取
            for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                sb.append(temp);
            }
            Gson gson=new Gson();  //创建Gosn操作Json
            return gson.fromJson(sb.toString(),Map.class);  //转换Json字符串到Map中
        }
        return null;
    }
    
    
    public static Map<String,Object> doDelete(String url, String token) throws IOException {

        HttpClient client=new DefaultHttpClient();
        HttpDelete delete =new HttpDelete(URI.create(url));   //创建Post请求

        //设置请求配置
        RequestConfig config=RequestConfig.custom()
                .setConnectionRequestTimeout(150000)  //连接请求时间
                .setConnectTimeout(150000)      //连接服务器主机超时时间
                .setSocketTimeout(60000)   //设置读取数据的响应时间
                .build();
        delete.setConfig(config);
               
        //设置请求实体
        //添加头信息，告诉浏览器传入的参数是xml格式的
        //post.addHeader(HTTP.CONTENT_TYPE,"text/xml");
        delete.addHeader("Content-Type","application/json");
        delete.addHeader("Authorization",token);
       
        

        //执行请求
        HttpResponse response=client.execute(delete);

        //如果响应成功，返回数据
        if (response.getStatusLine().getStatusCode()==200){
            HttpEntity entity=response.getEntity();  //获取响应返回的内容
            BufferedReader reader=new BufferedReader(new InputStreamReader(entity.getContent()));  //创建缓存流读取返回的内容
            StringBuilder sb=new StringBuilder();
            //一行一行读取
            for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                sb.append(temp);
            }
            Gson gson=new Gson();  //创建Gosn操作Json
            return gson.fromJson(sb.toString(),Map.class);  //转换Json字符串到Map中
        }
        return null;
    }
    
    /**
	 * 获取用户机的实际IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "192.168.0.0";
		}
		return ip;
	}

	
	/**
	 * post请求
	 * @param url  请求地址
	 * @param jsonParams  json格式的字符串数据
	 * @return 返回Map格式的数据（由json格式转换的）
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static Map<String, Object> doPostReturnMap(String url,String jsonParams) throws ClientProtocolException, IOException{
		CloseableHttpClient client=HttpClients.createDefault();
        HttpPost post=new HttpPost(URI.create(url));   //创建Post请求

        //设置请求配置
        RequestConfig config=RequestConfig.custom()
                .setConnectionRequestTimeout(150000)  //连接请求时间
                .setConnectTimeout(150000)      //连接服务器主机超时时间
                .setSocketTimeout(60000)   //设置读取数据的响应时间
                .build();
        post.setConfig(config);

        StringEntity entity=new StringEntity(jsonParams,"UTF-8");
//        entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));   //这里必须指定
//        entity.setContentType("application/json");   //设置实体的文本格式
        post.setHeader(HTTP.CONTENT_TYPE, "application/json");
        //设置请求实体
        post.setEntity(entity);

        //执行请求
        HttpResponse response=client.execute(post);
        
        System.out.println("staus="+response.getStatusLine().getStatusCode());
        
        //如果响应成功，返回数据
        if (response.getStatusLine().getStatusCode()==200){
            HttpEntity httpEntity=response.getEntity();  //获取响应返回的内容
            BufferedReader reader=new BufferedReader(new InputStreamReader(httpEntity.getContent()));  //创建缓存流读取返回的内容
            StringBuilder sb=new StringBuilder();
            //一行一行读取
            for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                sb.append(temp);
            }
            return new Gson().fromJson(sb.toString(), Map.class);  //直接返回字符串
        }
        return null;  //如果响应失败，返回null
	}
	
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	
}
