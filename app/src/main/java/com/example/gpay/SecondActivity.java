package com.example.gpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SecondActivity extends AppCompatActivity {
    ViewPager mviewpager;
    //Customize image
   // https://github.com/siyamed/android-shape-imageview#:~:text=Shape%20Image%20View,bitmap%20mask%20based%20image%20views.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
         mviewpager = (ViewPager)findViewById(R.id.viewpager);
        ImageAdapter adapterview = new ImageAdapter(SecondActivity.this);
        mviewpager.setAdapter(adapterview);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Mytimertask(),800,1600);
    }
    public class Mytimertask extends TimerTask{
        @Override
        public void run() {
            SecondActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                if(mviewpager.getCurrentItem() ==0)
                {
mviewpager.setCurrentItem(1);
                }
                else if(mviewpager.getCurrentItem()==1)
                {
                    mviewpager.setCurrentItem(2);

                }
                else
                {
                    mviewpager.setCurrentItem(0);
                }
                }
            });
        }
    }

}
