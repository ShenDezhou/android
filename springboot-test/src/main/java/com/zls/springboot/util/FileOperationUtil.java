package com.zls.springboot.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 文件处理工具.
 * 
 * @author 承项
 * @date 2015年9月28日下午1:20:30
 */
public class FileOperationUtil {

	private final static Logger logger = LogManager
			.getLogger(FileOperationUtil.class);

	/**
	 * 写文件到磁盘.
	 * 
	 * @param in
	 *            文件输入流
	 * @param dest
	 *            目的文件
	 * @throws Exception
	 */
	public static void wirteToFile(InputStream in, File dest) throws Exception {
		BufferedOutputStream buffer = null;
		int count = 0;
		byte[] b = null;

		try {
			b = new byte[2048];
			buffer = new BufferedOutputStream(new FileOutputStream(dest));
			while ((count = in.read(b)) != -1) {
				buffer.write(b, 0, count);
			}
			buffer.close();
		} catch (Exception e) {
			logger.error("wirteToFile error,file={}.", dest);
			throw e;
		}
	}

	/**
	 * 删除文件.
	 * 
	 * @param target
	 *            目标文件
	 */
	public static void deleteFile(File target) {
		if (target == null) {
			logger.warn("待删除文件对象target为null.");
			return;
		}
		try {
			if (target.exists()) {
				if (target.isDirectory()) {
					// 目录
					File[] fileLists = target.listFiles();
					for (File file : fileLists) {
						// 递归删除
						deleteFile(file);
					}
				} else {
					target.delete();
				}
			} else {
				// 文件不存在
				logger.warn("待删除对象,在磁盘上并不存在,target={}.", target);
			}

		} catch (Exception e) {
			logger.error("deleteFile error,file={}.", target);
			throw e;
		}
	}

	/**
	 * 删除文件.
	 */
	public static void deleteFile(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			logger.warn("待删除文件路径非法,filePath={}.", filePath);
			return;
		}
		deleteFile(new File(filePath));
	}

	/**
	 * 根据文件originName截取文件类型.
	 * 
	 * @param originName
	 *            文件在原client fileSystem的名称.
	 */
	public static String getTypeByOriginName(String originName) {
		String fileType = null;

		if (StringUtils.isBlank(originName)) {
			fileType = "jpg";
		} else {
			if (originName.lastIndexOf("\\.") != -1) {
				fileType = originName.split("\\.")[1];
			}
		}
		if (StringUtils.isBlank(fileType)) {
			fileType = "jpg";
		}

		return fileType;
	}
}
