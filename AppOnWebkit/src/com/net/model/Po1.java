package com.net.model;



public class Po1 implements ItemInterface {

	private String title1 = null;
	private String title2 = null;
	private String title3 = null;

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

	public String getTitle3() {
		return title3;
	}

	public void setTitle3(String title3) {
		this.title3 = title3;
	}

	@Override
	public int getItemType() {
		// TODO Auto-generated method stub
		return 2;
	}

}
