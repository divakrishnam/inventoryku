package com.divakrishnam.inventoryku.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Konsumen implements Parcelable {
    @SerializedName("kode_konsumen")
    private String kodeKonsumen;
    @SerializedName("nama_konsumen")
    private String namaKonsumen;
    @SerializedName("alamat_konsumen")
    private String alamatKonsumen;
    @SerializedName("kontak_konsumen")
    private String kontakKonsumen;
    @SerializedName("gambar_konsumen")
    private String gambarKonsumen;

    public Konsumen(String kodeKonsumen, String namaKonsumen, String alamatKonsumen, String kontakKonsumen, String gambarKonsumen) {
        this.kodeKonsumen = kodeKonsumen;
        this.namaKonsumen = namaKonsumen;
        this.alamatKonsumen = alamatKonsumen;
        this.kontakKonsumen = kontakKonsumen;
        this.gambarKonsumen = gambarKonsumen;
    }

    public String getKodeKonsumen() {
        return kodeKonsumen;
    }

    public String getNamaKonsumen() {
        return namaKonsumen;
    }

    public String getAlamatKonsumen() {
        return alamatKonsumen;
    }

    public String getKontakKonsumen() {
        return kontakKonsumen;
    }

    public String getGambarKonsumen() {
        return gambarKonsumen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kodeKonsumen);
        dest.writeString(this.namaKonsumen);
        dest.writeString(this.alamatKonsumen);
        dest.writeString(this.kontakKonsumen);
        dest.writeString(this.gambarKonsumen);
    }

    protected Konsumen(Parcel in) {
        this.kodeKonsumen = in.readString();
        this.namaKonsumen = in.readString();
        this.alamatKonsumen = in.readString();
        this.kontakKonsumen = in.readString();
        this.gambarKonsumen = in.readString();
    }

    public static final Parcelable.Creator<Konsumen> CREATOR = new Parcelable.Creator<Konsumen>() {
        @Override
        public Konsumen createFromParcel(Parcel source) {
            return new Konsumen(source);
        }

        @Override
        public Konsumen[] newArray(int size) {
            return new Konsumen[size];
        }
    };
}
