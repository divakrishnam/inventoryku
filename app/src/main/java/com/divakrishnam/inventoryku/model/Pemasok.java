package com.divakrishnam.inventoryku.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Pemasok implements Parcelable {
    @SerializedName("kode_pemasok")
    private String kodePemasok;
    @SerializedName("nama_pemasok")
    private String namaPemasok;
    @SerializedName("alamat_pemasok")
    private String alamatPemasok;
    @SerializedName("kontak_pemasok")
    private String kontakPemasok;
    @SerializedName("gambar_pemasok")
    private String gambarPemasok;

    public Pemasok(String kodePemasok, String namaPemasok, String alamatPemasok, String kontakPemasok, String gambarPemasok) {
        this.kodePemasok = kodePemasok;
        this.namaPemasok = namaPemasok;
        this.alamatPemasok = alamatPemasok;
        this.kontakPemasok = kontakPemasok;
        this.gambarPemasok = gambarPemasok;
    }

    public String getKodePemasok() {
        return kodePemasok;
    }

    public String getNamaPemasok() {
        return namaPemasok;
    }

    public String getAlamatPemasok() {
        return alamatPemasok;
    }

    public String getKontakPemasok() {
        return kontakPemasok;
    }

    public String getGambarPemasok() {
        return gambarPemasok;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kodePemasok);
        dest.writeString(this.namaPemasok);
        dest.writeString(this.alamatPemasok);
        dest.writeString(this.kontakPemasok);
        dest.writeString(this.gambarPemasok);
    }

    protected Pemasok(Parcel in) {
        this.kodePemasok = in.readString();
        this.namaPemasok = in.readString();
        this.alamatPemasok = in.readString();
        this.kontakPemasok = in.readString();
        this.gambarPemasok = in.readString();
    }

    public static final Parcelable.Creator<Pemasok> CREATOR = new Parcelable.Creator<Pemasok>() {
        @Override
        public Pemasok createFromParcel(Parcel source) {
            return new Pemasok(source);
        }

        @Override
        public Pemasok[] newArray(int size) {
            return new Pemasok[size];
        }
    };
}
