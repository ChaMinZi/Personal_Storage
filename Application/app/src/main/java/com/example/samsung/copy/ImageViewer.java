package com.example.samsung.copy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;

public class ImageViewer extends AppCompatActivity {

    ScrollView sv;
    LinearLayout Linear;
    int count;
    ImageView iv;
    String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        setTitle("뷰어");

        final Intent intent=getIntent();
        count=intent.getExtras().getInt("count");

        sv=new ScrollView(this);
        sv.setVerticalScrollBarEnabled(true);
        sv.arrowScroll(View.SCROLL_AXIS_VERTICAL);

        Linear=new LinearLayout(this);
        Linear.setOrientation(LinearLayout.VERTICAL);

        for(int i=0;i<count;i++) {
            iv = new ImageView(this);
            imgUrl=intent.getExtras().getString("img"+i);
            Glide.with(this).load(imgUrl).into(iv);
            iv.setAdjustViewBounds(true);
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Linear.addView(iv);
        }

        sv.addView(Linear);
        setContentView(sv);
    }
}
