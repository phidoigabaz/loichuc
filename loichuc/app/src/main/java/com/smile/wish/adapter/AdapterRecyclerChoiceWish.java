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
import com.smile.wish.databinding.ItemWishBinding;

import java.util.ArrayList;

public class AdapterRecyclerChoiceWish extends RecyclerView.Adapter<AdapterRecyclerChoiceWish.ViewHolder> {
    private Context context;
    private ArrayList<String> listWish;
    public AdapterRecyclerChoiceWish(ArrayList<String>listWish, Context context){
        this.context=context;
        this.listWish =listWish;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

         ItemWishBinding itemFontBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_wish,parent,false);
        return new ViewHolder(itemFontBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
             holder.bindData(listWish.get(position));
        holder.binding.btFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(listWish.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listWish.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewFrame;
        private ItemWishBinding binding;
        public ViewHolder(ItemWishBinding view) {
            super(view.getRoot());
            this.binding=view;

        }
        public void bindData(String wish) {
            binding.btFont.setText(wish);
        }
    }
    public interface OnItemClickedListener {
        void onItemClick(String wish);
    }
    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
