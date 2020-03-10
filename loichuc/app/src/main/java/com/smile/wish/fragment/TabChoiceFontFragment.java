package com.smile.wish.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.smile.wish.MainActivity;
import com.smile.wish.R;
import com.smile.wish.WishActivity;
import com.smile.wish.adapter.AdapterRecyclerChoiceFont;
import com.smile.wish.databinding.FragmentTabchoiceFontBinding;

import java.util.ArrayList;

public class TabChoiceFontFragment extends Fragment {
    private FragmentTabchoiceFontBinding binding;
    private AdapterRecyclerChoiceFont adapterRecyclerChoiceFont;
    private OnFragmentTypeface listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tabchoice_font, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvFont.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        listTypeface = MainActivity.listTypeface;
        adapterRecyclerChoiceFont = new AdapterRecyclerChoiceFont(listTypeface, getContext());
        binding.rvFont.setAdapter(adapterRecyclerChoiceFont);
        adapterRecyclerChoiceFont.setOnItemClickedListener(new AdapterRecyclerChoiceFont.OnItemClickedListener() {
            @Override
            public void onItemClick(Typeface font, int postion) {
                listener.onDataSelected(font, postion);
            }
        });
    }

    ArrayList<Typeface> listTypeface = new ArrayList<>();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WishActivity)
            this.listener = (OnFragmentTypeface) context; // gan listener vao MainActivity
        else
            throw new RuntimeException(context.toString() + " must implement onViewSelected!");
    }

    public interface OnFragmentTypeface {
        void onDataSelected(Typeface data, int postion);
    }
}
