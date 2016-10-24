package com.rock.androiddatasavedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button goSqlite;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		goSqlite =  (Button) this.findViewById(R.id.sqlite);
		goSqlite.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sqlite:
			Intent intent = new Intent(this, SqliteActivity.class);
			startActivity(intent);
			break;

		}
	}
}
