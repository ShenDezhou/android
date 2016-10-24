package com.rock.androiddatasavedemo;

import java.util.List;

import com.rock.androiddatasavedemo.db.DBContract;
import com.rock.androiddatasavedemo.db.DBContract.UserColums;
import com.rock.androiddatasavedemo.db.DataOpenHelper;
import com.rock.androiddatasavedemo.model.User;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SqliteActivity extends Activity implements OnClickListener {

	private static final String TAG = SqliteActivity.class.getSimpleName();
	
	private Button insert,delete,update,select;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite);
		insert = (Button) this.findViewById(R.id.insert);
		delete = (Button) this.findViewById(R.id.delete);
		update = (Button) this.findViewById(R.id.update);
		select = (Button) this.findViewById(R.id.select);
		
		insert.setOnClickListener(this);
		select.setOnClickListener(this);
		delete.setOnClickListener(this);
		update.setOnClickListener(this);
	}

	private int flag = 1;
	
	@Override
	public void onClick(View v) {
		DataOpenHelper db = new DataOpenHelper(this);
		switch (v.getId()) {
		case R.id.insert:
			
			User user = new User();
			user.setId("" + flag);
			user.setUsername("测试数据" + flag++);
			
			db.saveUser(user);
			break;
		case R.id.select:
			
			List<User> users = db.getUsers();
			// 遍历集合  将我们数据简单打印
			for (User u : users){
				Log.e(TAG, u.getId() + "--" + u.getUsername());
			}
			
			//Toast.makeText(this, db.getUsers().toString(), Toast.LENGTH_SHORT).show();
			break;
		case R.id.update:
			ContentValues values = new ContentValues();
			
			values.put(UserColums.USERNAME, "猴子");
			// 四个参数， ① 指定表名 ② 指定我们变更之后的值 ③ 我们需要变更的字段，它对应的值用？ ④ 我们变更条件，对应前面的？
			db.update(DBContract.TABLE_USER, values, "id=?", new String[]{"1"});
			
			break;
		case R.id.delete:
			// 三个参数 ① 指定表名  ② 我们删除条件对应字段 ③ 对应字段的值
			db.delete(DBContract.TABLE_USER, "id=?", new String[]{"1"});
			break;
		
		}
		db.destroy();
		
	}
	
}
