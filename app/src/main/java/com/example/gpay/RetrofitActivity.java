package com.example.gpay;
//Need to add a maininterface nd item
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitActivity extends AppCompatActivity {
    RecyclerView recyclerView_offers;
    Offers_ItemAdapter customAdapter_offers;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    ArrayList Images_offers = new ArrayList<>(Arrays.asList(R.drawable.ic_access_alarm_black_24dp,R.drawable.ic_access_alarm_black_24dp, R.drawable.ic_access_alarm_black_24dp, R.drawable.ic_access_alarm_black_24dp,R.drawable.ic_access_alarm_black_24dp));
    ArrayList Images_images = new ArrayList<>(Arrays.asList(R.drawable.ic_access_alarm_black_24dp,R.drawable.ic_access_alarm_black_24dp, R.drawable.ic_access_alarm_black_24dp, R.drawable.ic_access_alarm_black_24dp,R.drawable.ic_access_alarm_black_24dp,R.drawable.ic_access_alarm_black_24dp));
    ArrayList personNames_offers = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6"));
ArrayList<Items>dataarraylist = new ArrayList<>();
int page = 1,limit=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        nestedScrollView=(NestedScrollView)findViewById(R.id.scroll_view);
        progressBar=findViewById(R.id.progress_bar);
        recyclerView_offers = (RecyclerView) findViewById(R.id.itemrecycler_offers);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_offers.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter

        customAdapter_offers = new Offers_ItemAdapter(getApplicationContext(), dataarraylist);
        recyclerView_offers.setAdapter(customAdapter_offers);
        getData(page,limit);
nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if(scrollY== v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight())
        {
            page++;
            progressBar.setVisibility(View.VISIBLE);
            getData(page,limit);
        }
    }
});
    }

    private void getData(int page, int limit) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dailyestoreapp.com/dailyestore/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        MainInterface mainInterface = retrofit.create(MainInterface.class);
        Call<String> call = mainInterface.STRING_CALL(page,limit);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()&& response.body()!=null)
                {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        parseJson(jsonArray);
                    } catch (JSONException e) {
                        Log.e("error","er"+e);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("error","er"+call);
                Log.e("error","er"+t);
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
