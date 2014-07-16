package com.m.adpter;

import java.lang.ref.SoftReference;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.date.ListMap;
import com.xunlu.lizhenshezhi.R;

public class gridAdpter extends BaseAdapter {
	private Context con;
	private LayoutInflater inflater;
	private List<SoftReference<Bitmap>> lb;

	public List<SoftReference<Bitmap>> getLb() {
		return lb;
	}

	public void setLb(List<SoftReference<Bitmap>> lb) {
		this.lb = lb;
	}

	public Context getCon() {
		return con;
	}

	public void setCon(Context con) {
		this.con = con;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int prame = 0;
		if (lb != null) {
			prame = lb.size();
		}
		return prame;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ImageView imageView;
		TextView textView;
		inflater = LayoutInflater.from(con);
		arg1 = inflater.inflate(R.layout.grid, null);
		imageView = (ImageView) arg1.findViewById(R.id.grideimg);
		textView = (TextView) arg1.findViewById(R.id.gridfenshu);
		imageView.setImageBitmap(lb.get(arg0).get());
		Log.e("ListMap.getLb().get(arg0)", ListMap.getLb().get(arg0) + "");
		textView.setText("id¿¨Í¼Æ¬");
		return arg1;
	}
}
