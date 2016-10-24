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
//�������Ż����ּ��ɣ�
//��ListView�����б����������Ż�����������Ż���Ϊ���֣�View�Ĵ�����View��findViewById()������˵����item���ֵ�����item��ÿ���ؼ��ĳ�ʼ������
//1��android:layout_height���ԣ�
//���뽫ListView�Ĳ��ָ߶���������Ϊ�ǡ�wrap_content���������ǡ�match_parent /  fill_parent  /  400dp�Ⱦ�����ֵ���������ListView�Ĳ��ָ߶�Ϊ��wrap_content������ôgetView()�ͻ��ظ����á�һ����˵������item��������ﵽitem�������ı���
//��ΪgetView()�ظ�ִ�У��Ӷ������ν���ڴ����ĺ������˷ѡ�
//
//2��ViewHolder��
//����ViewHolder�ڲ��࣬��item�����ļ�����Ҫչʾ�Ŀؼ�����Ϊ���ԣ���ʵViewHolder����һ���Զ����ģ���ࣩ�������Ͱ�item��ɢ�ڵĶ���ؼ��ϳ�һ�����塣
//View��findViewById()�����ǱȽϺ�ʱ�ģ������Ҫ����item�е�ÿ���ؼ��ɷ�ֻ����һ�Σ���ViewHolder�������ǽ�����findViewById()��һ����ķ�����
//
//3��convertView��
//ListView�ļ�����һ��itemһ��item�ļ��أ������ͻ�ÿ����һ��item�͵���һ�β�������������һ��item���֣�Ȼ��findViewByIdһ��ò����ϵ����пؼ�����ΪView��ÿ�δ����ȽϺ�ʱ����findViewByIdҲ�ȽϺ�ʱ�������������ʱ���ǲ�������ġ�������Recycle���ջ��ƾͿ��Խ�����⡣
//��ListView��һ�μ���ʱ��ֻ����һ����Ļ������ʾ��item������Ļ���޷���ʾ��item�Ȳ�Ҫ���ء�����Ļ������������Ļ��item����Ҫ�������ã��Ǿ���converView���󡣳������convertView�Ƿ�Ϊ null���жϣ���convertViewʵ������ʱֱ��ʹ�ã��������Լ��ٲ������Ĺ��̡�
//����convertView��setTag()������ViewHolder������Ϊ��ǩ���ӵ�convertView�ϣ���convertView���ظ����õ�ʱ����Ȼ�����õ�ViewHolder������˾ͼ�����ViewHolder����ʵ�����Ĵ�������ΪViewHolder�����ж�����item�ϵĿؼ��������ͽ�ʡ��itemÿ���ؼ��ĳ�ʼ����Ҳ����findViewById()������̡���������˼����ٶȣ���ʡ�����ܿ�����
//
//4��������Ļ�Ĺ���״̬�ı䶯�����
//ListView������OnScrollListener����������ص�����onScrollStateChanged(AbsListView view, int scrollState)�ĵڶ�������������Ļ����״̬��
//scrollState = SCROLL_STATE_TOUCH_SCROLL(1)����ʾ���ڹ���������Ļ�������û�ʹ�õĴ�������ָ������Ļ��ʱΪ1
//scrollState =SCROLL_STATE_FLING(2) ����ʾ��ָ�����׵Ķ�������ָ�뿪��Ļǰ����������һ�£���Ļ�������Ի������� 
//scrollState =SCROLL_STATE_IDLE(0) ����ʾ��Ļ��ֹͣ����Ļֹͣ����ʱΪ0��
//������������Ļ����״̬�У����������SCROLL_STATE_FLING״̬����˵����Ļ�����ڹ��Ի���״̬����ʱ���Բ������첽����ͼƬ���Ӷ���ʡ��ν��������ʵĿ�����

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
			// �������Ż����ؼ�����convertView����
			// 1����ʡ��ʵ����ViewHolder
			holder=new ViewHolder();
			// 2����ʡ�˲����������䲼�ֵĹ���
			convertView=LayoutInflater.from(context).inflate(R.layout.item_listview, null);
			// 3����ʡ�˳�ʼ��item������ÿ���ؼ��Ĺ��̣�Ҳ����findViewById�Ĺ���
			holder.imageView_item_cover=(ImageView) convertView.findViewById(R.id.imageView_item_cover);
			holder.textView_item_subject=(TextView) convertView.findViewById(R.id.textView_item_subject);
			holder.textView_item_summary=(TextView) convertView.findViewById(R.id.textView_item_summary);
			// Ϊ�˲��÷�����ʼ��item������ÿ���ؼ���Ҳ���ǲ��ٷ���findViewById����ViewHolder��Ϊ��ǩ����convertView��
			convertView.setTag(holder);
		}else {
			// ��convertView��ȡ�±�ǩ�����������ViewHolder����Ҳ�ͻ����item��ÿ���ؼ����൱�ڸ�item��ÿ���ؼ������˳�ʼ��
			holder=(ViewHolder) convertView.getTag();
		}
		// ��item��ÿ���ؼ����и�ֵ
		holder.textView_item_subject.setText(list.get(position).getSubject());
		holder.textView_item_summary.setText(list.get(position).getSummary());
		
		String imageUrl="http://litchiapi.jstv.com"+list.get(position).getCover();
		holder.imageView_item_cover.setImageResource(R.drawable.ic_launcher);
		holder.imageView_item_cover.setTag(imageUrl);
		
		// ʹ�ô���LruCache��SD��������첽����ͼƬ��
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
	// ��item�е� ÿ���ؼ�������ViewHolder�У�Ŀ���ǰ�item��ɢ�ڵĶ��UI�ؼ��ϳ�һ�����塣
	class ViewHolder{
		private ImageView imageView_item_cover;
		private TextView textView_item_subject;
		private TextView textView_item_summary;
	}
	
}
