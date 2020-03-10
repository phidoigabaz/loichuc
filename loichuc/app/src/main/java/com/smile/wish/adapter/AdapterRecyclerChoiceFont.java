package com.smile.wish.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.smile.wish.R;
import com.smile.wish.databinding.ItemFontBinding;

import java.util.ArrayList;

public class AdapterRecyclerChoiceFont extends RecyclerView.Adapter<AdapterRecyclerChoiceFont.ViewHolder> {
    private Context context;
    private ArrayList<Typeface> listTypeface;
    public AdapterRecyclerChoiceFont(ArrayList<Typeface>listFont, Context context){
        this.context=context;
        this.listTypeface =listFont;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

         ItemFontBinding itemFontBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_font,parent,false);
        return new ViewHolder(itemFontBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
             holder.bindData(listTypeface.get(position));
        holder.binding.btFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(listTypeface.get(position),position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTypeface.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewFrame;
        private ItemFontBinding binding;
        public ViewHolder(ItemFontBinding view) {
            super(view.getRoot());
            this.binding=view;

        }
        public void bindData(Typeface font) {
            binding.btFont.setTypeface(font);
        }
    }
    public interface OnItemClickedListener {
        void onItemClick(Typeface font,int postion);
    }
    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
