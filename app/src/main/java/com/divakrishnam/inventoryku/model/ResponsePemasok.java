package com.divakrishnam.inventoryku.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePemasok {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Pemasok> data;

    public ResponsePemasok(int status, String message, List<Pemasok> data) {
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

    public List<Pemasok> getData() {
        return data;
    }
}
