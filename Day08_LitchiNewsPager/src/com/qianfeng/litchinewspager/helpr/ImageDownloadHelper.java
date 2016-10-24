package com.qianfeng.litchinewspager.helpr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StatFs;
import android.util.LruCache;
import android.widget.ImageView;

public class ImageDownloadHelper {
	public final String TAG = "ImageDownloaderHelper";
	public Map<String, SoftReference<Bitmap>> softCaches = new LinkedHashMap<String, SoftReference<Bitmap>>();
	public LruCache<String, Bitmap> lruCache = null;
	public static ImageDownloadHelper downloadHelper;
	private String imageCachePath = "";

	public static ImageDownloadHelper getInstance() {
		if (downloadHelper == null) {
			downloadHelper = new ImageDownloadHelper();
		}
		return downloadHelper;
	}

	public interface OnImageDownloadListener {
		void onImageDownload(Bitmap bitmap, String imgUrl);
	}

	public ImageDownloadHelper() {
		int memoryAmount = (int) Runtime.getRuntime().maxMemory();
		// ��ȡʣ���ڴ��8��֮һ��Ϊ����
		int cacheSize = memoryAmount / 8;
		if (lruCache == null) {
			lruCache = new MyLruCache(cacheSize);
		}
	}

	// �첽����ͼƬ����
	@SuppressLint("NewApi")
	public void downloadImage(Context context, String url, ImageView imageView,
			int width, OnImageDownloadListener downloadListener) {
		Bitmap bitmap = null;
		// �ȴ�ǿ������������
		if (lruCache != null) {
			bitmap = lruCache.get(url);
		}
		if (bitmap != null && url.equals(imageView.getTag())) {
			// Log.i(TAG, "--->��ǿ�������ҵ�����" + bitmap.toString());
			imageView.setImageBitmap(bitmap);
		} else {
			SoftReference<Bitmap> softReference = softCaches.get(url);
			if (softReference != null) {
				bitmap = softReference.get();
			}
			// ����������������
			if (bitmap != null && url.equals(imageView.getTag())) {
				// Log.i(TAG, "--->�����������ҵ�����" + bitmap.toString());
				imageView.setImageBitmap(bitmap);

				// ��ӵ�ǿ������
				bitmap = lruCache.put(url, bitmap);
				if (bitmap != null) {
					// �������ü������Ƴ�
					softCaches.remove(url);
				}
			} else {
				// ���ļ�������������
				if (url != null) {
					String imageName = "";
					imageName = getImageName(url);
					String cacheBasePath = SDCardHelper
							.getSDCardPrivateCacheDir(context);
					imageCachePath = cacheBasePath + File.separator + imageName;
					if (new File(imageCachePath).exists()) {
						bitmap = SDCardHelper
								.loadBitmapFromSDCard(imageCachePath);

						if (bitmap != null && url.equals(imageView.getTag())) {
							// Log.i(TAG, "--->���ļ��������ҵ�����" + bitmap.toString());
							Bitmap bitmap_thumb = BitmapThumbnailHelper
									.createThumbnail(imageCachePath, width, 0);
							imageView.setImageBitmap(bitmap_thumb);
							// ����ǿ����
							lruCache.put(url, bitmap_thumb);
						} else {
							new MyAsyncTask(context, url, imageView, width,
									downloadListener).execute(url);
						}
					} else {
						new MyAsyncTask(context, url, imageView, width,
								downloadListener).execute(url);
					}
				}
			}
		}
	}

	// �첽������
	class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
		private Context context;
		private ImageView mImageView;
		private String url;
		private OnImageDownloadListener downloadListener;
		private int width;// ����ͼ�Ŀ��

		public MyAsyncTask(Context context, String url, ImageView mImageView,
				int width, OnImageDownloadListener downloadListener) {
			this.context = context;
			this.url = url;
			this.mImageView = mImageView;
			this.width = width;
			this.downloadListener = downloadListener;
		}

		@SuppressLint("NewApi")
		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bm = null;
			try {
				String urlString = params[0];
				URL urlObj = new URL(urlString);
				HttpURLConnection httpConn = (HttpURLConnection) urlObj
						.openConnection();
				httpConn.setDoInput(true);
				httpConn.setRequestMethod("GET");
				httpConn.setConnectTimeout(5000);
				httpConn.connect();
				if (httpConn.getResponseCode() == 200) {
					InputStream is = httpConn.getInputStream();
					bm = BitmapFactory.decodeStream(is);
				}
				if (bm != null) {
					String imageName = getImageName(urlString);

					String cacheBasePath = SDCardHelper
							.getSDCardPrivateCacheDir(context);
					imageCachePath = cacheBasePath + File.separator + imageName;

					boolean flag = SDCardHelper
							.saveBitmapToSDCardPrivateCacheDir(bm, imageName,
									context);
					if (flag) {
						// Log.i(TAG, "--->���������ҵ�����" + bm.toString());
						Bitmap bitmap_thumb = BitmapThumbnailHelper
								.createThumbnail(imageCachePath, width, 0);
						// ����ǿ����
						lruCache.put(urlString, bitmap_thumb);
					}
					return bm;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			// �ص�����ͼƬ
			if (downloadListener != null && result != null) {
				downloadListener.onImageDownload(result, url);
				// ��url��Ӧ��task�Ѿ�������ɣ���map�н���ɾ��
				// removeTaskFromMap(url);
			}
		}
	}

