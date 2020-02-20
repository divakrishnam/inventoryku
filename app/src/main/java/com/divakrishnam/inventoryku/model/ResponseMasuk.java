package com.divakrishnam.inventoryku.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMasuk {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Masuk> data;

    public ResponseMasuk(int status, String message, List<Masuk> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Masuk> getData() {
        return data;
    }
}
