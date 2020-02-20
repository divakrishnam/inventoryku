package com.divakrishnam.inventoryku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.divakrishnam.inventoryku.R;
import com.divakrishnam.inventoryku.adapter.BarangAdapter;
import com.divakrishnam.inventoryku.adapter.KeluarAdapter;
import com.divakrishnam.inventoryku.api.APIClient;
import com.divakrishnam.inventoryku.api.APIService;
import com.divakrishnam.inventoryku.model.Barang;
import com.divakrishnam.inventoryku.model.Keluar;
import com.divakrishnam.inventoryku.model.ResponseBarang;
import com.divakrishnam.inventoryku.model.ResponseKeluar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeluarActivity extends AppCompatActivity {

    private ProgressBar pbData;
    private TextView tvError;
    private FloatingActionButton fabTambah;
    private RecyclerView rvData;

    private APIService apiService;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keluar);

        getSupportActionBar().setTitle("Barang Keluar");

        pbData = findViewById(R.id.pb_data);
        tvError = findViewById(R.id.tv_error);
        fabTambah = findViewById(R.id.fab_tambah);
        rvData = findViewById(R.id.rv_data);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KeluarActivity.this, FormKeluarActivity.class));
            }
        });

        layoutManager = new LinearLayoutManager(this);
        rvData.setLayoutManager(layoutManager);
        apiService = APIClient.getClient().create(APIService.class);
        loadData();
    }

    private void loadData() {
        showLoading(true);
        Call<ResponseKeluar> call = apiService.getKeluar();
        call.enqueue(new Callback<ResponseKeluar>() {
            @Override
            public void onResponse(Call<ResponseKeluar> call, Response<ResponseKeluar> response) {
                List<Keluar> list = response.body().getData();
                adapter = new KeluarAdapter(list);
                rvData.setAdapter(adapter);
                showLoading(false);
            }

            @Override
            public void onFailure(Call<ResponseKeluar> call, Throwable t) {
                showLoading(false);
                showMessage(true, t.getMessage());
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            pbData.setVisibility(View.VISIBLE);
        } else {
            pbData.setVisibility(View.GONE);
        }
    }

    private void showMessage(Boolean state, String message) {
        if (state) {
            tvError.setText(message);
            tvError.setVisibility(View.VISIBLE);
        } else {
            tvError.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
