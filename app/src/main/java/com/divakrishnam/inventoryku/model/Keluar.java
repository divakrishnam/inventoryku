package com.divakrishnam.inventoryku.model;

import com.google.gson.annotations.SerializedName;

public class Keluar {
    @SerializedName("kode_barang_keluar")
    private String kodeBarangKeluar;
    @SerializedName("kode_barang")
    private String kodeBarang;
    @SerializedName("nama_barang")
    private String namaBarang;
    @SerializedName("gambar_barang")
    private String gambarBarang;
    @SerializedName("nama_konsumen")
    private String namaKonsumen;
    @SerializedName("alamat_konsumen")
    private String alamatKonsumen;
    @SerializedName("kontak_konsumen")
    private String kontakKonsumen;
    @SerializedName("kuantitas_barang_keluar")
    private String kuantitasBarangKeluar;
    @SerializedName("tanggal_waktu_barang_keluar")
    private String tanggalWaktuBarangKeluar;
    @SerializedName("catatan_barang_keluar")
    private String catatanBarangKeluar;

    public Keluar(String kodeBarangKeluar, String kodeBarang, String namaBarang, String gambarBarang, String namaKonsumen, String alamatKonsumen, String kontakKonsumen, String kuantitasBarangKeluar, String tanggalWaktuBarangKeluar, String catatanBarangKeluar) {
        this.kodeBarangKeluar = kodeBarangKeluar;
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.gambarBarang = gambarBarang;
        this.namaKonsumen = namaKonsumen;
        this.alamatKonsumen = alamatKonsumen;
        this.kontakKonsumen = kontakKonsumen;
        this.kuantitasBarangKeluar = kuantitasBarangKeluar;
        this.tanggalWaktuBarangKeluar = tanggalWaktuBarangKeluar;
        this.catatanBarangKeluar = catatanBarangKeluar;
    }

    public String getKodeBarangKeluar() {
        return kodeBarangKeluar;
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

    public String getNamaKonsumen() {
        return namaKonsumen;
    }

    public String getAlamatKonsumen() {
        return alamatKonsumen;
    }

    public String getKontakKonsumen() {
        return kontakKonsumen;
    }

    public String getKuantitasBarangKeluar() {
        return kuantitasBarangKeluar;
    }

    public String getTanggalWaktuBarangKeluar() {
        return tanggalWaktuBarangKeluar;
    }

    public String getCatatanBarangKeluar() {
        return catatanBarangKeluar;
    }
}
