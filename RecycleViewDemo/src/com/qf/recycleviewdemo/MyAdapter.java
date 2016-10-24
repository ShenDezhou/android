package com.qf.recycleviewdemo;

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
public class MyAdapter extends Adapter<MyAdapter.MyViewHolder> {
	private Context context = null;
	private List<String> list = null;

	private OnChildClickListener onChildClickListener = null;

	public void setOnChildClickListener(
			OnChildClickListener onChildClickListener) {
		this.onChildClickListener = onChildClickListener;
	}

	public MyAdapter(Context context) {
		this.context = context;
		list = new ArrayList<String>();
	}

	public void setList(List<String> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public void removeItem(int position) {
		this.list.remove(position);
		notifyItemRemoved(position);
		// notifyDataSetChanged();
	}

	class MyViewHolder extends ViewHolder implements OnClickListener {

		public MyViewHolder(View arg0) {
			super(arg0);
			// TODO Auto-generated constructor stub
			arg0.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (onChildClickListener != null) {
				onChildClickListener.onChildChickListener(v, getPosition(),
						this);
			}
		}

	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public void onBindViewHolder(MyViewHolder arg0, int position) {
		// TODO Auto-generated method stub
		arg0.itemView.setX(0);
		TextView textView = (TextView) arg0.itemView
				.findViewById(R.id.textView);
		textView.setText(list.get(position));
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(context).inflate(R.layout.itemview,
				arg0, false);
		return new MyViewHolder(view);

	}
}

interface OnChildClickListener {
	void onChildChickListener(View view, int position, ViewHolder viewHolder);
}
