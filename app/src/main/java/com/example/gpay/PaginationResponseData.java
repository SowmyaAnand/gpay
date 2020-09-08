package com.example.gpay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaginationResponseData {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private List<Paginationdata> data = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Paginationdata> getData() {
        return data;
    }

    public void setData(List<Paginationdata> data) {
        this.data = data;
    }
}
