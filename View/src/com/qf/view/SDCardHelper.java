package com.qf.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.StatFs;

public class SDCardHelper {
	// �ж�SD���Ƿ񱻹���
	public static boolean isSDCardMounted() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	// ��ȡSD���ĸ�Ŀ¼
	public static String getSDCardBaseDir() {
		if (isSDCardMounted()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		} else
			return null;
	}

	// ��ȡSD��������ռ��С
	public static long getSDCardSize() {
		if (isSDCardMounted()) {
			StatFs fs = new StatFs(getSDCardBaseDir());
			long count = fs.getBlockCount();
			long size = fs.getBlockSize();
			return count * size / 1024 / 1024;
		}
		return 0;
	}
	public static long getSDCardFreeSize() {
		if (isSDCardMounted()) {
			StatFs fs = new StatFs(getSDCardBaseDir());
			long count = fs.getFreeBlocks();
			long size = fs.getBlockSize();
			return count * size / 1024 / 1024;
		}
		return 0;
	}
	public static long getSDCardAvailableSize() {
		if (isSDCardMounted()) {
			StatFs fs = new StatFs(getSDCardBaseDir());
			long count = fs.getAvailableBlocks();
			long size = fs.getBlockSize();
			return count * size / 1024 / 1024;
		}
		return 0;
	}
	public static boolean saveFileToSDCardPublicDir(byte[] data, String type,
			String fileName) {
		if (isSDCardMounted()) {
			BufferedOutputStream bos = null;
			File file = Environment.getExternalStoragePublicDirectory(type);
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						file, fileName)));
				bos.write(data);
				bos.flush();
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return false;
	}
	public static boolean saveFileToSDCardCustomDir(byte[] data, String dir,
			String fileName) {
		if (isSDCardMounted()) {
			BufferedOutputStream bos = null;
			File file = new File(getSDCardBaseDir() + File.separator + dir);
			if (!file.exists()) {
				file.mkdirs();
			}
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						file, fileName)));
				bos.write(data);
				bos.flush();
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return false;
	}
	public static boolean saveFileToSDCardPrivateFilesDir(byte[] data, String type,
			String fileName,Context context){
		if (isSDCardMounted()) {
			BufferedOutputStream bos = null;
			File file = context.getExternalFilesDir(type);
			if (!file.exists()) {
				file.mkdirs();// �ݹ鴴���Զ���Ŀ¼
			}
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						file, fileName)));
				bos.write(data);
				bos.flush();
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return false;
		
	}
	public static boolean saveFileToSDCardPrivateCacheDir(byte[] data,
			String fileName,Context context){
		if (isSDCardMounted()) {
			BufferedOutputStream bos = null;
			File file = context.getExternalCacheDir();
			if (!file.exists()) {
				file.mkdirs();
			}
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						file, fileName)));
				bos.write(data);
				bos.flush();
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if(bos!=null){
					bos.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return false;
	}
	public static byte[] loadFileFromSDCard(String fileDir){
		BufferedInputStream bis=null;
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		try {
			bis =new BufferedInputStream(new FileInputStream(new File(fileDir)));
			byte [] buffer=new byte[8*1024];
			int len=0;
			while((len=bis.read(buffer))!=-1){
				baos.write(buffer, 0, len);
				baos.flush();
			}
			return baos.toByteArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				baos.close();
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public static String getSDCardPublicDir(String type){
		return Environment.getExternalStoragePublicDirectory(type).toString();
	}
	public static String getSDCardPirvateCacheDir(Context context){
		return context.getExternalCacheDir().getAbsolutePath();
	}
	public static String getSDCardPirvateFilesDir(Context context,String type){
		return context.getExternalFilesDir(type).getAbsolutePath();
	}
}
