package com.example.gpay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("responsedata")
    @Expose
    private PaginationResponseData responsedata;

    public PaginationResponseData getResponsedata() {
        return responsedata;
    }

    public void setResponsedata(PaginationResponseData responsedata) {
        this.responsedata = responsedata;
    }
}
