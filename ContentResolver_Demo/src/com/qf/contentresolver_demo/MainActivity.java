package com.qf.contentresolver_demo;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	ContentResolver contentResolver = null;
	//使用uri作为区分contentprovider   http://
	Uri uri = Uri.parse("content://com.qf.contentproviderdemo.MyContentProvider");

	public void onClick(View view) {
		//获取接受者
		contentResolver = getContentResolver();
		contentResolver.insert(uri, new ContentValues());
		Cursor cursor = contentResolver.query(uri, null, null, null, null);
		MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
		TextView textView = (TextView) view;
		textView.setText(mySQLiteOpenHelper.cursorToList(cursor).get(0)
				.get("name")
				+ "");
	}

}
