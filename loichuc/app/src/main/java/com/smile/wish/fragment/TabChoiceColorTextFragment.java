package com.smile.wish.fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.smile.wish.R;
import com.smile.wish.WishActivity;
import com.smile.wish.adapter.AdapterRecyclerChoiceWish;
import com.smile.wish.databinding.FragmentTabchoiceColorBinding;


import java.util.ArrayList;

import top.defaults.colorpicker.ColorObserver;

public class TabChoiceColorTextFragment extends Fragment {
    private FragmentTabchoiceColorBinding binding;
    private OnFragmentColor listener;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_tabchoice_color,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.colorPicker.subscribe(new ColorObserver() {
            @Override
            public void onColor(int color, boolean fromUser, boolean shouldPropagate) {
                listener.onDataSelected(color);
            }
        });

      /*  binding.colorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
            @Override
            public void onColorSelected(int color) {
                // Do whatever you want with the color
                binding.imageView.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                listener.onDataSelected(color);
            }
        });
*/
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WishActivity)
            this.listener = (OnFragmentColor) context; // gan listener vao MainActivity
        else
            throw new RuntimeException(context.toString() + " must implement onViewSelected!");
    }
    public interface OnFragmentColor{
        void onDataSelected(int data);
    }
}
