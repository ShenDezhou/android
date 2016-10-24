package com.rock.androiddatasavedemo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SharedActvivity extends Activity implements OnClickListener {

	private Button save ;
	
	private Button get;
	// 用来存储字典的文件
	private SharedPreferences sdf;
	
	private Button contain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shared);
		//
		save = (Button) findViewById(R.id.save_pref);
		
		get = (Button) findViewById(R.id.get_pref);
		
		contain = (Button) findViewById(R.id.contains);
		
		save.setOnClickListener(this);
		get.setOnClickListener(this);
		contain.setOnClickListener(this);
		
		// 获取SharedPreference，两个参数 ① 名字 ② 模式
		sdf= getSharedPreferences("shared", Context.MODE_PRIVATE);
		
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save_pref:
			Editor editor = sdf.edit();
			// putString的时候 两个参数 ① key ② value
			editor.putString("test", "测试数据");
			// 使用的时候需要提交
			editor.commit();
			break;
		case R.id.get_pref:
			// 两个参数  ① key ② 默认值defaultValue
			String result = sdf.getString("test", "没有你想要的值");
			Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
			break;
		case R.id.contains:
			// 指定SharedPreference中是否存在指定key
			boolean con = sdf.contains("test");
			if (con) {
				Toast.makeText(this, "这个key真实存在", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show();
			}
			break;
		}
		
	}
	
}
