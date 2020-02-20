package com.divakrishnam.inventoryku.model;

import com.google.gson.annotations.SerializedName;

public class ResponseInfo {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    public ResponseInfo(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
