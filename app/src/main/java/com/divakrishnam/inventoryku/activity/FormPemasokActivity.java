package com.divakrishnam.inventoryku.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.divakrishnam.inventoryku.R;
import com.divakrishnam.inventoryku.api.APIClient;
import com.divakrishnam.inventoryku.api.APIService;
import com.divakrishnam.inventoryku.model.Pemasok;
import com.divakrishnam.inventoryku.model.ResponseInfo;
import com.divakrishnam.inventoryku.util.Urls;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormPemasokActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DATA = "data";
    private Pemasok pemasok;
    private EditText etNama, etKontak, etAlamat;
    private ImageView ivGambar;
    private Button btnSimpan;
    private APIService apiService;

    private static final int REQUEST_IMAGE = 200;
    private static final int REQUEST_PHOTO = 300;
    private Uri resultUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pemasok);

        etNama = findViewById(R.id.et_nama);
        etKontak = findViewById(R.id.et_kontak);
        etAlamat = findViewById(R.id.et_alamat);
        ivGambar = findViewById(R.id.iv_gambar);
        btnSimpan = findViewById(R.id.btn_simpan);


        apiService = APIClient.getClient().create(APIService.class);

        pemasok = getIntent().getParcelableExtra(DATA);
        if(pemasok != null){
            etNama.setText(pemasok.getNamaPemasok());
            etKontak.setText(pemasok.getKontakPemasok());
            etAlamat.setText(pemasok.getAlamatPemasok());
            Glide.with(getApplicationContext()).load(Urls.PEMASOK_IMG_URL + pemasok.getGambarPemasok()).error(R.drawable.ic_launcher_background).into(ivGambar);
        }

        btnSimpan.setOnClickListener(this);
        ivGambar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSimpan){
            simpanData(resultUri);
        }else if (view == ivGambar){
            menuDialog();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                CropImage.activity(Uri.fromFile(imageFile))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setFixAspectRatio(true)
                        .start(FormPemasokActivity.this);
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Toast.makeText(FormPemasokActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
            }
        });

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                Glide.with(getApplicationContext()).load(resultUri).error(R.drawable.ic_launcher_background).into(ivGambar);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void simpanData(Uri resultUri){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (resultUri != null){
            File image = new File(resultUri.getPath());
            File file = null;
            try {
                file = new Compressor(getApplicationContext()).compressToFile(image);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String nama = etNama.getText().toString();
            String kontak = etKontak.getText().toString();
            String alamat = etAlamat.getText().toString();
            String gambar = file.getName();

            if (!nama.isEmpty() && !kontak.isEmpty() && !alamat.isEmpty() && !gambar.isEmpty()){
                RequestBody mNama = RequestBody.create(MediaType.parse("text/plain"), nama);
                RequestBody mKontak = RequestBody.create(MediaType.parse("text/plain"), kontak);
                RequestBody mAlamat = RequestBody.create(MediaType.parse("text/plain"), alamat);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part mGambar = MultipartBody.Part.createFormData("gambar", file.getName(), requestFile);

                if (pemasok != null){
                    RequestBody mKode = RequestBody.create(MediaType.parse("text/plain"), pemasok.getKodePemasok());
                    Call<ResponseInfo> call = apiService.putGambarPemasok(mKode, mNama, mKontak, mAlamat, mGambar);
                    responseData(call, progressDialog);
                }else{
                    Call<ResponseInfo> call = apiService.postGambarPemasok(mNama, mKontak, mAlamat, mGambar);
                    responseData(call, progressDialog);
                }
            }else{
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something empty", Toast.LENGTH_LONG).show();
            }
        }

        else{
            String nama = etNama.getText().toString();
            String kontak = etKontak.getText().toString();
            String alamat = etAlamat.getText().toString();

            if (!nama.isEmpty() && !kontak.isEmpty() && !alamat.isEmpty()){
                if (pemasok != null){
                    Call<ResponseInfo> call = apiService.putPemasok(pemasok.getKodePemasok(), nama, kontak, alamat);
                    responseData(call, progressDialog);
                }else{
                    Call<ResponseInfo> call = apiService.postPemasok(nama, kontak, alamat);
                    responseData(call, progressDialog);
                }
            }else{
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something empty", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void responseData(Call<ResponseInfo> call, ProgressDialog progressDialog){
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
    }

    private void menuDialog(){
        CharSequence[] menu = {"Camera", "Select Photo"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setItems(menu, (dialogInterface, i) -> {
                    switch (i){
                        case 0:
                            EasyImage.openCamera(this, REQUEST_IMAGE);
                            break;
                        case 1:
                            EasyImage.openChooserWithGallery(this, "Choose Picture",REQUEST_PHOTO);
                            break;
                    }
                });
        dialog.create();
        dialog.show();
    }
}
