package com.qianfeng.day02_listviewarrayadapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class MyActivity extends Activity {

	private TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myactivity_layout);
		textView=(TextView) findViewById(R.id.textView1);
		String str = getIntent().getStringExtra("Country");
		textView.setText("欢迎来到"+str);
		textView.setTextColor(Color.RED);
		textView.setTextSize(25);
	}
}