	@SuppressLint("NewApi")
	// ǿ���û�����
	class MyLruCache extends LruCache<String, Bitmap> {
		public MyLruCache(int maxSize) {
			super(maxSize);
		}

		@Override
		protected int sizeOf(String key, Bitmap value) {
			return value.getHeight() * value.getWidth() * 4;
			// BitmapͼƬ��һ��������4���ֽ�
			// return value.getRowBytes() * value.getHeight();
		}

		@Override
		protected void entryRemoved(boolean evicted, String key,
				Bitmap oldValue, Bitmap newValue) {
			if (evicted) {
				SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(
						oldValue);
				softCaches.put(key, softReference);
			}
		}
	}

	// SDCard������
	static class SDCardHelper {

		// �ж�SD���Ƿ񱻹���
		public static boolean isSDCardMounted() {
			// return Environment.getExternalStorageState().equals("mounted");
			return Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED);
		}

		// ��ȡSD���ĸ�Ŀ¼
		public static String getSDCardBaseDir() {
			if (isSDCardMounted()) {
				return Environment.getExternalStorageDirectory()
						.getAbsolutePath();
			}
			return null;
		}

		// ��ȡSD���������ռ��С������MB
		public static long getSDCardSize() {
			if (isSDCardMounted()) {
				StatFs fs = new StatFs(getSDCardBaseDir());
				int count = fs.getBlockCount();
				int size = fs.getBlockSize();
				return count * size / 1024 / 1024;
			}
			return 0;
		}

		// ��ȡSD����ʣ��ռ��С
		public static long getSDCardFreeSize() {
			if (isSDCardMounted()) {
				StatFs fs = new StatFs(getSDCardBaseDir());
				int count = fs.getFreeBlocks();
				int size = fs.getBlockSize();
				return count * size / 1024 / 1024;
			}
			return 0;
		}

		// ��ȡSD���Ŀ��ÿռ��С
		public static long getSDCardAvailableSize() {
			if (isSDCardMounted()) {
				StatFs fs = new StatFs(getSDCardBaseDir());
				int count = fs.getAvailableBlocks();
				int size = fs.getBlockSize();
				return count * size / 1024 / 1024;
			}
			return 0;
		}

