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

import com.smile.wish.Object.Category;
import com.smile.wish.R;
import com.smile.wish.databinding.ItemCategoryBinding;
import com.smile.wish.databinding.ItemFontBinding;

import java.util.ArrayList;

public class AdapterRecyclerChoiceCategory extends RecyclerView.Adapter<AdapterRecyclerChoiceCategory.ViewHolder> {
    private Context context;
    private ArrayList<Category> listCategorys;
    public AdapterRecyclerChoiceCategory(ArrayList<Category>listCategorys, Context context){
        this.context=context;
        this.listCategorys =listCategorys;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

         ItemCategoryBinding itemCategoryBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_category,parent,false);
        return new ViewHolder(itemCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
             holder.bindData(listCategorys.get(position));
        holder.binding.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(listCategorys.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCategorys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewFrame;
        private ItemCategoryBinding binding;
        public ViewHolder(ItemCategoryBinding view) {
            super(view.getRoot());
            this.binding=view;

        }
        public void bindData(Category category) {
            binding.tvContent.setText(category.getContent());
            //binding.btFont.setTypeface(font);
        }
    }
    public interface OnItemClickedListener {
        void onItemClick(Category category);
    }
    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
