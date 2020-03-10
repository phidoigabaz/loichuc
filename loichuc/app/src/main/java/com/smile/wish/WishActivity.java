package com.smile.wish;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.SeekBar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import com.smile.wish.adapter.TabWishAdapter;
import com.smile.wish.databinding.ActivityWishBinding;
import com.smile.wish.fragment.TabChoiceColorTextFragment;
import com.smile.wish.fragment.TabChoiceFontFragment;
import com.smile.wish.fragment.TabListTextWishFragment;

public class WishActivity extends AppCompatActivity implements TabListTextWishFragment.OnFragmentManager, TabChoiceFontFragment.OnFragmentTypeface, TabChoiceColorTextFragment.OnFragmentColor {
   /* private EditText edWish;
    private TabLayout tabLayout;
    private ViewPager viewPager;
   */

    private TabWishAdapter tabWishAdapter;
    private ActivityWishBinding binding;
    private int color;
    private int postionTypeface;
    private int textSize = 30;

    /* static {
         AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
     }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wish);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        tabWishAdapter = new TabWishAdapter(getSupportFragmentManager());
        //
        tabWishAdapter.addFragment(new TabChoiceColorTextFragment(), "Color");
        tabWishAdapter.addFragment(new TabChoiceFontFragment(), "Font");
        tabWishAdapter.addFragment(new TabListTextWishFragment(), "Wishes");

        //


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("textwish", binding.edWish.getText().toString());
                intent.putExtra("textcolor", color);
                intent.putExtra("typeface", postionTypeface);
                intent.putExtra("textSize", (textSize+30));
                setResult(10, intent);
                finish();
            }
        });
        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.setAdapter(tabWishAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.edWish.setTextSize(textSize);
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textSize = seekBar.getProgress();
                binding.edWish.setTextSize(textSize);
            }
        });
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();
    @Override
    public void onDataSelected(String data) {
        binding.edWish.setText(data);
    }

    @Override
    public void onDataSelected(Typeface data, int postion) {
        postionTypeface = postion;
        binding.edWish.setTypeface(data);
    }

    @Override
    public void onDataSelected(int data) {
        color = data;
        binding.edWish.setTextColor(data);
    }
}
