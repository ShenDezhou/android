package com.qf.listviewdemo;

import com.qf.interf.ItemInterface;

public class Po implements ItemInterface {

	private String title=null;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int getItemType() {
		// TODO Auto-generated method stub
		return 1;
	}

}
