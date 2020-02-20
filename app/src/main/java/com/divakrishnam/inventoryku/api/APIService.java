package com.divakrishnam.inventoryku.api;

import com.divakrishnam.inventoryku.model.ResponseBarang;
import com.divakrishnam.inventoryku.model.ResponseInfo;
import com.divakrishnam.inventoryku.model.ResponseKeluar;
import com.divakrishnam.inventoryku.model.ResponseKonsumen;
import com.divakrishnam.inventoryku.model.ResponseMasuk;
import com.divakrishnam.inventoryku.model.ResponsePemasok;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {
    // Barang
    @GET("barang")
    Call<ResponseBarang> getBarang();

    @GET("barang")
    Call<ResponseBarang> getBarangDetail(@Query("kode") String kode);

    @Multipart
    @POST("barang")
    Call<ResponseInfo> postGambarBarang(
            @Part("kode") RequestBody kode,
            @Part("nama") RequestBody nama,
            @Part("catatan") RequestBody catatan,
            @Part MultipartBody.Part gambar
    );

    @FormUrlEncoded
    @POST("barang")
    Call<ResponseInfo> postBarang(
            @Field("kode") String kode,
            @Field("nama") String nama,
            @Field("catatan") String catatan
    );

    @Multipart
    @POST("barang/update")
    Call<ResponseInfo> putGambarBarang(
            @Part("kode") RequestBody kode,
            @Part("nama") RequestBody nama,
            @Part("catatan") RequestBody catatan,
            @Part MultipartBody.Part gambar
    );

    @FormUrlEncoded
    @POST("barang/update")
    Call<ResponseInfo> putBarang(
            @Field("kode") String kode,
            @Field("nama") String nama,
            @Field("catatan") String catatan
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "barang", hasBody = true)
    Call<ResponseInfo> deleteBarang(@Field("kode") String kode);

    // Pemasok
    @GET("pemasok")
    Call<ResponsePemasok> getPemasok();

    @Multipart
    @POST("pemasok")
    Call<ResponseInfo> postGambarPemasok(
            @Part("nama") RequestBody nama,
            @Part("alamat") RequestBody alamat,
            @Part("kontak") RequestBody kontak,
            @Part MultipartBody.Part gambar
    );

    @FormUrlEncoded
    @POST("pemasok")
    Call<ResponseInfo> postPemasok(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("kontak") String kontak
    );

    @Multipart
    @POST("pemasok/update")
    Call<ResponseInfo> putGambarPemasok(
            @Part("kode") RequestBody kode,
            @Part("nama") RequestBody nama,
            @Part("alamat") RequestBody alamat,
            @Part("kontak") RequestBody kontak,
            @Part MultipartBody.Part gambar
    );

    @FormUrlEncoded
    @POST("pemasok/update")
    Call<ResponseInfo> putPemasok(
            @Field("kode") String kode,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("kontak") String kontak
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "pemasok", hasBody = true)
    Call<ResponseInfo> deletePemasok(@Field("kode") String kode);

    // Konsumen
    @GET("konsumen")
    Call<ResponseKonsumen> getKonsumen();

    @Multipart
    @POST("konsumen")
    Call<ResponseInfo> postGambarKonsumen(
            @Part("nama") RequestBody nama,
            @Part("alamat") RequestBody alamat,
            @Part("kontak") RequestBody kontak,
            @Part MultipartBody.Part gambar
    );

    @FormUrlEncoded
    @POST("konsumen")
    Call<ResponseInfo> postKonsumen(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("kontak") String kontak
    );

    @Multipart
    @POST("konsumen/update")
    Call<ResponseInfo> putGambarKonsumen(
            @Part("kode") RequestBody kode,
            @Part("nama") RequestBody nama,
            @Part("alamat") RequestBody alamat,
            @Part("kontak") RequestBody kontak,
            @Part MultipartBody.Part gambar
    );

    @FormUrlEncoded
    @POST("konsumen/update")
    Call<ResponseInfo> putKonsumen(
            @Field("kode") String kode,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("kontak") String kontak
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "konsumen", hasBody = true)
    Call<ResponseInfo> deleteKonsumen(@Field("kode") String kode);

    // Masuk
    @GET("masuk")
    Call<ResponseMasuk> getMasuk();

    @FormUrlEncoded
    @POST("masuk")
    Call<ResponseInfo> postMasuk(@Field("kode_barang") String kode_barang,
                                 @Field("kode_pemasok") String kode_pemasok,
                                 @Field("kuantitas") String kuantitas,
                                 @Field("tanggal_waktu") String tanggal_waktu,
                                 @Field("catatan") String catatan
                                 );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "masuk", hasBody = true)
    Call<ResponseInfo> deleteMasuk(@Field("kode") String kode);

    // Keluar
    @GET("keluar")
    Call<ResponseKeluar> getKeluar();

    @FormUrlEncoded
    @POST("keluar")
    Call<ResponseInfo> postKeluar(@Field("kode_barang") String kode_barang,
                                 @Field("kode_konsumen") String kode_konsumen,
                                 @Field("kuantitas") String kuantitas,
                                 @Field("tanggal_waktu") String tanggal_waktu,
                                 @Field("catatan") String catatan
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "masuk", hasBody = true)
    Call<ResponseInfo> deleteKeluar(@Field("kode") String kode);
}
