package com.smile.wish.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.smile.wish.Common;
import com.smile.wish.Object.Icon;
import com.smile.wish.R;

import java.util.ArrayList;

public class AdapterRecyclerViewIcon extends RecyclerView.Adapter<AdapterRecyclerViewIcon.ViewHolderIcon> {
    private Context context;
    private int[] listFrame;

    public AdapterRecyclerViewIcon(int[] listFrame, Context context) {

        this.context = context;
        this.listFrame=listFrame;

    }

    @NonNull
    @Override
    public ViewHolderIcon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderIcon(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderIcon holder, final int position) {
        holder.bindData(listFrame[position]);
        holder.imageViewFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(listFrame[position]);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFrame.length;
    }

    public class ViewHolderIcon extends RecyclerView.ViewHolder {
        ImageView imageViewFrame;

        public ViewHolderIcon(View itemView) {
            super(itemView);
            imageViewFrame = itemView.findViewById(R.id.imageViewFrame);
        }

        public void bindData(int icon) {
            imageViewFrame.setImageResource(icon);
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(int icon);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
