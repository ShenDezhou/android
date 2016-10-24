package com.qf.appdemo2;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private LinearLayout linearLayout = null;
	private ViewPager viewPager = null;

	private HorizontalScrollView horizont = null;

	private void initView() {
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		((TextView) (linearLayout.getChildAt(0))).setTextColor(Color.RED);

		viewPager = (ViewPager) findViewById(R.id.viewpager_main);
		horizont = (HorizontalScrollView) findViewById(R.id.horizont);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		viewPager.setAdapter(new FragmentPagerAdapter(
				getSupportFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return linearLayout.getChildCount();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				// switch (arg0) {
				// case 0:
				// //fragment1
				// break;
				//
				// default:
				// break;
				// }
				MyFragment myFragment = new MyFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("pageno", arg0);
				myFragment.setArguments(bundle);

				return myFragment;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// TODO Auto-generated method stub
				// super.destroyItem(container, position, object);

			}

		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				for (int i = 0; i < linearLayout.getChildCount(); i++) {
					// 十六进制一定要写8位argb
					((TextView) (linearLayout.getChildAt(i)))
							.setTextColor(0xff000000);
				}
				((TextView) (linearLayout.getChildAt(position)))
						.setTextColor(0xffff0000);
				horizont.smoothScrollTo(60 * position, 0);

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View view) {
		for (int i = 0; i < linearLayout.getChildCount(); i++) {
			// 十六进制一定要写8位argb
			((TextView) (linearLayout.getChildAt(i))).setTextColor(0xff000000);
		}
		((TextView) (view)).setTextColor(0xffff0000);
		// 判断view在linearlayout里的序号，没有-1
		int pageno = linearLayout.indexOfChild(view);
		viewPager.setCurrentItem(pageno);

	}

}
