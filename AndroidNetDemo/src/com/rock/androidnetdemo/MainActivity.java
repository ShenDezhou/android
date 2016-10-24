package com.rock.androidnetdemo;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.rock.androidnetdemo.http.HttpUtil;
import com.rock.androidnetdemo.http.StreamUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();
	
	private Button openWebView;
	
	private Button getString;
	private Button getBitmap;
	
	private Button getStringUrl;
	private Button getBitmapUrl;
	
	private ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		openWebView = (Button) this.findViewById(R.id.open_webview);
		getString = (Button)this.findViewById(R.id.get_string);
		getBitmap = (Button) this.findViewById(R.id.get_bitmap);
		getStringUrl = (Button) this.findViewById(R.id.get_string_url);
		getBitmapUrl = (Button) this.findViewById(R.id.get_bitmap_url);
		
		image = (ImageView) this.findViewById(R.id.pic);
		
		openWebView.setOnClickListener(this);
		getString.setOnClickListener(this);
		getBitmap.setOnClickListener(this);
		getStringUrl.setOnClickListener(this);
		getBitmapUrl.setOnClickListener(this);
	}

	@Override
	public void onClick(View paramView) {
		// TODO Auto-generated method stub
		switch (paramView.getId()) {
		case R.id.open_webview:
			Intent intent = new Intent(this,SecondActivity.class);
			this.startActivity(intent);
			break;
		case R.id.get_string:
			new AsyncTask<Void, Void, String>(){

				@Override
				protected String doInBackground(Void... paramVarArgs) {
					try {
						String result = EntityUtils.toString(HttpUtil.getHttpClient("http://192.168.1.103:9999/user/list?currPage=1&pageSize=5"));
						return result;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}
				
				@Override
				protected void onPostExecute(String result) {
					Log.e(TAG, "result--" + result);
				};
			
			
			}.execute();
			break;
		case R.id.get_bitmap:
			new AsyncTask<Void, Void, Bitmap>(){

				@Override
				protected Bitmap doInBackground(Void... paramVarArgs) {
					try {
						byte[] bytes = EntityUtils.toByteArray(HttpUtil.getHttpClient("http://img2.3lian.com/2014/f2/7/d/21.jpg"));
						Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
						return bitmap;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.e(TAG, "result--null");
					return null;
				}
				
				@Override
				protected void onPostExecute(Bitmap result) {
					if (result != null) {
						image.setImageBitmap(result);
					}
				};
				
				}.execute();
			break;
		
		case R.id.get_string_url:
			new AsyncTask<Void, Void, String>(){

				@Override
				protected String doInBackground(Void... params) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("cateid", "0");
					map.put("p", "1");
					InputStream is = HttpUtil.postHttpUrlConnection("http://apiv2.vmovier.com/api/post/getPostInCate",map);
					String result = StreamUtil.parseString(is);
					Log.e(TAG, "doInBackground--"+Thread.currentThread().getName());
					return result;
				}
				@Override
				protected void onPostExecute(String result) {
					Log.e(TAG, "onPostExecute--"+Thread.currentThread().getName());
					Log.e(TAG, "result--" + result);
				};
			}.execute();
			break;
			
		case R.id.get_bitmap_url:
			new AsyncTask<Void, Void, Bitmap>(){

				@Override
				protected Bitmap doInBackground(Void... paramVarArgs) {
					InputStream is = HttpUtil.getHttpUrlConnection("http://img2.3lian.com/2014/f2/7/d/21.jpg");
					Bitmap result = BitmapFactory.decodeStream(is);
					return result;
				}
				@Override
				protected void onPostExecute(Bitmap result) {
					if (result != null) {
						image.setImageBitmap(result);
					}
				};
			}.execute();
			break;
		}
		
	}
	
	class MyAsync extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... paramVarArgs) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
