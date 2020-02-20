package com.divakrishnam.inventoryku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.divakrishnam.inventoryku.R;
import com.divakrishnam.inventoryku.api.APIClient;
import com.divakrishnam.inventoryku.api.APIService;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    public static final String KODE = "kode";
    private ZXingScannerView mScannerView;
    private FrameLayout flCamera;

    private APIService apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        getSupportActionBar().setTitle("Scan Barcode");

        flCamera = findViewById(R.id.fl_camera);

        apiInterface = APIClient.getClient().create(APIService.class);

        initScannerView();
    }

    private void initScannerView(){
        mScannerView = new ZXingScannerView(this);
        mScannerView.setAutoFocus(true);
        mScannerView.setResultHandler(this);
        flCamera.addView(mScannerView);
    }

    @Override
    protected void onStart() {
        doRequestPermission();
        mScannerView.startCamera();
        super.onStart();
    }

    @Override
    protected void onPause() {
        mScannerView.stopCamera();
        super.onPause();
    }

    private void doRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
            }
        }
    }

    @Override
    public void handleResult(Result result) {
//        mahasiswaRegister(result.getText());
        mScannerView.resumeCameraPreview(this);
        Intent intent = new Intent();
        intent.putExtra(KODE, result.getText());
        setResult(RESULT_OK, intent);
        finish();
    }
}
