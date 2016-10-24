package com.qf.contentresolver_demo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	private SQLiteDatabase sQLiteDatabase = null;
	// 它是数据库名
	private static final String DB_NAME = "helper";
	// 它是数据库的路径
	private static final String DB_PATH = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
			.getAbsolutePath();
	// 它是数据库全路径名
	private static final String DB = DB_PATH + File.separator + DB_NAME;

	// 这是封装在内部的，访问数据库的对象

	public MySQLiteOpenHelper(Context context) {
		super(context, DB, null, 2);
		// TODO Auto-generated constructor stub
		sQLiteDatabase=getWritableDatabase();
	}

	// 它是创建时候的回调
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i("onCreate", "执行了");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS tb_my(_id integer PRIMARY KEY AUTOINCREMENT,name);");
	}

	// 这个是版本更新的回调
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i("onUpgrade", "执行了");
		if(newVersion>oldVersion){
			db.execSQL("DROP TABLE IF EXISTS tb_my;");
			db.execSQL("CREATE TABLE IF NOT EXISTS tb_my1(_id integer PRIMARY KEY AUTOINCREMENT,name);");
		}
	}
	// 这是查询数据库的方法们，也就是各种重载
		public Cursor selectCursor(String table, String[] columns,
				String selection, String[] selectionArgs, String groupBy,
				String having, String orderBy) {
			return sQLiteDatabase.query(table, columns, selection, selectionArgs,
					groupBy, having, orderBy);
		}

		public Cursor selectCursor(String sql) {
			return sQLiteDatabase.rawQuery(sql, new String[] {});
		}

		public Cursor selectCursor(String sql, String[] selectionArgs) {
			return sQLiteDatabase.rawQuery(sql, selectionArgs);
		}

		// 这是一个插入的方法，封装了内部访问数据库的insert
		public long insert(String table, String nullColumnHack, ContentValues values) {
			// insert方法第一个参数是表名，第二个参数是防止空行所提供的列名，第三个参数是要插入的字段对应的键值对
			// ContentValues它是一种键值对，可以直接new出来，就像hashmap
			// 返回这一行的行号
			return sQLiteDatabase.insert(table, nullColumnHack, values);
		}

		// 这是一个删除的方法，封装了内部访问数据库的delete
		public long delete(String table, String whereClause, String[] whereArgs) {
			// 第一个参数是表名，第二个参数是条件，第三个参数是替换条件中的占位符
			// 返回值是此次操作影响了多少行
			return sQLiteDatabase.delete(table, whereClause, whereArgs);
		}

		// 这是一个更新的方法，封装了内部访问数据库的update
		public long update(String table, ContentValues values, String whereClause,
				String[] whereArgs) {
			// 第一个参数是表名，第二个参数表示要更新的列，用键值对的方式来表达，第三个参数是条件，第四个参数是替换条件中的占位符
			// 返回值是此次操作影响了多少行
			return sQLiteDatabase.update(table, values, whereClause, whereArgs);
		}
		
		public Cursor query(String table, String[] columns, String selection,
	            String[] selectionArgs, String groupBy, String having,
	            String orderBy){
			return sQLiteDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		}

		// 封装一个执行任意语句的方法
		public void execSQL(String sql) {
			sQLiteDatabase.execSQL(sql);
		}

		// 封装一个执行任意语句的方法的重载
		public void execSQL(String sql, Object[] bindArgs) {
			// 第一个参数是任意sql语句，第二个参数是替换占位符，为了兼容数据类型，这里使用了Object数组
			sQLiteDatabase.execSQL(sql, bindArgs);
		}

		// 封装一个cursor转换成list的方法
		public List<Map<String, Object>> cursorToList(Cursor cursor) {
			if (cursor == null) {
				return null;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			// 获取操作cursor之前的下标
			int position = cursor.getPosition();
			
			cursor.moveToPosition(-1);
			for (; cursor.moveToNext();) {
				Map<String, Object> map = new HashMap<String, Object>();
				// cursor.getColumnCount()方法可以获取到一共有多少个列
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					// cursor.getColumnName(i)可以获取到第i列的列名
					map.put(cursor.getColumnName(i), cursor.getString(i));

				}
				list.add(map);
			}
			// 把cursor恢复到之前的状态
			cursor.moveToPosition(position);
			return list;
		}

		// 查询list的方法们，各种重载就像cursor的查询一样。
		public List<Map<String, Object>> selectList(String table, String[] columns,
				String selection, String[] selectionArgs, String groupBy,
				String having, String orderBy) {
			// 调用之前的查询方法
			Cursor cursor = selectCursor(table, columns, selection, selectionArgs,
					groupBy, having, orderBy);
			// 返回list使用之前的转换方法
			return cursorToList(cursor);
		}

		public List<Map<String, Object>> selectList(String sql,
				String[] selectionArgs) {
			Cursor cursor = selectCursor(sql, selectionArgs);
			return cursorToList(cursor);
		}

		public List<Map<String, Object>> selectList(String sql) {
			Cursor cursor = selectCursor(sql);
			return cursorToList(cursor);
		}

		public void destroy() {
			if (sQLiteDatabase != null) {
				sQLiteDatabase.close();
			}
		}

}
