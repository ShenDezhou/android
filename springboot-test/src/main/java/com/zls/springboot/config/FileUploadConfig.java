package com.zls.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件上传相关属性.
 * @author 承项
 * @date 2015年9月28日上午11:45:48
 */
@ConfigurationProperties(prefix="fileupload")
@Component
public class FileUploadConfig {
	
	private boolean temp; // 是否为临时文件
	private String savePath; // 文件存储路径
	
	public boolean isTemp() {
		return temp;
	}
	public void setTemp(boolean temp) {
		this.temp = temp;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
}
