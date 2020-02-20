package com.divakrishnam.inventoryku.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBarang {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Barang> data;

    public ResponseBarang(int status, String message, List<Barang> data) {
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

    public List<Barang> getData() {
        return data;
    }
}
