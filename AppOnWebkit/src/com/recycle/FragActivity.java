package com.recycle;

import com.tsingdata.zxingdemo.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * 引进了Fragment，Fragment是Android3.0推出的，我们使用的V4中的兼容包
 * 使用兼容包需要注意的是：所有有关兼容包的类要从同一个包中导入，以免引起不同包组件不兼容
 * Fragment 直译为 碎片，我们同过FragmentManager开启事务，将我们有关Fragment的操作添加到事务中，最后进行提交。
 * Fragment是动态的页面，灵活性比较高，同一个Activity中拥有多个Fragment，Fragment是依附在Activity上。
 * 同一个Activity中可以同时显示多个Frgment的。
 * 
 * FragmentManager中的事务我们使用的是Replace，这样每次会替换掉我们的Fragment，同时被替换的Fragment也会销毁。
 * 
 * Fragment中比较常用的方法onCreateView,在这个方法中导入布局文件，可以进行常规的View初始化操作。
 * 
 * FragmentManager的获取方法是在Activity中通过getFragmentManager，在V4的Activity中是通过getSupportFragmentManager获取的
 *
 * Fragment在嵌套的时候，嵌套在里面的Fragment加载要使用getChildFragmentManager
 *
 */

public class FragActivity extends FragmentActivity implements OnClickListener {

	private FragmentManager fm;
	
	private Button pageOne;
	
	private Button pageTwo;
	
	private Button pageThree;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_fragment);
		fm = getSupportFragmentManager();
		// 初始化Button
		pageOne = (Button) this.findViewById(R.id.page_one);
		pageTwo = (Button) this.findViewById(R.id.page_two);
		pageThree = (Button) this.findViewById(R.id.page_three);
		//
		pageOne.setOnClickListener(this);
		pageTwo.setOnClickListener(this);
		pageThree.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// 开启事务
		FragmentTransaction beginTransaction = fm.beginTransaction();
		// 根据不同id向事务中添加不同的操作
		switch (v.getId()) {
		case R.id.page_one:
			beginTransaction.replace(R.id.container, new PageOne());
			break;
		case R.id.page_two:
			beginTransaction.replace(R.id.container, new PageTwo());
			break;
		case R.id.page_three:
			beginTransaction.replace(R.id.container, new PageThree());
			break;
		
		}
		// 提交事务
		beginTransaction.commit();
	}
	
}
