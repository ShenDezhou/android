package com.recycle;

import com.tsingdata.zxingdemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class WebViewActivity extends Activity implements OnClickListener{

	private WebView mWebView;
	
	private EditText mEditText;
	
	private Button mJump;
	
	/**
	 * Activity的入口，初始化操作
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//导入布局
		setContentView(R.layout.activity_webview);
		// 初始化 ,通过findViewById进行View的查找
		mWebView = (WebView) this.findViewById(R.id.mwebview);
		// 设置webView支持javaScript,默认为false
		mWebView.getSettings().setJavaScriptEnabled(true);
		
		// 设置需要加载的页面
	//	mWebView.loadUrl("http://192.168.2.1:9999/");
		
		mEditText = (EditText) this.findViewById(R.id.path);
		
		mJump = (Button) this.findViewById(R.id.webview_jump);
		
		mJump.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.webview_jump:
			// 将输入的文本获取过来
			String url = mEditText.getText().toString();
			// 讲我们输入的网址加载到浏览器上
			mWebView.loadUrl(url);
			
			break;

		}
		
	}
	
	
}
