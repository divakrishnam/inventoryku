package com.divakrishnam.inventoryku.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKonsumen {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Konsumen> data;

    public ResponseKonsumen(int status, String message, List<Konsumen> data) {
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

    public List<Konsumen> getData() {
        return data;
    }
}
