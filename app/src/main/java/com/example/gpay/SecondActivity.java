package com.example.gpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class SecondActivity extends AppCompatActivity {
    ViewPager mviewpager;
    String selectedPath="";
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
        String url = "http://dailyestoreapp.com/dailyestore/api/listCategory";
        JSONObject obj = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(SecondActivity.this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);

                        Log.e("RESPONSE",""+response.toString());
//                        try {
//                            JSONObject jsonArray = response.getJSONObject("responsedata");
//                            Log.e("RESPONSE",""+jsonArray);
//                            JSONArray jarray = jsonArray.getJSONArray("data");
//                            Log.e("RESPONSE",""+jarray);
//                            JSONObject ja= jarray.getJSONObject(0);
//                            String userid = ja.getString("firstName");
//                            Log.e("RESPONSE",""+userid);
//                            Log.e("RESPONSE",""+ja);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Log.e("RESPONSE error",""+e);
//                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("ERROR",""+error);
                    }
                });

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsObjRequest);
        Log.d("request>>>>>>", queue.toString());
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
