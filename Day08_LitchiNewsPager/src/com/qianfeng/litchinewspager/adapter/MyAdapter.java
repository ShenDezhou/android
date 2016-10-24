package com.qianfeng.litchinewspager.adapter;

import java.util.List;

import com.qianfeng.litchinewspager.R;
import com.qianfeng.litchinewspager.bean.News;
import com.qianfeng.litchinewspager.helpr.ImageDownloadHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//适配器优化部分技巧：
//从ListView的运行本质来进行优化，最基本的优化分为两种（View的创建和View的findViewById()，或者说就是item布局的填充和item上每个控件的初始化）。
//1、android:layout_height属性：
//必须将ListView的布局高度属性设置为非“wrap_content”（可以是“match_parent /  fill_parent  /  400dp等绝对数值”），如果ListView的布局高度为“wrap_content”，那么getView()就会重复调用。一般来说，调用item的总数会达到item数量的四倍。
//因为getView()重复执行，从而造成无谓的内存消耗和性能浪费。
//
//2、ViewHolder：
//利用ViewHolder内部类，将item布局文件中需要展示的控件定义为属性（其实ViewHolder就是一个自定义的模型类）。这样就把item中散在的多个控件合成一个整体。
//View的findViewById()方法是比较耗时的，因此需要考虑item中的每个控件可否只调用一次，而ViewHolder对象正是解决多次findViewById()这一问题的方案。
//
//3、convertView：
//ListView的加载是一个item一个item的加载，这样就会每加载一个item就调用一次布局填充器来填充一个item布局，然后findViewById一遍该布局上的所有控件。因为View的每次创建比较耗时，且findViewById也比较耗时。当数据量大的时候，是不可想象的。而利用Recycle回收机制就可以解决问题。
//当ListView第一次加载时，只加载一个屏幕可以显示的item，而屏幕上无法显示的item先不要加载。当屏幕滚动，滚出屏幕的item对象要善于利用，那就是converView对象。充分利用convertView是否为 null的判断，当convertView实例存在时直接使用，这样可以减少布局填充的过程。
//利用convertView的setTag()方法将ViewHolder对象作为标签附加到convertView上，当convertView被重复利用的时候，自然就能拿到ViewHolder对象，因此就减少了ViewHolder对象实例化的次数。因为ViewHolder对象中定义了item上的控件，这样就节省了item每个控件的初始化，也就是findViewById()这个过程。因此提升了加载速度，节省了性能开销。
//
//4、监听屏幕的滚动状态的变动情况：
//ListView对象有OnScrollListener监听器。其回调方法onScrollStateChanged(AbsListView view, int scrollState)的第二个参数就是屏幕滚动状态。
//scrollState = SCROLL_STATE_TOUCH_SCROLL(1)：表示正在滚动。当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1
//scrollState =SCROLL_STATE_FLING(2) ：表示手指做了抛的动作（手指离开屏幕前，用力滑了一下，屏幕产生惯性滑动）。 
//scrollState =SCROLL_STATE_IDLE(0) ：表示屏幕已停止。屏幕停止滚动时为0。
//在以上三种屏幕滚动状态中，如果还处于SCROLL_STATE_FLING状态，则说明屏幕还处于惯性滑动状态，此时可以不进行异步加载图片，从而节省无谓的网络访问的开销。

public class MyAdapter extends BaseAdapter {
	private Context context;
	private List<News>list;
	
	public MyAdapter(Context context, List<News> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView==null) {
			// 适配器优化：关键在于convertView复用
			// 1、节省了实例化ViewHolder
			holder=new ViewHolder();
			// 2、节省了布局填充器填充布局的过程
			convertView=LayoutInflater.from(context).inflate(R.layout.item_listview, null);
			// 3、节省了初始化item布局上每个控件的过程，也就是findViewById的过程
			holder.imageView_item_cover=(ImageView) convertView.findViewById(R.id.imageView_item_cover);
			holder.textView_item_subject=(TextView) convertView.findViewById(R.id.textView_item_subject);
			holder.textView_item_summary=(TextView) convertView.findViewById(R.id.textView_item_summary);
			// 为了不用反复初始化item布局上每个控件，也就是不再反复findViewById，将ViewHolder作为标签贴在convertView上
			convertView.setTag(holder);
		}else {
			// 从convertView上取下标签，这样当获得ViewHolder对象，也就获得了item上每个控件，相当于给item上每个控件进行了初始化
			holder=(ViewHolder) convertView.getTag();
		}
		// 给item上每个控件进行赋值
		holder.textView_item_subject.setText(list.get(position).getSubject());
		holder.textView_item_summary.setText(list.get(position).getSummary());
		
		String imageUrl="http://litchiapi.jstv.com"+list.get(position).getCover();
		holder.imageView_item_cover.setImageResource(R.drawable.ic_launcher);
		holder.imageView_item_cover.setTag(imageUrl);
		
		// 使用带有LruCache及SD卡缓存的异步加载图片类
		ImageDownloadHelper.getInstance().downloadImage(context, imageUrl, holder.imageView_item_cover, 100, new ImageDownloadHelper.OnImageDownloadListener() {
			
			@Override
			public void onImageDownload(Bitmap bitmap, String imgUrl) {
				if (bitmap!=null && !imgUrl.equals("")) {
					holder.imageView_item_cover.setImageBitmap(bitmap);
				}
			}
		});
		
		return convertView;
	}
	// 将item中的 每个控件定义在ViewHolder中，目的是把item中散在的多个UI控件合成一个整体。
	class ViewHolder{
		private ImageView imageView_item_cover;
		private TextView textView_item_subject;
		private TextView textView_item_summary;
	}
	
}
