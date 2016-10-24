package com.rock.androiddatasavedemo.db;

import android.provider.BaseColumns;
/**
 * 定义了一个数据库的契约类
 * 
 * 里面存放了 数据库的名字，数据库的版本，各个表的名字
 *
 */
public class DBContract {
	
	public static final String NAME = "dbtest.db";
	
	public static final int VERSION = 1;
	
	public static final String TABLE_USER = "User";
	
	/**
	 *  每一个表中对应的字段 我们需要写一个类 来实现BaseColumns接口
	 *  
	 *  接口中默认提供了  _id
	 *  
	 *  在类中定义我们表中包含的字段
	 *
	 */
	public class UserColums implements BaseColumns{
		
		public static final String USERNAME = "username";
		
		public static final String ID = "id";
		
	}
	
}
