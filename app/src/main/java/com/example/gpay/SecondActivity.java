package com.example.gpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {
    //Customize image
   // https://github.com/siyamed/android-shape-imageview#:~:text=Shape%20Image%20View,bitmap%20mask%20based%20image%20views.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ViewPager mviewpager = (ViewPager)findViewById(R.id.viewpager);
        ImageAdapter adapterview = new ImageAdapter(SecondActivity.this);
        mviewpager.setAdapter(adapterview);
    }
}
