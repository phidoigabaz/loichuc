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

public class HorizontallmageAdapterfont extends BaseAdapter {

	private Activity context;

	private int stype_icon_adapter;
	private List plotsImages;
	private List plotsImages_select;
	private static ViewHolder holder;
	// /public static ViewHolder selectHolder_all;
	private LayoutInflater l_Inflater;

	// public int mThumbIds_theme;

	public HorizontallmageAdapterfont(Activity context, List plotsImages, List plotsImages_select) {

		this.context = context;
		this.plotsImages = plotsImages;
		this.plotsImages_select = plotsImages_select;
		l_Inflater = LayoutInflater.from(context);
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
			/*convertView = l_Inflater.inflate(R.layout.listviewfont, null);
			holder.imageView_font= (ImageView) convertView
					.findViewById(R.id.itemviewfont);	
			convertView.setTag(holder);*/

		} else {

			holder = (ViewHolder) convertView.getTag();		
			}
		holder.imageView_font.setId(position);		
		holder.imageView_font.setImageDrawable((Drawable) plotsImages.get(position));
		/*if(position==HomeActivity.selectfont)
		holder.imageView_font.setImageDrawable((Drawable) plotsImages_select.get(position));*/
		
		holder.id=position;
		return convertView;
	}

	public static class ViewHolder {
		public ImageView imageView_font;
		int id;
	}

}