		// ��SD���Ĺ���Ŀ¼�±����ļ�
		public static boolean saveFileToSDCardPublicDir(byte[] data,
				String type, String fileName) {
			BufferedOutputStream bos = null;
			if (isSDCardMounted()) {
				File file = Environment.getExternalStoragePublicDirectory(type);
				try {
					bos = new BufferedOutputStream(new FileOutputStream(
							new File(file, fileName)));
					bos.write(data);
					bos.flush();
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (bos != null) {
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

		// ��SD�����Զ���Ŀ¼�±����ļ�
		public static boolean saveFileToSDCardCustomDir(byte[] data,
				String dir, String fileName) {
			BufferedOutputStream bos = null;
			if (isSDCardMounted()) {
				File file = new File(getSDCardBaseDir() + File.separator + dir);
				if (!file.exists()) {
					file.mkdirs();// �ݹ鴴���Զ���Ŀ¼
				}
				try {
					bos = new BufferedOutputStream(new FileOutputStream(
							new File(file, fileName)));
					bos.write(data);
					bos.flush();
					return true;
				} catch (Exception e) {
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

		// ��SD����˽��FilesĿ¼�±����ļ�
		public static boolean saveFileToSDCardPrivateFilesDir(byte[] data,
				String type, String fileName, Context context) {
			BufferedOutputStream bos = null;
			if (isSDCardMounted()) {
				File file = context.getExternalFilesDir(type);
				try {
					bos = new BufferedOutputStream(new FileOutputStream(
							new File(file, fileName)));
					bos.write(data);
					bos.flush();
					return true;
				} catch (Exception e) {
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

		// ��SD����˽��CacheĿ¼�±����ļ�
		public static boolean saveFileToSDCardPrivateCacheDir(byte[] data,
				String fileName, Context context) {
			BufferedOutputStream bos = null;
			if (isSDCardMounted()) {
				File file = context.getExternalCacheDir();
				try {
					bos = new BufferedOutputStream(new FileOutputStream(
							new File(file, fileName)));
					bos.write(data);
					bos.flush();
					return true;
				} catch (Exception e) {
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

		// ����bitmapͼƬ��SDCard��˽��CacheĿ¼
		public static boolean saveBitmapToSDCardPrivateCacheDir(Bitmap bitmap,
				String fileName, Context context) {
			if (isSDCardMounted()) {
				BufferedOutputStream bos = null;
				// ��ȡ˽�е�Cache����Ŀ¼
				File file = context.getExternalCacheDir();
				try {
					bos = new BufferedOutputStream(new FileOutputStream(
							new File(file, fileName)));
					if (fileName != null
							&& (fileName.contains(".png") || fileName
									.contains(".PNG"))) {
						bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
					} else {
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
					}
					bos.flush();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bos != null) {
						try {
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				return true;
			} else {
				return false;
			}
		}

		// ��SD����ȡ�ļ�
		public static byte[] loadFileFromSDCard(String fileDir) {
			BufferedInputStream bis = null;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			try {
				bis = new BufferedInputStream(new FileInputStream(new File(
						fileDir)));
				byte[] buffer = new byte[8 * 1024];
				int c = 0;
				while ((c = bis.read(buffer)) != -1) {
					baos.write(buffer, 0, c);
					baos.flush();
				}
				return baos.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (baos != null) {
						baos.close();
					}
					if (bis != null) {
						bis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		// ��SDCard��Ѱ��ָ��Ŀ¼�µ��ļ�������Bitmap
		public static Bitmap loadBitmapFromSDCard(String filePath) {
			byte[] data = loadFileFromSDCard(filePath);
			if (data != null) {
				Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
				if (bm != null) {
					return bm;
				}
			}
			return null;
		}

		// ��ȡSD������Ŀ¼��·��
		public static String getSDCardPublicDir(String type) {
			return Environment.getExternalStoragePublicDirectory(type)
					.toString();
		}

		// ��ȡSD��˽��CacheĿ¼��·��
		public static String getSDCardPrivateCacheDir(Context context) {
			return context.getExternalCacheDir().getAbsolutePath();
		}

		// ��ȡSD��˽��FilesĿ¼��·��
		public static String getSDCardPrivateFilesDir(Context context,
				String type) {
			return context.getExternalFilesDir(type).getAbsolutePath();
		}

		public static boolean isFileExist(String filePath) {
			File file = new File(filePath);
			return file.isFile();
		}

		// ��sdcard��ɾ���ļ�
		public static boolean removeFileFromSDCard(String filePath) {
			File file = new File(filePath);
			if (file.exists()) {
				try {
					file.delete();
					return true;
				} catch (Exception e) {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	static class BitmapThumbnailHelper {
		/**
		 * ��ͼƬ���ж��β�������������ͼ�����ü��ع���ͼƬ�����ڴ����
		 */
		public static Bitmap createThumbnail(byte[] data, int newWidth,
				int newHeight) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(data, 0, data.length, options);
			int oldWidth = options.outWidth;
			int oldHeight = options.outHeight;

			int ratioWidth = 0;
			int ratioHeight = 0;

			if (newWidth != 0 && newHeight == 0) {
				ratioWidth = oldWidth / newWidth;
				options.inSampleSize = ratioWidth;
			} else if (newWidth == 0 && newHeight != 0) {
				ratioHeight = oldHeight / newHeight;
				options.inSampleSize = ratioHeight;
			}
			options.inPreferredConfig = Config.ALPHA_8;
			options.inJustDecodeBounds = false;
			Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length,
					options);
			return bm;
		}

		public static Bitmap createThumbnail(String pathName, int newWidth,
				int newHeight) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(pathName, options);
			int oldWidth = options.outWidth;
			int oldHeight = options.outHeight;

			int ratioWidth = 0;
			int ratioHeight = 0;

			if (newWidth != 0 && newHeight == 0) {
				ratioWidth = oldWidth / newWidth;
				options.inSampleSize = ratioWidth;
			} else if (newWidth == 0 && newHeight != 0) {
				ratioHeight = oldHeight / newHeight;
				options.inSampleSize = ratioHeight;
			}
			options.inPreferredConfig = Config.ALPHA_8;
			options.inJustDecodeBounds = false;
			Bitmap bm = BitmapFactory.decodeFile(pathName, options);
			return bm;
		}
	}

	public String getImageName(String url) {
		String imageName = "";
		if (url != null) {
			imageName = url.substring(url.lastIndexOf("/") + 1);
		}
		return imageName;
	}

}
