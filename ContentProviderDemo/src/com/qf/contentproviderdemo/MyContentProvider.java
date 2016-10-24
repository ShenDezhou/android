package com.qf.contentproviderdemo;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

	private MySQLiteOpenHelper mySQLiteOpenHelper = null;

	// private SQLiteDatabase sqLiteDatabase = null;

	// 它是创建contentprovider时候的回调
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		mySQLiteOpenHelper = new MySQLiteOpenHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stu
		// 去query
		// 把结果返回
		// sqLiteDatabase.q
		
		return mySQLiteOpenHelper.selectCursor("select * from tb_saved",
				selectionArgs);
	}

	// 它是返回数据的mime类型
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub

		mySQLiteOpenHelper.execSQL(
				"insert into tb_saved(id,name) values(1,\"qf\");", new Object[] {});
		return uri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
