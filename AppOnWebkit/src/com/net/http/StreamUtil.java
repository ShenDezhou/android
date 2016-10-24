package com.net.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class StreamUtil {

	/**
	 * 输入流转换为数组
	 * @param is
	 * @return
	 */
	public static byte[] parseBytes(InputStream is){
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		byte[] bytes = new byte[1024];
		
		int len = 0;
		
		try {
			
			while ((len = is.read(bytes)) != -1) {
				baos.write(bytes, 0, len);
			}
			baos.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return baos.toByteArray();
	}
	/**
	 *  数组转换为String
	 * @param bytes
	 * @return
	 */
	public static String parseString(byte[] bytes){
		
		return new String(bytes);
	}
	
	public static String parseString(InputStream is){
		byte[] bytes = parseBytes(is);
		return new String(bytes);
	}
	/**
	 *  数组转换为Bitmap
	 * @param bytes
	 * @return
	 */
	public static Bitmap parseBitmap(byte[] bytes){
		
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}
	
	public static Bitmap parseBitmap(InputStream is){
		byte[] bytes = parseBytes(is);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}
}
