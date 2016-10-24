package com.qf.listviewdemo;

import com.qf.interf.ItemInterface;

public class Po1 implements ItemInterface {

	private String title1=null;
	private String title2=null;
	
	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	@Override
	public int getItemType() {
		// TODO Auto-generated method stub
		return 2;
	}

}
