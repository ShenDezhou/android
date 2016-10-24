package com.qfsheldon.recyclerviewdemo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter extends Adapter<MyAdapter.MyViewHolder>{

	public OnChildClickListener onChildClickListener=null;
	public void setOnChildClickListener(OnChildClickListener onChildClickListener){
		this.onChildClickListener=onChildClickListener;
	}
	private Context context = null;
	private List<String> list = null;

	public void removeItem(int position){
		list.remove(position);
		//通知第几个删除了
//		notifyItemRemoved(position);
		notifyDataSetChanged();
	}
	//增加元素的方法
	public void addItem(int position){
		list.add(position, "345789");
		notifyItemInserted(position);
	}
	
	public void addAllItem(List<String> list){
		int size=list.size();
		this.list.addAll(list);
		notifyItemRangeInserted(size, this.list.size());
	}
	
	public MyAdapter(Context context) {
		this.context = context;
		list = new ArrayList<String>();
	}

	public void setList(List<String> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public void onBindViewHolder(MyViewHolder arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.textView.setText(list.get(arg1));
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup arg0, int position) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_recyclerview, arg0, false);
		MyViewHolder myViewHolder = new MyViewHolder(view);
		myViewHolder.textView = (TextView) view.findViewById(R.id.textView);
		myViewHolder.textView.setText(list.get(position));
		return myViewHolder;
	}
	class MyViewHolder extends ViewHolder implements OnClickListener{

		public TextView textView=null;
		public MyViewHolder(View arg0) {
			super(arg0);
			// TODO Auto-generated constructor stub
			//设置每一个item可以有点击事件
			arg0.setOnClickListener(this);
		}
		//item回调点击事件
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//把这个点击事件交给Adapter来处理
			//而Adapter里的onChildClickListener又是MainAcitivty实现的
			//于是，getPosition就转发给了MainActivity
			onChildClickListener.onClick(getPosition());
		}
		
	}



}

interface OnChildClickListener {
	void onClick(int position);
}
