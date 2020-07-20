package com.example.gpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ViewPager mviewpager = (ViewPager)findViewById(R.id.viewpager);
        ImageAdapter adapterview = new ImageAdapter(SecondActivity.this);
        mviewpager.setAdapter(adapterview);
    }
}
