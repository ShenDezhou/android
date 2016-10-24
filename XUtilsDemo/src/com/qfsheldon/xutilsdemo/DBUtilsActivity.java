package com.qfsheldon.xutilsdemo;

import java.util.List;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.DbModelSelector;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class DBUtilsActivity extends Activity {

	private DbUtils dbUtils = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dbutils);
		dbUtils = DbUtils.create(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dbutils, menu);
		return true;
	}

	public void onClick(View view) {
		User user = new User();
		user.setId(3);
		user.setName("qianfeng");
		user.setPassword("1234567");
		try {
			// //修改对象,如果有就改，如果没有就存，
			// //通过主键来判断有没有
//			 dbUtils.saveOrUpdate(user);

//			dbUtils.delete(user);

			// 在user表上删除，用后面wherebuilder来控制到底删除哪些记录
			// dbUtils.delete(User.class, whereBuilder)

			 //保存对象
			 dbUtils.save(user);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onClick1(View view) {
		try {
			// List<User> list=dbUtils.findAll(User.class);
			// Selector
			// List<User>
			// list=dbUtils.findAll(Selector.from(User.class).where("id", "=",
			// 1));
			// 这是wherebuilder的实例的方式
			// WhereBuilder whereBuilder=WhereBuilder.b();
			// 这个是实例wherebuilder时，直接指定了查询条件
			WhereBuilder whereBuilder = WhereBuilder.b("id", "=", 3);

			// 多条件查询？怎么办？
			// 在后面直接and和or
			// WhereBuilder whereBuilder=WhereBuilder.b("id", "=", 1).and("id",
			// "=", 1).or("id", "=", 1);

			// 在Selector中封装了分组、条件、排序、分页等等功能。
			List<User> list = dbUtils.findAll(Selector.from(User.class)
					.where(whereBuilder).orderBy("id"));

			// 它是把第一个符合条件的记录返回
			// User user=dbUtils.findFirst(User.class);
			// findfirst结合第一个的那个selector来使用

			
			
//			//返回指定列
			DbModel dbModel=dbUtils.findDbModelFirst(DbModelSelector.from(User.class).select("name"));
			String str =dbModel.getString("name");
			
			
			
			if (list != null && list.size() != 0) {
				((TextView) view).setText(list.get(0).getName()
						+ list.get(0).getPassword());
			} else {
				((TextView) view).setText("没有结果");
			}

			((TextView) view).setText(str);
			
			
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
