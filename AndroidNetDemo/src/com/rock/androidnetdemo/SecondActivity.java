package com.rock.androidnetdemo;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WebView webView = initView();
		setContentView(webView);
	}

	private WebView initView() {
		// 实例化
		WebView webView = new WebView(this);
		// 支持javaScript
		webView.getSettings().setJavaScriptEnabled(true);
		// 
		webView.setWebViewClient(new WebViewClient());
		// 
		webView.setWebChromeClient(new WebChromeClient());
		
		webView.loadUrl("http://www.baidu.com");
		
		return webView;
	}
	
}
