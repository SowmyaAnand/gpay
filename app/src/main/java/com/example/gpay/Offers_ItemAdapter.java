package com.example.gpay;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;


public class Offers_ItemAdapter  extends RecyclerView.Adapter<Offers_ItemAdapter.MyViewHolder> {
    ArrayList<String> personNames = new ArrayList<String>();
    Context context;
    ArrayList Images;
    private Activity activity;
private ArrayList<Items>data;
    ArrayList<String> lts = new ArrayList<String>();
    ArrayList personNames_offers = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6", "ITEM7"));
    int quantity = 1;

    public Offers_ItemAdapter(Context context, ArrayList<Items> data) {
        this.context = context;
        this.data = data;



    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_items, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
Items datas = this.data.get(position);
Glide.with(context).load(datas.getImage())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(holder.image_image);
holder.name.setText(datas.getName());
        // set the data in items
//        String name = (String) personNames.get(position);
//        int n= (int) Images.get(position);
//        holder.image_image.setImageResource(n);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("person","person"+personNames);

            }
        });
        holder.outofstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("url","url");


                final String url = "http://dailyestoreapp.com/dailyestore/api/activateItem";

                final JSONObject obj = new JSONObject();
                try {
                    obj.put("itemId", 1);
                    obj.put("status", "activate");
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                RequestQueue queue = Volley.newRequestQueue(context);
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,url,obj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println(response);

                                Log.e("url",""+url);
                                Log.e("obj",""+obj);
                                Log.e("RESPONSE",""+response.toString());

                                // response start
                                JSONObject sub = new JSONObject();
                                try {
                                    sub.put("success","0");
                                    sub.put("data",0);
                                    response.put("responsedata",sub);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

//response end
                                try {
                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    if(jsonObject.has("responsedata")) {
                                        Log.e("jsonObject","jsonObject is "+jsonObject);
                                        JSONObject json2 = new JSONObject(String.valueOf(jsonObject));
                                        JSONObject res = (JSONObject) json2.get("responsedata");
                                        JSONObject actualvalue = new JSONObject(res.toString());
                                        Log.e("json2","json2is "+json2);
                                        Log.e("res","res is"+res);
                                        Log.e("actualvalue","actualvalue is"+actualvalue);
                                        String result = actualvalue.getString("success");
                                        Log.e("actualvalue","success  is"+result);

                                        if(result.equals("0"))
                                        {
                                            String text = holder.outofstock.getText().toString();
                                            if(text.equals("Activate"))
                                            {
                                                text="OUT OF STOCK";
                                                holder.outofstock.setText(text);

                                            }
                                            else
                                            {
                                                text="Activate";
                                                holder.outofstock.setText(text);

                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(context,"Something Went Wrong .Try After Sometime",Toast.LENGTH_LONG).show();
                                        }
                                        // Object json2 = jsonObject.get("responsedata");
                                        // Log.e("json2","json2 is "+json2);

                                        // Object result = jsonObject.get("success");
                                        //  Log.e("success","success is "+result);


                                        // String token = jsonObject.getString("token");
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // progressDialog.dismiss();
                                // showToast("Unable to connect Server,please try after sometime!");
                                Log.e("ERROR",""+error);
                            }
                        });

                jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                        20000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(jsObjRequest);
                Log.d("request>>>>>>", queue.toString());

                // CustomDialogClass1 cdd1=new CustomDialogClass1(context,position);
                // cdd1.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantityy;// init the item view's
        Button outofstock;
        ImageView image_image;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Title);
            outofstock = (Button) itemView.findViewById(R.id.outofstock);
            image_image=(ImageView)itemView.findViewById(R.id.im);

        }
    }


    private void Activate(final int itemIdparam, final String statusparam){
        final String url = "http://dailyestoreapp.com/dailyestore/api/activateItem";

        final JSONObject obj = new JSONObject();
        try {
            obj.put("itemId", itemIdparam);
            obj.put("status", statusparam);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,url,obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);

                        Log.e("url",""+url);
                        Log.e("obj",""+obj);
                        Log.e("RESPONSE",""+response.toString());

                        // response start
                        JSONObject sub = new JSONObject();
                        try {
                            sub.put("success","0");
                            sub.put("data",0);
                            response.put("responsedata",sub);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//response end
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if(jsonObject.has("responsedata")) {
                                Log.e("jsonObject","jsonObject is "+jsonObject);
                                JSONObject json2 = new JSONObject(String.valueOf(jsonObject));
                                JSONObject res = (JSONObject) json2.get("responsedata");
                                JSONObject actualvalue = new JSONObject(res.toString());
                                Log.e("json2","json2is "+json2);
                                Log.e("res","res is"+res);
                                Log.e("actualvalue","actualvalue is"+actualvalue);
                                String result = actualvalue.getString("success");
                                Log.e("actualvalue","success  is"+result);
                                if(result.equals("0"))
                                {

                                }
                                else
                                {

                                }
                                // Object json2 = jsonObject.get("responsedata");
                                // Log.e("json2","json2 is "+json2);

                                // Object result = jsonObject.get("success");
                                //  Log.e("success","success is "+result);


                                // String token = jsonObject.getString("token");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // progressDialog.dismiss();
                        // showToast("Unable to connect Server,please try after sometime!");
                        Log.e("ERROR",""+error);
                    }
                });

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsObjRequest);
        Log.d("request>>>>>>", queue.toString());
        //                String URL ="http://dailyestoreapp.com/dailyestore/api/userDetails";
//                JSONObject jsonBody = new JSONObject();
//                try {
//                    jsonBody.put("userId", "1");
//                    final String requestBody = jsonBody.toString();
//
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.e("VOLLEY", response);
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.e("VOLLEY", error.toString());
//                        }
//                    }) {
//                        @Override
//                        public String getBodyContentType() {
//                            return "application/json; charset=utf-8";
//                        }
//
//                        @Override
//                        public byte[] getBody() throws AuthFailureError {
//                            try {
//                                return requestBody == null ? null : requestBody.getBytes("utf-8");
//                            } catch (UnsupportedEncodingException uee) {
//                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//                                return null;
//                            }
//                        }
//
//                        @Override
//                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                            String responseString = "";
//                            if (response != null) {
//                                responseString = String.valueOf(response.statusCode);
//                                // can get more details such as response.headers
//                            }
//                            Log.e("tresponse","the response"+response);
//                            return  super.parseNetworkResponse(response);
//                        }
//                    };
//                    queue.add(stringRequest);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.e("error","error"+e);
//                }
    }

}