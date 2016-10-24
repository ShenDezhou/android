package com.qf.activitydemo;

import android.app.Application;

public class MyApp extends Application{
	private int itemNo;
	public void setItemNo(int itemNo){
		this.itemNo=itemNo;
	}
	public int getItemNo(){
		return itemNo;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
}
