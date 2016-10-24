package com.qfsheldon.camerademo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

public class CameraActivity extends Activity implements Callback {

	private SurfaceView surfaceView = null;
	private Camera camera = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		surfaceView.getHolder().addCallback(this);
		// 打开照相机，打开之后，就可以进行预览
		// Camera.open() 打开的永远都是后置摄像头
		camera = Camera.open();

		// 对于带有 Camera.open(int)
		// 打开特定的照相机
		// id 从 0 开始，到摄像头的个数-1
		// Camera.open(int id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// TODO 进行摄像头预览的设置

		try {

			// 获取摄像头内置的配置信息，改动这个对象之后
			// 摄像头配置信息自动生效
			Camera.Parameters parameters = camera.getParameters();

			// 设置预览时，图像的像素格式。
			// JPEG 就是压缩的图像
			// RGB_565 降低内存占用的。
			parameters.setPreviewFormat(ImageFormat.JPEG);
			// 拍照需要设置的参数

			// 设置拍照的时候，收到的图片文件的格式。
			parameters.setPictureFormat(ImageFormat.JPEG);

			// parameters.setRotation();

			parameters.setJpegQuality(100);

			// 设置闪光灯自动模式。其他模式参考。
			// parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);

			// 设置摄像头旋转角度
			parameters.setRotation(90);

			// 设置摄像头预览的界面显示
			camera.setPreviewDisplay(holder);

			// 设置 预览的内容，顺时针旋转90度。
			camera.setDisplayOrientation(90);

			if (Build.VERSION.SDK_INT >= 17) {
				// 是否有快门声音
				camera.enableShutterSound(false);
			}

			// 开始预览
			camera.startPreview();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		  // 停止预览
		  camera.stopPreview();
		  camera.release();
	}
	public void onClick(View view){
		//拍照的实际的方法
		camera.takePicture(new ShutterCallback() {
			//传感速度
			@Override
			public void onShutter() {
				// TODO Auto-generated method stub
				
			}
		}, new PictureCallback() {
			//拍照时按下快门的回调
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// TODO Auto-generated method stub
				
			}
		}, new PictureCallback() {
			//这是返回拍照结果的回调
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// TODO Auto-generated method stub
				 Log.d("CameraActivity", "data : " + data.length);

			       String state = Environment
			         .getExternalStorageState();

			       if (state.equals(Environment.MEDIA_MOUNTED)) {

			        File directory = Environment
			          .getExternalStorageDirectory();

			        File file = new File(directory, "ABC.jpg");

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
			         fout.write(data);
			        } catch (IOException e) {
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

			       // data 实际上也是反着的。也需要顺时针旋转九十度。

				camera.startPreview();
			      }
			     
			}
		);
	}

}
