package com.divakrishnam.inventoryku.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Barang implements Parcelable {
    @SerializedName("kode_barang")
    private String kodeBarang;
    @SerializedName("nama_barang")
    private String namaBarang;
    @SerializedName("kuantitas_barang")
    private String kuantitasBarang;
    @SerializedName("catatan_barang")
    private String catatanBarang;
    @SerializedName("gambar_barang")
    private String gambarBarang;

    public Barang(String kodeBarang, String namaBarang, String kuantitasBarang, String catatanBarang, String gambarBarang) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.kuantitasBarang = kuantitasBarang;
        this.catatanBarang = catatanBarang;
        this.gambarBarang = gambarBarang;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getKuantitasBarang() {
        return kuantitasBarang;
    }

    public String getCatatanBarang() {
        return catatanBarang;
    }

    public String getGambarBarang() {
        return gambarBarang;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kodeBarang);
        dest.writeString(this.namaBarang);
        dest.writeString(this.kuantitasBarang);
        dest.writeString(this.catatanBarang);
        dest.writeString(this.gambarBarang);
    }

    protected Barang(Parcel in) {
        this.kodeBarang = in.readString();
        this.namaBarang = in.readString();
        this.kuantitasBarang = in.readString();
        this.catatanBarang = in.readString();
        this.gambarBarang = in.readString();
    }

    public static final Parcelable.Creator<Barang> CREATOR = new Parcelable.Creator<Barang>() {
        @Override
        public Barang createFromParcel(Parcel source) {
            return new Barang(source);
        }

        @Override
        public Barang[] newArray(int size) {
            return new Barang[size];
        }
    };
}
