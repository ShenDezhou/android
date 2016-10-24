package com.rock.androiddatasavedemo.db;

import java.util.ArrayList;
import java.util.List;

import com.rock.androiddatasavedemo.db.DBContract.UserColums;
import com.rock.androiddatasavedemo.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataOpenHelper extends SQLiteOpenHelper {

	private static final String CREATE = "create table " + DBContract.TABLE_USER + "( " + UserColums.USERNAME+" text, "
													+ UserColums.ID + " text )";
	
	private SQLiteDatabase db;
	// 构造函数，至少需要Context对象， 然后数据库的名字 数据库的版本
	public DataOpenHelper(Context context) {
		// 创建一个数据库
		super(context, DBContract.NAME, null, DBContract.VERSION);
		// TODO Auto-generated constructor stub
		connection(); 
	}
	/**
	 *  在onCreate时 创建我们业务逻辑中所需要的表
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE);
	}
	/**
	 *  数据库升级函数
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 
		if (oldVersion < newVersion) {
			//如果需要增加或删除字段
			/**
			 * ① 备份原表
			 * ② 删除原表，创建新表，新表将是我们添加或是删除字段后表的样子
			 * ③ 将备份表中的数据还原到我们的新表中
			 * ④ 删除备份表
			 */
			
		}

	}
	
	private void connection(){
		// 只读权限的数据库
		//db = getReadableDatabase();
		
		// 写入权限 包含读取权限
		db = getWritableDatabase();
	}

	public void insert(String tableName,ContentValues values){
		// 插入有三个参数，第一个 表的名字， 第二个是 约束  第三个 我们需要插入的值
		
		db.insert(tableName, null, values);
	}
	
	public void delete(String tableName,String whereClause,String[] whereArgs){
		db.delete(tableName, whereClause, whereArgs);
	}
	
	public void update(String tableName,ContentValues values,String whereClause,String[] whereArgs){
		db.update(tableName, values, whereClause, whereArgs);
	}
	/**
	 * 查询数据库
	 * 
	 */
	public Cursor select(String tableName,String[] columns){
		// 提供多种限制条件供查询使用
		Cursor query = db.query(tableName, columns, null,null,null,null,null);
		// 返回值Cursor是一个装着我们数据的游标
		return query;
	}
	/**
	 *  保存一个用户
	 * @param user
	 */
	public void saveUser(User user){
		// Android中封装的一个存储数据的字典
		ContentValues values = new ContentValues();
		// 所对应的值 分别存入我们ContentValues 中
		values.put(UserColums.ID, user.getId());
		
		values.put(UserColums.USERNAME, user.getUsername());
		
		insert(DBContract.TABLE_USER, values);
	}
	/**
	 * 存储一个User集合
	 */
	public void saveUsers(List<User> users){
		// 遍历集合，逐一进行存储
		for(User user : users){
			saveUser(user);
		}
	}
	/**
	 * 获取数据库中存在的集合数据
	 * 首先通过数据库的查询获取一个游标，如果没有约束条件 select * from tableName
	 * 
	 * cursor.moveToNext 判断我们的游标是否可以向下一个位置移动
	 * 
	 * 
	 */
	public List<User> getUsers(){
		
		List<User> data = new ArrayList<User>();
		
		Cursor cursor = select(DBContract.TABLE_USER, null);
		
//		cursor.moveToFirst();
		
		while (cursor.moveToNext()) {
			// 创建一个User
			User user = new User();
			// 根据Column的名字去查找  当前行 Column的索引位置 index
			int index = cursor.getColumnIndex(UserColums.ID);
			// 根据索引值 index 去这个位置将数据取出
			String id = cursor.getString(index);
			// 数据赋值给用户
			user.setId(id);
			//
			index = cursor.getColumnIndex(UserColums.USERNAME);
			
			String username = cursor.getString(index);
			
			user.setUsername(username);
			
			data.add(user);
		}
		
		return data;
	}
	
	public void destroy(){
		if (db != null) {
			db.close();
		}
	}
	
}
