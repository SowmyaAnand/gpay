package com.example.gpay;

import models.Result;
import models.UserParams;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MainInterface {

    @GET("listUser")
    Call<String> STRING_CALL(
            @Query("offset") int page,
            @Query("limit") int limit
    );
@FormUrlEncoded
    @POST("listUser")
    Call<Pagination>userdetails(@Field("limit") int id,
                             @Field("start") int startid);

}
