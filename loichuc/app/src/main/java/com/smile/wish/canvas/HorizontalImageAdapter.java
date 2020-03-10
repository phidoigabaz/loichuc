package com.smile.wish.canvas;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.smile.wish.R;

import java.util.List;

public class HorizontalImageAdapter extends BaseAdapter {

	private Activity context;

	private int stype_icon_adapter;
	private List plotsImages;
	private List plotsImages_select;
    
	private static ViewHolder holder;
	///public static ViewHolder selectHolder_all;
	private LayoutInflater l_Inflater;

	// public int mThumbIds_theme;

	public HorizontalImageAdapter(Activity context, List plotsImages,
                                  int id_sype_adapter) {

		this.context = context;
		this.plotsImages = plotsImages;
		l_Inflater = LayoutInflater.from(context);
		this.stype_icon_adapter = id_sype_adapter;
	}
	public HorizontalImageAdapter(Activity context, List plotsImages, List plotsImages_select,
                                  int id_sype_adapter) {

		this.context = context;
		this.plotsImages = plotsImages;
		this.plotsImages_select=plotsImages_select;
		l_Inflater = LayoutInflater.from(context);
		this.stype_icon_adapter = id_sype_adapter;
	}
	@Override
	public int getCount() {
		return plotsImages.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			holder = new ViewHolder();
/*
			convertView = l_Inflater.inflate(R.layout.listviewiconmain, null);

			switch (stype_icon_adapter) {
			case 0:
				convertView = l_Inflater.inflate(R.layout.listviewiconmain, null);
				holder.imageView_all = (ImageView) convertView
						.findViewById(R.id.icon_main);			
				break;
			case 1:
				convertView = l_Inflater.inflate(R.layout.listviewicon, null);
				holder.imageView_all = (ImageView) convertView
						.findViewById(R.id.icon_icon);						
				break;			
			}*/
			convertView.setTag(holder);

		} else {			

			holder = (ViewHolder) convertView.getTag();
		}
		holder.imageView_all.setId(position);		
		holder.imageView_all.setImageDrawable((Drawable) plotsImages.get(position));
		if(stype_icon_adapter==0){		
			
			/*if(position==HomeActivity.selectposition)
				holder.imageView_all.setImageDrawable((Drawable) plotsImages_select.get(position));*/
			/*	holder.imageView_all.setBackgroundResource(R.drawable.backgroundselecticon);
			else
				holder.imageView_all.setBackgroundResource(R.drawable.backgroundicon);		*/
		}
		holder.id=position;
		return convertView;
	}

	public static class ViewHolder {
		public ImageView imageView_all;
		int id;
	}

}
