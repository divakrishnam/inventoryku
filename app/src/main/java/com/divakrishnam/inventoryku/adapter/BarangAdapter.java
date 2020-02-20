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
import com.divakrishnam.inventoryku.activity.FormBarangActivity;
import com.divakrishnam.inventoryku.api.APIClient;
import com.divakrishnam.inventoryku.api.APIService;
import com.divakrishnam.inventoryku.model.Barang;
import com.divakrishnam.inventoryku.model.ResponseInfo;
import com.divakrishnam.inventoryku.util.Urls;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.MyViewHolder> {

    private List<Barang> mList;
    private APIService apiService;

    public BarangAdapter(List<Barang> list) {
        mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Barang barang = mList.get(position);
        holder.tvKode.setText(barang.getKodeBarang());
        holder.tvNama.setText(barang.getNamaBarang());
        holder.tvKuantitas.setText(barang.getKuantitasBarang());
        holder.tvCatatan.setText(barang.getCatatanBarang());
        Glide.with(holder.itemView.getContext()).load(Urls.BARANG_IMG_URL + barang.getGambarBarang()).error(R.drawable.ic_launcher_background).into(holder.ivGambar);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return menuDialog(view, position);
            }
        });
    }

    private boolean menuDialog(final View view, final int position) {
        CharSequence[] menu = {"Edit", "Delete"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext())
                .setItems(menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                editData(position, view);
                                break;
                            case 1:
                                deleteData(position, view);
                                break;
                        }
                    }
                });
        dialog.create();
        dialog.show();
        return true;
    }

    private void editData(int position, View view) {
        Intent intent = new Intent(view.getContext(), FormBarangActivity.class);
        intent.putExtra(FormBarangActivity.DATA, mList.get(position));
        view.getContext().startActivity(intent);
    }

    private void deleteData(final int position, final View view) {
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Menghapus data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        apiService = APIClient.getClient().create(APIService.class);

            Call<ResponseInfo> call = apiService.deleteBarang(mList.get(position).getKodeBarang());
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
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvKode, tvNama, tvKuantitas, tvCatatan;
        ImageView ivGambar;

        MyViewHolder(View itemView) {
            super(itemView);
            tvKode = itemView.findViewById(R.id.tv_kode);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvKuantitas = itemView.findViewById(R.id.tv_kuantitas);
            tvCatatan = itemView.findViewById(R.id.tv_catatan);
            ivGambar = itemView.findViewById(R.id.iv_gambar);
        }
    }
}
