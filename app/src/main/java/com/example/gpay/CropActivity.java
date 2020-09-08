package com.example.gpay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class CropActivity extends AppCompatActivity {
Button btn,up;
ImageView img;
    String picturePath;
    File file;
Uri mimage;
    String selectedPath="";
    private final int GALLERY_ACTIVITY_CODE=200;
    private final int RESULT_CROP = 400;
    private static final int PICK_FROM_GALLERY = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        btn =(Button) findViewById(R.id.crop_btn);
        up =(Button) findViewById(R.id.upload);
        img = (ImageView) findViewById(R.id.img);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile(file, "1", "meat", "noufal");
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setMinCropResultSize(300,300)
                        .setMaxCropResultSize(300,300)
                        .start(CropActivity.this);

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
                img.setImageURI(resultUri);
                Uri selectedFileUri = data.getData();
                Log.e("the"," file is "+selectedFileUri);
                selectedPath = FileUtils.getPath(getApplicationContext(), resultUri);
                img.setImageURI(resultUri);
                file=new File(selectedPath);
                Log.e("the"," file is "+file);

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
        AndroidNetworking.upload("http://dailyestoreapp.com/dailyestore/api/addItems")
                .addMultipartFile("image",file)
                .addMultipartParameter("typeId", "1")
                .addMultipartParameter("subId", "1")
                .addMultipartParameter("itemName","HP")
                .addMultipartParameter("description","test")
                .addMultipartParameter("quantity","1")
                .addMultipartParameter("price","250")
                .addMultipartParameter("status","1")
                .addMultipartParameter("createdBy","1")
                .setTag("uploads/items/")
                .setPriority(Priority.HIGH).doNotCacheResponse()
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {

                        // do anything with progress

                    }
                })
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response","response = "+response);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        //  progressDialog.dismiss();
//                        // FileUtils.deleteCache(PostActivity.this.getApplicationContext());
//                        Log.e("CATEGORYList---->", "" + jsonObject);
//
//
//                        String result = null;
//                        JSONObject fullResponseObject = null;
////                        try {
////                            fullResponseObject = jsonObject;
////
////                         //   result = fullResponseObject.getString(Constant.API_RESPONSE);
////
////
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
//
//
////                        try {
////
////                            JSONArray jsonTokenBased = fullResponseObject.getJSONArray(Constant.API_TOKEN_RESPONSE);
////                            for (int i = 0; i < jsonTokenBased.length(); i++) {
////                                JSONObject jsonTokenObject = jsonTokenBased.getJSONObject(i);
////                                String authenticateTokenMsg = jsonTokenObject.getString(Constant.API_AUTHENTICATE_TOKEN_KEY);
////                                String refreshToken = jsonTokenObject.getString(Constant.API_TOKEN_KEY);
////                                if (refreshToken != null && !refreshToken.equalsIgnoreCase("null")) {
////                                    HelperClass.insertingRefreshToken(PostActivity.this, refreshToken);
////                                }
////                                else {
////
////                                    if (authenticateTokenMsg.contains(Constant.API_TOKEN_AUTHORIZATION_FAILURE)) {
////                                        uploadParameter("", categoryId, ADID, header, description, date,publisher);
////                                    } else {
////                                        showToast(authenticateTokenMsg);
////                                        HelperClass.LogOut(PostActivity.this);
////                                    }
////
////                                }
////                            }
////
////
////                            if (result != null && !result.equalsIgnoreCase("null")) {
////                                try{
////                                    JSONArray jsonArray = new JSONArray(result);
////                                    String postValue=jsonArray.getString(0);
////                                    String postResponse=postValue.trim();
////                                    if(postResponse.equals("Post updated")){
////                                        showToast("Successfully Posted");
////                                        finish();
////                                    }else{
////                                        showToast(postResponse);
////                                    }
////
////                                }
////                                catch(Exception e){
////                                    e.printStackTrace();
////                                }
////                            }
////
////
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                            Log.e("DecryptCategory ->", "" + e);
////                        }
//
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
////                        progressDialog.dismiss();
////                        FileUtils.deleteCache(PostActivity.this.getApplicationContext());
//
//                        Log.e("RESPONSE",""+anError);
//                        Log.d("", "onError errorCode : " + anError.getErrorCode());
//                        Log.d("", "onError errorBody : " + anError.getErrorBody());
//                        Log.d("", "onError errorDetail : " + anError.getErrorDetail());
////                        if(anError.toString().contains("javax.net.ssl.SSLPeerUnverifiedException") && count==1){
////                            uploadFile(file,categoryId,ADID, header, description,  date,  publisher);
////                            Log.e("Request->",""+file+categoryId+ADID+header+description+date+publisher);
////                           // ++count;
////
////
////                        }
////                        else{
////                          //  showToast("Unable to connect with server,please try after sometime!!");
////
////                        }
//                    }
//
//
//                });


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