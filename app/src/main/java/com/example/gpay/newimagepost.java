package com.example.gpay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class newimagepost extends AppCompatActivity {
Button add,up,test;
ImageView img;

    String selectedPath="";
    private final static int RESULT_SELECT_IMAGE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String TAG = "GalleryUtil";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newimagepost);
        add=(Button)findViewById(R.id.add);
        img=(ImageView) findViewById(R.id.img);
        up=(Button)findViewById(R.id.upload);
        test=(Button)findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://dailyestoreapp.com/dailyestore/api/listAllCategoryItem";
                JSONObject obj = new JSONObject();
//                try {
//                    obj.put("typeId", 1);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                RequestQueue queue = Volley.newRequestQueue(newimagepost.this);
                String URL ="http://dailyestoreapp.com/dailyestore/api/listAllCategoryItem";
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("typeId", 1);
                    final String requestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("VOLLEY", response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);
                                // can get more details such as response.headers
                            }
                            Log.e("tresponse","the response"+response);
                            return  super.parseNetworkResponse(response);
                        }
                    };
                    queue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("error","error"+e);
                }


//                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,url,obj,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                System.out.println(response);
//
//                                Log.e("RESPONSE",""+response.toString());
//
//
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // progressDialog.dismiss();
//                                // showToast("Unable to connect Server,please try after sometime!");
//                                Log.e("ERROR",""+error);
//                            }
//                        });
//
//                jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
//                        20000,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                queue.add(jsObjRequest);
//                Log.d("request>>>>>>", queue.toString());
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setMinCropResultSize(913,606)
                        .setMaxCropResultSize(913,606)
                        .start(newimagepost.this);

            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file=new File(selectedPath);

                uploadFile(file, "1", "meat", "noufal");
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.e("selected img","selected img"+resultUri);
                Uri selectedFileUri = data.getData();
                selectedPath = FileUtils.getPath(getApplicationContext(), selectedFileUri);
                img.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    public void uploadFile(final File file, final String typeid, final String subname, final String createdby){
Log.e("test","path="+file);
        ArrayList<String> dt= new ArrayList<String>(20);
        dt.add(0,"1");
        dt.add(1,"test");
        dt.add(2,"sowmya");
        //        String refreshToken = HelperClass.getRefreshToken(PostActivity.this);
//        String userADID = HelperClass.getUserADID(PostActivity.this);
//
//        final ProgressDialog progressDialog=new ProgressDialog(PostActivity.this);
//        progressDialog.setCancelable(false);
//        progressDialog.setTitle("Please Wait");
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
        AndroidNetworking.enableLogging();
        AndroidNetworking.upload("http://dailyestoreapp.com/dailyestore/api/addsubcategory")
                .addMultipartFile("subItemImage",file)
                .addMultipartParameter("idata", String.valueOf(dt))
                .addMultipartParameter("subName", subname)
                .addMultipartParameter("createdBy",createdby)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH).doNotCacheResponse()
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {

                        // do anything with progress

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                      //  progressDialog.dismiss();
                       // FileUtils.deleteCache(PostActivity.this.getApplicationContext());
                        Log.e("CATEGORYList---->", "" + jsonObject);


                        String result = null;
                        JSONObject fullResponseObject = null;
//                        try {
//                            fullResponseObject = jsonObject;
//
//                         //   result = fullResponseObject.getString(Constant.API_RESPONSE);
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }


//                        try {
//
//                            JSONArray jsonTokenBased = fullResponseObject.getJSONArray(Constant.API_TOKEN_RESPONSE);
//                            for (int i = 0; i < jsonTokenBased.length(); i++) {
//                                JSONObject jsonTokenObject = jsonTokenBased.getJSONObject(i);
//                                String authenticateTokenMsg = jsonTokenObject.getString(Constant.API_AUTHENTICATE_TOKEN_KEY);
//                                String refreshToken = jsonTokenObject.getString(Constant.API_TOKEN_KEY);
//                                if (refreshToken != null && !refreshToken.equalsIgnoreCase("null")) {
//                                    HelperClass.insertingRefreshToken(PostActivity.this, refreshToken);
//                                }
//                                else {
//
//                                    if (authenticateTokenMsg.contains(Constant.API_TOKEN_AUTHORIZATION_FAILURE)) {
//                                        uploadParameter("", categoryId, ADID, header, description, date,publisher);
//                                    } else {
//                                        showToast(authenticateTokenMsg);
//                                        HelperClass.LogOut(PostActivity.this);
//                                    }
//
//                                }
//                            }
//
//
//                            if (result != null && !result.equalsIgnoreCase("null")) {
//                                try{
//                                    JSONArray jsonArray = new JSONArray(result);
//                                    String postValue=jsonArray.getString(0);
//                                    String postResponse=postValue.trim();
//                                    if(postResponse.equals("Post updated")){
//                                        showToast("Successfully Posted");
//                                        finish();
//                                    }else{
//                                        showToast(postResponse);
//                                    }
//
//                                }
//                                catch(Exception e){
//                                    e.printStackTrace();
//                                }
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Log.e("DecryptCategory ->", "" + e);
//                        }

                    }

                    @Override
                    public void onError(ANError anError) {
//                        progressDialog.dismiss();
//                        FileUtils.deleteCache(PostActivity.this.getApplicationContext());

                        Log.e("RESPONSE",""+anError);
                        Log.d("", "onError errorCode : " + anError.getErrorCode());
                        Log.d("", "onError errorBody : " + anError.getErrorBody());
                        Log.d("", "onError errorDetail : " + anError.getErrorDetail());
//                        if(anError.toString().contains("javax.net.ssl.SSLPeerUnverifiedException") && count==1){
//                            uploadFile(file,categoryId,ADID, header, description,  date,  publisher);
//                            Log.e("Request->",""+file+categoryId+ADID+header+description+date+publisher);
//                           // ++count;
//
//
//                        }
//                        else{
//                          //  showToast("Unable to connect with server,please try after sometime!!");
//
//                        }
                    }


                });


              /*  .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        Log.e("RESPONSE",""+s);
                        if(s.equals("Post updated")){
                            showToast("Successfully Posted");
                            finish();
                        }else{
                            showToast(s);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("RESPONSE",""+anError);
                        Log.d("", "onError errorCode : " + anError.getErrorCode());
                        Log.d("", "onError errorBody : " + anError.getErrorBody());
                        Log.d("", "onError errorDetail : " + anError.getErrorDetail());
                        if(anError.toString().contains("javax.net.ssl.SSLPeerUnverifiedException") && count==1){
                            uploadFile(file,categoryId,ADID, header, description,  date,  publisher);
                            Log.e("Request->",""+file+categoryId+ADID+header+description+date+publisher);
                            ++count;
                        }
                        else{
                            showToast("Unable to connect with server,please try after sometime!!");

                        }
                    }
                });*/
    }

}
