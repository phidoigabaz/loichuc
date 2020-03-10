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

public class HorizontalImageAdapterEffect extends BaseAdapter {

	private Activity context;
	private List plotsImages;
	private List plotsImages_select;
    
	private static ViewHolder holder;
	///public static ViewHolder selectHolder_all;
	private LayoutInflater l_Inflater;

	// public int mThumbIds_theme;

	public HorizontalImageAdapterEffect(Activity context, List plotsImages) {
		this.context = context;
		this.plotsImages = plotsImages;
		l_Inflater = LayoutInflater.from(context);
	}
	public HorizontalImageAdapterEffect(Activity context, List plotsImages, List plotsImages_select
			) {

		this.context = context;
		this.plotsImages = plotsImages;
		this.plotsImages_select=plotsImages_select;
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
			
			/*convertView = l_Inflater.inflate(R.layout.listvieweffect, null);
			holder.imageView_effect = (ImageView) convertView
					.findViewById(R.id.icon_effect);	
			convertView.setTag(holder);*/

		} else {			

			holder = (ViewHolder) convertView.getTag();
		}
		holder.imageView_effect.setId(position);		
		holder.imageView_effect.setImageDrawable((Drawable) plotsImages.get(position));
		/*if(stype_icon_adapter==0){		
			
			if(position==HomeActivity.selectposition)
				holder.imageView_all.setImageDrawable((Drawable) plotsImages_select.get(position));
				holder.imageView_all.setBackgroundResource(R.drawable.backgroundselecticon);
			else
				holder.imageView_all.setBackgroundResource(R.drawable.backgroundicon);			
		}*/
		holder.id=position;
		return convertView;
	}

	public static class ViewHolder {
		public ImageView imageView_effect;
		int id;
	}

}
