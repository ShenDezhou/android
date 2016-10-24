package com.rock.androidnetdemo;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.android.volley.toolbox.Volley;
import com.rock.androidnetdemo.http.HttpUtil;
import com.rock.androidnetdemo.http.StreamUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OnePage extends Activity implements OnClickListener {
	// 声明一个TAG
	private static final String TAG = OnePage.class.getSimpleName();
	
	private Button mButton;
	// 声明操作Handler的按钮
	private Button mButtonHandler;
	
	private int i = 1;
	
	private TextView handleMsg;
	
	private Button jump;
	
	private Button httpGet;
	
	private Button urlHttpGet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one);
		// 初始化
		mButton = (Button) this.findViewById(R.id.asyncTask);
		// 添加点击监听器
		mButton.setOnClickListener(this);
		//
		mButtonHandler = (Button) this.findViewById(R.id.handler);
		//
		mButtonHandler.setOnClickListener(this);
		// 初始化跳转按钮
		jump = (Button) this.findViewById(R.id.jump);
		// 添加监听，用来处理页面跳转操作
		jump.setOnClickListener(this);
		//
		httpGet = (Button) this.findViewById(R.id.http_get);
		//
		httpGet.setOnClickListener(this);
		//
		urlHttpGet = (Button) this.findViewById(R.id.http_url_get);
		//
		urlHttpGet.setOnClickListener(this);
		//
		handleMsg = (TextView) this.findViewById(R.id.handler_msg);
		//
		handleMsg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(){
					@Override
					public void run() {
						// 获取一个Message对象，有两种方式，① 直接new 一个出来， ② 通过Message.obtain（建议使用这个第二种方式，可以复用MessageQueue中）
						Message message = Message.obtain();
						message.what = 2;
						message.obj = "我是发送过来的消息";
						mHanlder.sendMessage(message);
						// 测试
					//	mButtonHandler.setText("子线程更改View");
						
					};
					
				}.start();
				
			}
		});
		
		
	}
	/**
	 *  常规开发中，非UI线程不可以进行UI操作
	 *  但是在SurfaceView中是可以进行UI操作的
	 *  一般用于二维码识别，视频播放等
	 * 
	 * 
	 * 
	 */
	// 声明一个Handler，并重写handleMessage
	private Handler mHanlder = new Handler(){
		
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				mButtonHandler.setText("我收到子线程发过来的消息" + i++);
				break;
			case 2:
				// 收到消息的时候，将收到的内容吐司出来
				// 吐司里面有三个参数，第一个参数 上下文的对象 第二个参数  吐司的内容  第三个参数  吐司的时长    构建玩Toast之后将内容显示出来
				Toast.makeText(OnePage.this, "收到的内容--" + msg.obj.toString(), Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Log.e(TAG, "result--" + msg.obj.toString());
				break;
			}
		};
		
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.asyncTask:
			new AsyncTask<Void, Integer, String>(){
				/**
				 * 在后台操作，用来耗时操作
				 */
				@Override
				protected String doInBackground(Void... paramVarArgs) {
					// 获取当前线程名字
					String threadName = Thread.currentThread().getName();
					// 将结果打印出来
					Log.e(TAG, "doInBack--Thread--" + threadName);
					
					return null;
				}
				
				/**
				 *  执行前的预备操作
				 */
				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
				}
				/**
				 *  执行结束，将doInBackGround返回到主线程中
				 */
				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					// 获取当前线程名字
					String threadName = Thread.currentThread().getName();
					// 将结果打印出来
					Log.e(TAG, "onPost--Thread--" + threadName);
					
				}
				/**
				 * 用来更新进度条
				 */
				@Override
				protected void onProgressUpdate(Integer... values) {
					// TODO Auto-generated method stub
					super.onProgressUpdate(values);
				}
				/**
				 * 取消异步任务，一般在页面销毁，或是取消请求的时候调用
				 */
				@Override
				protected void onCancelled() {
					// TODO Auto-generated method stub
					super.onCancelled();
				}
				// 调用使异步任务开始执行
			}.execute();
			break;
		case R.id.handler:
			// 定义一个匿名内部类，Thread重写run()方法，在run()发送一个消息给Handler
			new Thread(){
				@Override
				public void run() {
					// 发送一个空的消息，里面的message.what = 1
					mHanlder.sendEmptyMessage(1);
					// 可以写各种耗时操作，以及复杂的运算
					
					
				};
				// 启动线程
			}.start();
			
			break;
		case R.id.jump:
			// Android中页面跳转 Intent 两个参数① 上下文对象，② 指定要跳转页面的class
			Intent intent = new Intent(this, WebViewActivity.class);
			// 启动页面跳转
			startActivity(intent);
			
			break;
		case R.id.http_get:
			new AsyncTask<String, Void, String>(){

				@Override
				protected String doInBackground(String... params) {
					HttpEntity entity = HttpUtil.getHttpClient(params[0]);
					try {
						String result = EntityUtils.toString(entity);
						return result;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.e(TAG, "doInbackground--null");
					return null;
				}
				
				@Override
				protected void onPostExecute(String result) {
					Log.e(TAG, "result--" + result);
				};
				
				}.execute("http://192.168.57.1:9999/user/list?currPage=1&pageSize=5");
			
			break;
		case R.id.http_url_get:
			new Thread(){
				@Override
				public void run() {
					// 使用HttpUrlConnection发送get请求将结果转换为String
					String result = StreamUtil.parseString(HttpUtil.getHttpUrlConnection("http://192.168.57.1:9999/user/list?currPage=1&pageSize=5"));
					// 获取Message对象，讲结果包装在里面，通过Handler发送给主线程
					Message msg = Message.obtain();
					msg.what = 3;
					msg.obj = result;
					mHanlder.sendMessage(msg);
					
				};
				
				
			}.start();
			break;
		}
		
	}
	
	
}
