package com.example.gpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import models.UserParams;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRetrofitActivity extends AppCompatActivity {
    RecyclerView recyclerView_offers;
    Offers_ItemAdapter customAdapter_offers;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    ArrayList Images_offers = new ArrayList<>(Arrays.asList(R.drawable.ic_access_alarm_black_24dp,R.drawable.ic_access_alarm_black_24dp, R.drawable.ic_access_alarm_black_24dp, R.drawable.ic_access_alarm_black_24dp,R.drawable.ic_access_alarm_black_24dp));
    ArrayList Images_images = new ArrayList<>(Arrays.asList(R.drawable.ic_access_alarm_black_24dp,R.drawable.ic_access_alarm_black_24dp, R.drawable.ic_access_alarm_black_24dp, R.drawable.ic_access_alarm_black_24dp,R.drawable.ic_access_alarm_black_24dp,R.drawable.ic_access_alarm_black_24dp));
    ArrayList personNames_offers = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6"));
    ArrayList<Items>dataarraylist = new ArrayList<>();
    int page = 1,limit=7;
    UserParams user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_retrofit);
        nestedScrollView=(NestedScrollView)findViewById(R.id.scroll_view);
        progressBar=findViewById(R.id.progress_bar_post);
       // recyclerView_offers = (RecyclerView) findViewById(R.id.itemrecycler_offers_post);
        // set a LinearLayoutManager with default vertical orientation
       // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
       // recyclerView_offers.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
 user = new UserParams();
user.setUserId(1);
       // customAdapter_offers = new Offers_ItemAdapter(getApplicationContext(), dataarraylist);
     //   recyclerView_offers.setAdapter(customAdapter_offers);

        getData(page,limit);
//        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if(scrollY== v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight())
//                {
//                    page++;
//                    progressBar.setVisibility(View.VISIBLE);
//                    getData(page,limit);
//                }
//            }
//        });
    }

    private void getData(int page, int limit) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
       OkHttpClient okHttpClient = new OkHttpClient.Builder()
               .addInterceptor(loggingInterceptor)
               .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dailyestoreapp.com/dailyestore/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        MainInterface mainInterface = retrofit.create(MainInterface.class);
        Call<Result1> resultCall = mainInterface.userdetails(1);
        resultCall.enqueue(new Callback<Result1>() {
            @Override
            public void onResponse(Call<Result1> call, Response<Result1> response) {



                Log.e("response","post result"+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()) );
            String res= new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getResponsedata());
                JsonObject obj = new JsonParser().parse(res).getAsJsonObject();
            Log.e("response je","post result"+obj);
            }

            @Override
            public void onFailure(Call<Result1> call, Throwable t) {
                Log.e("response failure","post result"+t.getMessage());
            }
        });


    }

    private void parseJson(JSONArray jsonArray) {
        for(int i =0; i<jsonArray.length();i++)
        {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                Items dt = new Items();
                dt.setImage(object.getString("download_url"));
                dt.setName(object.getString("author"));
                dataarraylist.add(dt);
                customAdapter_offers = new Offers_ItemAdapter(getApplicationContext(),dataarraylist);
                recyclerView_offers.setAdapter(customAdapter_offers);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("error","er"+e);
            }
        }
    }
}

