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
import com.divakrishnam.inventoryku.adapter.KonsumenAdapter;
import com.divakrishnam.inventoryku.api.APIClient;
import com.divakrishnam.inventoryku.api.APIService;
import com.divakrishnam.inventoryku.model.Konsumen;
import com.divakrishnam.inventoryku.model.ResponseKonsumen;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonsumenActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_konsumen);

        getSupportActionBar().setTitle("Konsumen");

        pbData = findViewById(R.id.pb_data);
        tvError = findViewById(R.id.tv_error);
        fabTambah = findViewById(R.id.fab_tambah);
        rvData = findViewById(R.id.rv_data);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KonsumenActivity.this, FormKonsumenActivity.class));
            }
        });

        layoutManager = new LinearLayoutManager(this);
        rvData.setLayoutManager(layoutManager);
        apiService = APIClient.getClient().create(APIService.class);
        loadData();
    }

    private void loadData() {
        showLoading(true);
        Call<ResponseKonsumen> call = apiService.getKonsumen();
        call.enqueue(new Callback<ResponseKonsumen>() {
            @Override
            public void onResponse(Call<ResponseKonsumen> call, Response<ResponseKonsumen> response) {
                List<Konsumen> list = response.body().getData();
                adapter = new KonsumenAdapter(list);
                rvData.setAdapter(adapter);
                showLoading(false);
            }

            @Override
            public void onFailure(Call<ResponseKonsumen> call, Throwable t) {
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
