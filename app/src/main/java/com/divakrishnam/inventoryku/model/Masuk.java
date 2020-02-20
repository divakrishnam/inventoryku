package com.divakrishnam.inventoryku.model;

import com.google.gson.annotations.SerializedName;

public class Masuk {
    @SerializedName("kode_barang_masuk")
    private String kodeBarangMasuk;
    @SerializedName("kode_barang")
    private String kodeBarang;
    @SerializedName("nama_barang")
    private String namaBarang;
    @SerializedName("gambar_barang")
    private String gambarBarang;
    @SerializedName("nama_pemasok")
    private String namaPemasok;
    @SerializedName("alamat_pemasok")
    private String alamatPemasok;
    @SerializedName("kontak_pemasok")
    private String kontakPemasok;
    @SerializedName("kuantitas_barang_masuk")
    private String kuantitasBarangMasuk;
    @SerializedName("tanggal_waktu_barang_masuk")
    private String tanggalWaktuBarangMasuk;
    @SerializedName("catatan_barang_masuk")
    private String catatanBarangMasuk;

    public Masuk(String kodeBarangMasuk, String kodeBarang, String namaBarang, String gambarBarang, String namaPemasok, String alamatPemasok, String kontakPemasok, String kuantitasBarangMasuk, String tanggalWaktuBarangMasuk, String catatanBarangMasuk) {
        this.kodeBarangMasuk = kodeBarangMasuk;
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.gambarBarang = gambarBarang;
        this.namaPemasok = namaPemasok;
        this.alamatPemasok = alamatPemasok;
        this.kontakPemasok = kontakPemasok;
        this.kuantitasBarangMasuk = kuantitasBarangMasuk;
        this.tanggalWaktuBarangMasuk = tanggalWaktuBarangMasuk;
        this.catatanBarangMasuk = catatanBarangMasuk;
    }

    public String getKodeBarangMasuk() {
        return kodeBarangMasuk;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getGambarBarang() {
        return gambarBarang;
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

    public String getKuantitasBarangMasuk() {
        return kuantitasBarangMasuk;
    }

    public String getTanggalWaktuBarangMasuk() {
        return tanggalWaktuBarangMasuk;
    }

    public String getCatatanBarangMasuk() {
        return catatanBarangMasuk;
    }
}
