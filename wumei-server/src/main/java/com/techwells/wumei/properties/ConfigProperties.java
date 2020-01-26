package com.techwells.wumei.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 本项目的所有配置信息
 * @author 爱撒谎的男孩
 */
@ConfigurationProperties(prefix="project.config")
@Data
public class ConfigProperties {
	
	/**
	 * 环信服务器
	 */
	private String API_SERVER_HOST;
	
	/**
	 * appkey
	 */
	private String APPKEY ;
	
	/**
	 * 公钥
	 */
	private String APP_CLIENT_ID;
	
	/**
	 * 私钥
	 */
	private String APP_CLIENT_SECRET;
	
	
	
	
}
