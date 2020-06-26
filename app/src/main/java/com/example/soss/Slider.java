package com.example.soss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.soss.Adapters.SlideAdapter;

public class Slider extends AppCompatActivity {

    private ViewPager viewPager;
    private SlideAdapter myadapter;
    private LinearLayout dostsLayout;
    private TextView[] mDots;
    private Button btnBack;
    private Button btnNext;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        viewPager = (ViewPager) findViewById(R.id.vprSlider);
        dostsLayout = (LinearLayout) findViewById(R.id.layoutDots);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);

        myadapter = new SlideAdapter(this);
        viewPager.setAdapter(myadapter);
        addDotsIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                viewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int next = getItem(+1);
                if(next < mDots.length){
                    viewPager.setCurrentItem(next);
                }else {
                    Intent intent = new Intent(Slider.this, Principal.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void addDotsIndicator(int position){
        mDots = new TextView[4];
        dostsLayout.removeAllViews();

        for(int i = 0; i< mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparenteWhite));
            dostsLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    private int getItem(int i){
        return viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;
            if(i == mDots.length - 1){
                btnNext.setEnabled(true);
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                btnNext.setText("Finish");
                btnBack.setText("Back");

            } else {
                btnNext.setEnabled(true);
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                btnNext.setText("Next");
                btnBack.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
