package com.divakrishnam.inventoryku.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKeluar {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Keluar> data;

    public ResponseKeluar(int status, String message, List<Keluar> data) {
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

    public List<Keluar> getData() {
        return data;
    }
}
