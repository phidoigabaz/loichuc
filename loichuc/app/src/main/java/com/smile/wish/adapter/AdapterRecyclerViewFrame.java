package com.smile.wish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smile.wish.Object.Frame;
import com.smile.wish.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterRecyclerViewFrame extends RecyclerView.Adapter<AdapterRecyclerViewFrame.RecyclerViewHolder> {
    private Context context;
    private ArrayList<Frame>listFrame;
    public AdapterRecyclerViewFrame(ArrayList<Frame>listFrame, Context context){
        this.context=context;
        this.listFrame=listFrame;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
             holder.bindData(listFrame.get(position));
        holder.imageViewFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(listFrame.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFrame.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewFrame;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageViewFrame = itemView.findViewById(R.id.imageViewFrame);
        }
        public void bindData(Frame frame) {
            Glide.with(context).load(frame.getId()).override(100,100).into(imageViewFrame);
            //imageViewFrame.setBackgroundDrawable(context.getResources().getDrawable(frame.getId()));
        }
    }
    public interface OnItemClickedListener {
        void onItemClick(Frame  frame);
    }
    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
