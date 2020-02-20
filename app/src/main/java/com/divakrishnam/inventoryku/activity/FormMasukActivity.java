package com.divakrishnam.inventoryku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.divakrishnam.inventoryku.R;
import com.divakrishnam.inventoryku.api.APIClient;
import com.divakrishnam.inventoryku.api.APIService;
import com.divakrishnam.inventoryku.model.Barang;
import com.divakrishnam.inventoryku.model.Masuk;
import com.divakrishnam.inventoryku.model.Pemasok;
import com.divakrishnam.inventoryku.model.ResponseBarang;
import com.divakrishnam.inventoryku.model.ResponseInfo;
import com.divakrishnam.inventoryku.model.ResponsePemasok;
import com.divakrishnam.inventoryku.util.Urls;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormMasukActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DATA = "data";
    private EditText etKodeBarang, etNamaBarang, etKuantitas, etTanggal, etWaktu, etCatatan;
    private ImageView ivGambar;
    private Spinner spNamaPemasok;
    private Button btnSimpan, btnKurang, btnTambah, btnScan;
    private APIService apiService;

    private int tahun, bulan, hari, jam, menit, kuantitas = 0;

    private String[] keys;
    private List<Pemasok> pemasoks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_masuk);

        etKodeBarang = findViewById(R.id.et_kode_barang);
        etNamaBarang = findViewById(R.id.et_nama_barang);
        etKuantitas = findViewById(R.id.et_kuantitas);
        etTanggal = findViewById(R.id.et_tanggal);
        etWaktu = findViewById(R.id.et_waktu);
        etCatatan = findViewById(R.id.et_catatan);
        ivGambar = findViewById(R.id.iv_gambar);
        spNamaPemasok = findViewById(R.id.sp_nama_instansi);
        btnKurang = findViewById(R.id.btn_kurang);
        btnTambah = findViewById(R.id.btn_tambah);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnScan = findViewById(R.id.btn_scan);

        etWaktu.setOnClickListener(this);
        etTanggal.setOnClickListener(this);
        btnTambah.setOnClickListener(this);
        btnKurang.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
        btnScan.setOnClickListener(this);

        apiService = APIClient.getClient().create(APIService.class);

        Calendar calendar = Calendar.getInstance();
        tahun = calendar.get(Calendar.YEAR);
        bulan = calendar.get(Calendar.MONTH);
        hari = calendar.get(Calendar.DAY_OF_MONTH);
        jam = calendar.get(Calendar.HOUR_OF_DAY);
        menit = calendar.get(Calendar.MINUTE);

        etTanggal.setText(tahun + "-" + String.format("%02d-%02d", (bulan + 1), hari));
        etWaktu.setText(String.format("%02d:%02d:00", jam, menit));
        etKuantitas.setText(kuantitas + "");
        loadPemasok();

    }

    public void simpanData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        int idPemasok = (int) spNamaPemasok.getSelectedItemId();
        String kodePemasok = keys[idPemasok];

        String kodeBarang = etKodeBarang.getText().toString();
        String kuantitas = etKuantitas.getText().toString();
        String tanggalWaktu = etTanggal.getText().toString()+" "+etWaktu.getText().toString();
        String catatan = etCatatan.getText().toString();

        if (!kodeBarang.isEmpty() && idPemasok!=0 && !kuantitas.equals("0") && !tanggalWaktu.isEmpty() && !catatan.isEmpty()) {
                Call<ResponseInfo> call = apiService.postMasuk(kodeBarang, kodePemasok, kuantitas, tanggalWaktu, catatan);
                call.enqueue(new Callback<ResponseInfo>() {
                    @Override
                    public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                        progressDialog.dismiss();
                        if (response.body().getStatus() == 200){
                            finish();
                        }
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseInfo> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error : "+t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        } else {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Something empty", Toast.LENGTH_LONG).show();
        }

    }

    public void loadPemasok() {
        Call<ResponsePemasok> call = apiService.getPemasok();
        call.enqueue(new Callback<ResponsePemasok>() {
            @Override
            public void onResponse(Call<ResponsePemasok> call, Response<ResponsePemasok> response) {
                pemasoks = response.body().getData();
                spinnerPemasok(pemasoks);
            }

            @Override
            public void onFailure(Call<ResponsePemasok> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void spinnerPemasok(List<Pemasok> pemasoks) {
        int size = pemasoks.size() + 1;
        String[] values = new String[size];
        keys = new String[size];
        HashMap<String, String> map;
        int i = 0;
        map = new HashMap<>();
        values[i] = "Pilih Pemasok";
        keys[i] = "0";
        map.put(keys[i], values[i]);
        i++;
        for (Pemasok pemasok : pemasoks) {
            values[i] = pemasok.getNamaPemasok();
            keys[i] = pemasok.getKodePemasok();
            map.put(keys[i], values[i]);
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNamaPemasok.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSimpan){
            simpanData();
        }
        else if (view == etWaktu) {

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            etWaktu.setText(hourOfDay + ":" + minute + ":00");
                        }
                    }, jam, menit, true);
            timePickerDialog.show();

        } else if (view == etTanggal) {

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            etTanggal.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, tahun, bulan, hari);
            datePickerDialog.show();
        } else if (view == btnKurang) {
            if (kuantitas != 0) {
                kuantitas -= 1;
                etKuantitas.setText(kuantitas + "");
            }
        } else if (view == btnTambah) {
            kuantitas += 1;
            etKuantitas.setText(kuantitas + "");
        } else if (view == btnScan) {
            startActivityForResult(new Intent(FormMasukActivity.this, ScanActivity.class), 200);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                String kode = data.getStringExtra(ScanActivity.KODE);
                etKodeBarang.setText(kode);
                loadBarang(kode);
            }
        }
    }

    public void loadBarang(String kode) {
        Call<ResponseBarang> call = apiService.getBarangDetail(kode);
        call.enqueue(new Callback<ResponseBarang>() {
            @Override
            public void onResponse(Call<ResponseBarang> call, Response<ResponseBarang> response) {

                if (response.body().getData().size() != 0) {
                    Barang barang = response.body().getData().get(0);
                    etNamaBarang.setText(barang.getNamaBarang());
                    Glide.with(getApplicationContext()).load(Urls.BARANG_IMG_URL + barang.getGambarBarang()).error(R.drawable.ic_launcher_background).into(ivGambar);
                } else {
                    etKodeBarang.setText("");
                    Toast.makeText(getApplicationContext(), "Data tidak ada", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBarang> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
