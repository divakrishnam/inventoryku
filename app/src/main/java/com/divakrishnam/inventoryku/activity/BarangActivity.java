package com.divakrishnam.inventoryku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.divakrishnam.inventoryku.R;
import com.divakrishnam.inventoryku.adapter.BarangAdapter;
import com.divakrishnam.inventoryku.api.APIClient;
import com.divakrishnam.inventoryku.api.APIService;
import com.divakrishnam.inventoryku.model.Barang;
import com.divakrishnam.inventoryku.model.ResponseBarang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_barang);

        getSupportActionBar().setTitle("Barang");

        pbData = findViewById(R.id.pb_data);
        tvError = findViewById(R.id.tv_error);
        fabTambah = findViewById(R.id.fab_tambah);
        rvData = findViewById(R.id.rv_data);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BarangActivity.this, FormBarangActivity.class));
            }
        });

        layoutManager = new LinearLayoutManager(this);
        rvData.setLayoutManager(layoutManager);
        apiService = APIClient.getClient().create(APIService.class);
        loadData();
    }

    private void loadData() {
        showLoading(true);
        Call<ResponseBarang> call = apiService.getBarang();
        call.enqueue(new Callback<ResponseBarang>() {
            @Override
            public void onResponse(Call<ResponseBarang> call, Response<ResponseBarang> response) {
                List<Barang> list = response.body().getData();
                adapter = new BarangAdapter(list);
                rvData.setAdapter(adapter);
                showLoading(false);
            }

            @Override
            public void onFailure(Call<ResponseBarang> call, Throwable t) {
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
