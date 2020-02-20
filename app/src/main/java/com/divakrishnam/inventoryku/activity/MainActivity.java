package com.divakrishnam.inventoryku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.divakrishnam.inventoryku.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnBarang, btnPemasok, btnKonsumen, btnMasuk, btnKeluar, btnLogOut;

    private static final int PERMISSION_ALL = 100;

    private String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBarang = findViewById(R.id.btnBarang);
        btnPemasok = findViewById(R.id.btnPemasok);
        btnKonsumen = findViewById(R.id.btnKonsumen);
        btnMasuk = findViewById(R.id.btnMasuk);
        btnKeluar = findViewById(R.id.btnKeluar);
        btnLogOut = findViewById(R.id.btnLogOut);

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        btnBarang.setOnClickListener(this);
        btnPemasok.setOnClickListener(this);
        btnKonsumen.setOnClickListener(this);
        btnMasuk.setOnClickListener(this);
        btnKeluar.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ALL && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thanks for granting Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == btnBarang) {
            startActivity(new Intent(MainActivity.this, BarangActivity.class));
        } else if (view == btnPemasok) {
            startActivity(new Intent(MainActivity.this, PemasokActivity.class));
        } else if (view == btnKonsumen) {
            startActivity(new Intent(MainActivity.this, KonsumenActivity.class));
        } else if (view == btnMasuk) {
            startActivity(new Intent(MainActivity.this, MasukActivity.class));
        } else if (view == btnKeluar) {
            startActivity(new Intent(MainActivity.this, KeluarActivity.class));
        } else if (view == btnLogOut) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
