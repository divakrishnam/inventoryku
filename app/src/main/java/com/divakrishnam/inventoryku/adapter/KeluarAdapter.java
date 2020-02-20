package com.divakrishnam.inventoryku.adapter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.divakrishnam.inventoryku.model.Keluar;
import com.divakrishnam.inventoryku.model.ResponseInfo;
import com.divakrishnam.inventoryku.util.Urls;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeluarAdapter extends RecyclerView.Adapter<KeluarAdapter.MyViewHolder>{
    List<Keluar> mList;
    private APIService apiService;

    public KeluarAdapter(List <Keluar> list) {
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
        Keluar keluar = mList.get(position);
        holder.tvKode.setText(keluar.getKodeBarangKeluar());
        holder.tvKodeBarang.setText(keluar.getKodeBarang());
        holder.tvNamaBarang.setText(keluar.getNamaBarang());
        holder.tvNamaKonsumen.setText(keluar.getNamaKonsumen());
        holder.tvAlamatKonsumen.setText(keluar.getAlamatKonsumen());
        holder.tvKuantitas.setText(keluar.getKuantitasBarangKeluar());
        holder.tvTanggalWaktu.setText(keluar.getTanggalWaktuBarangKeluar());
        holder.tvCatatan.setText(keluar.getCatatanBarangKeluar());
        Glide.with(holder.itemView.getContext()).load(Urls.BARANG_IMG_URL + keluar.getGambarBarang()).error(R.drawable.ic_launcher_background).into(holder.ivGambar);
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

        Call<ResponseInfo> call = apiService.deleteKeluar(mList.get(position).getKodeBarangKeluar());
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
        TextView tvKode, tvKodeBarang, tvNamaBarang, tvNamaKonsumen, tvAlamatKonsumen, tvKuantitas, tvTanggalWaktu, tvCatatan;
        ImageView ivGambar;

        MyViewHolder(View itemView) {
            super(itemView);
            tvKode = itemView.findViewById(R.id.tv_kode);
            tvKodeBarang = itemView.findViewById(R.id.tv_kode_barang);
            tvNamaBarang = itemView.findViewById(R.id.tv_nama_barang);
            tvNamaKonsumen = itemView.findViewById(R.id.tv_nama_instansi);
            tvAlamatKonsumen = itemView.findViewById(R.id.tv_alamat_instansi);
            tvKuantitas = itemView.findViewById(R.id.tv_kuantitas);
            tvTanggalWaktu = itemView.findViewById(R.id.tv_tanggal_waktu);
            tvCatatan = itemView.findViewById(R.id.tv_catatan);
            ivGambar = itemView.findViewById(R.id.iv_gambar);
        }
    }
}
