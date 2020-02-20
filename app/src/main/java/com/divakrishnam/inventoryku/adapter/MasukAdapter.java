package com.divakrishnam.inventoryku.adapter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.divakrishnam.inventoryku.R;
import com.divakrishnam.inventoryku.api.APIClient;
import com.divakrishnam.inventoryku.api.APIService;
import com.divakrishnam.inventoryku.model.Masuk;
import com.divakrishnam.inventoryku.model.ResponseInfo;
import com.divakrishnam.inventoryku.util.Urls;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasukAdapter extends RecyclerView.Adapter<MasukAdapter.MyViewHolder>{
    List<Masuk> mList;
    private APIService apiService;

    public MasukAdapter(List <Masuk> list) {
        mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proses, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position){
        Masuk masuk = mList.get(position);
        holder.tvKode.setText(masuk.getKodeBarangMasuk());
        holder.tvKodeBarang.setText(masuk.getKodeBarang());
        holder.tvNamaBarang.setText(masuk.getNamaBarang());
        holder.tvNamaPemasok.setText(masuk.getNamaPemasok());
        holder.tvAlamatPemasok.setText(masuk.getAlamatPemasok());
        holder.tvKuantitas.setText(masuk.getKuantitasBarangMasuk());
        holder.tvTanggalWaktu.setText(masuk.getTanggalWaktuBarangMasuk());
        holder.tvCatatan.setText(masuk.getCatatanBarangMasuk());
        Glide.with(holder.itemView.getContext()).load(Urls.BARANG_IMG_URL + masuk.getGambarBarang()).error(R.drawable.ic_launcher_background).into(holder.ivGambar);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return menuDialog(view, position);
            }
        });
    }
    private boolean menuDialog(final View view, final int position) {
        CharSequence[] menu = {"Delete"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext())
                .setItems(menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                deleteData(position, view);
                                break;
                        }
                    }
                });
        dialog.create();
        dialog.show();
        return true;
    }

    private void deleteData(final int position, final View view) {
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Menghapus data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        apiService = APIClient.getClient().create(APIService.class);

        Call<ResponseInfo> call = apiService.deleteMasuk(mList.get(position).getKodeBarangMasuk());
        call.enqueue(new Callback<ResponseInfo>() {
            @Override
            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                progressDialog.dismiss();
                if (response.body().getStatus() == 200){
                    mList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mList.size());
                }
                Toast.makeText(view.getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public int getItemCount () {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvKode, tvKodeBarang, tvNamaBarang, tvNamaPemasok, tvAlamatPemasok, tvKuantitas, tvTanggalWaktu, tvCatatan;
        ImageView ivGambar;

        MyViewHolder(View itemView) {
            super(itemView);
            tvKode = itemView.findViewById(R.id.tv_kode);
            tvKodeBarang = itemView.findViewById(R.id.tv_kode_barang);
            tvNamaBarang = itemView.findViewById(R.id.tv_nama_barang);
            tvNamaPemasok = itemView.findViewById(R.id.tv_nama_instansi);
            tvAlamatPemasok = itemView.findViewById(R.id.tv_alamat_instansi);
            tvKuantitas = itemView.findViewById(R.id.tv_kuantitas);
            tvTanggalWaktu = itemView.findViewById(R.id.tv_tanggal_waktu);
            tvCatatan = itemView.findViewById(R.id.tv_catatan);
            ivGambar = itemView.findViewById(R.id.iv_gambar);
        }
    }
}
