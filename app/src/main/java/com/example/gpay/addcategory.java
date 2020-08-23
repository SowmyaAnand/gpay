package com.example.gpay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class addcategory {

    @SerializedName("responsedata")
    @Expose
    private Responsedata2 responsedata;

    public Responsedata2 getResponsedata() {
        return responsedata;
    }

    public void setResponsedata(Responsedata2 responsedata) {
        this.responsedata = responsedata;
    }

}
