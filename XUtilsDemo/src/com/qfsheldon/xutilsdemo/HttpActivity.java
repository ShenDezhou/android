package com.qfsheldon.xutilsdemo;

import java.io.File;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class HttpActivity extends Activity {

	private TextView textView = null;

	private void initView() {
		textView = (TextView) findViewById(R.id.textView_http);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http);
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.http, menu);
		return true;
	}

	public void onClick(View view) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpUtils http = new HttpUtils();
				http.send(HttpMethod.GET, "http://www.baidu.com",
						new RequestCallBack<String>() {
							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {
								textView.setText(current + "/" + total);
							}

							@Override
							public void onSuccess(
									ResponseInfo<String> responseInfo) {
								textView.setText(responseInfo.result);
							}

							@Override
							public void onStart() {
							}

							@Override
							public void onFailure(HttpException error,
									String msg) {
							}
						});
			}
		}).start();
	}

	public void onClick1(View view) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpUtils http = new HttpUtils();
				HttpHandler handler = http
						.download(
								"https://github.com/wyouflf/xUtils3/archive/master.zip",
								Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+File.separator+"httpcomponents-client-4.2.5-src.zip",
								true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将重新下载。
								true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
								new RequestCallBack<File>() {

									@Override
									public void onStart() {
										textView.setText("conn...");
									}

									@Override
									public void onLoading(long total,
											long current, boolean isUploading) {
										textView.setText(current + "/" + total);
									}

									@Override
									public void onSuccess(
											ResponseInfo<File> responseInfo) {
										textView.setText("downloaded:"
												+ responseInfo.result.getPath());
									}

									@Override
									public void onFailure(HttpException error,
											String msg) {
										textView.setText(msg);
									}
								});
			}
		}).start();
	}
}
