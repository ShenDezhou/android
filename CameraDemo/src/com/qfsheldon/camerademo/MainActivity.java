package com.qfsheldon.camerademo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageView imageView = null;
	private File targetFile;

	private void initView() {
		imageView = (ImageView) findViewById(R.id.imageView);
	}

	// 它是说startActivityForResult的返回回调
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 998) {
			if (targetFile != null && targetFile.exists()) {
				Bitmap bitmap = BitmapFactory.decodeFile(targetFile
						.getAbsolutePath());
				imageView.setImageBitmap(bitmap);

			}
		} else {
			if (requestCode == 111) {
				if (data != null) {
					Log.d("Media", "Camera result : " + data);

					if (data.hasExtra("data")) {
						Bitmap bitmap = data.getParcelableExtra("data");

						imageView.setImageBitmap(bitmap);

						// 对于Bitmap 进行文件存储。

						// 第三个参数 OutputStream

						String state = Environment.getExternalStorageState();

						if (state.equals(Environment.MEDIA_MOUNTED)) {

							File directory = Environment
									.getExternalStorageDirectory();

							// 拍照之后，保存的图片文件
							File file = new File(directory,
									System.currentTimeMillis() + ".jpg");

							// 如果问文件不存在，先创建

							if (!file.exists()) {
								try {
									file.createNewFile();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							FileOutputStream fout = null;

							try {

								fout = new FileOutputStream(file);

								// 压缩图片，压缩之后的内容，写到输出流
								bitmap.compress(Bitmap.CompressFormat.JPEG,
										100, fout);

							} catch (FileNotFoundException e) {

								e.printStackTrace();

							} finally {

								if (fout != null) {
									try {
										fout.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}

							}

						}

					}
				}
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick1(View view) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 拍照之后，保存的图片文件
		targetFile = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
				System.currentTimeMillis() + ".jpg");

		// !!!
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(targetFile));

		startActivityForResult(intent, 998);
	}

	public void onClick2(View view) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 111);
	}

	public void onClick3(View view) {
		// ACTION_VIDEO_CAPTURE 录视频的。
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

		// 视频录制需要注意的事项:
		// 1. 视频录制只能够存到文件中，因为视频可能很大，没法直接保存成对象。
		// 2. 录制视频时，可以指定视频文件尺寸的最大限制，例如 不要超过 500MB
		// 3. 录制视频时，可以指定录制的最长时间
		// 4. 录制视频，需要 摄像头的权限，和 录音的权限

		// 对于EXTRA_OUTPUT 第二个参数必须是 android.net.Uri 类型
		// 对于 文件存储，可以使用 Uri.fromFile(File) 生成Uri
		// 1. 存储卡路径
		// 2. 指定一个 图片的名称
		// 3. 生成 File

		String state = Environment.getExternalStorageState();

		if (state.equals(Environment.MEDIA_MOUNTED)) {

			File directory = Environment.getExternalStorageDirectory();

			// 保存的视频文件
			File targetVideoFile = new File(directory,
					System.currentTimeMillis() + ".mp4");

			// !!!
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(targetVideoFile));

			// 在视频录制中，文件尺寸设置，尺寸设置必须采用 Long 型，否则无效
			intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 200 * 1024 * 1024L); // 200K
			// 测试

			// 设置最大录制的时间。单位 秒。
			// intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);

			// 设置视频质量。
			// 整型参数，0 低质量； 1 高质量
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		}

		startActivityForResult(intent, 299);
	}

	public void onClick4(View view) {
		Intent intent=new Intent(getApplicationContext(), CameraActivity.class);
		startActivity(intent);
	}

}
