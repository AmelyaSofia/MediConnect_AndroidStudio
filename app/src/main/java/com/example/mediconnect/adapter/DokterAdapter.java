package com.example.mediconnect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.R;
import com.example.mediconnect.model.DokterModel;

import java.util.List;

public class DokterAdapter extends RecyclerView.Adapter<DokterAdapter.ViewHolder> {

    public interface OnDokterClickListener {
        void onDokterClick(DokterModel dokter);
    }

    private List<DokterModel> list;
    private OnDokterClickListener listener;

    public DokterAdapter(List<DokterModel> list, OnDokterClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dokter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DokterModel dokter = list.get(position);

        holder.nama.setText(dokter.getNama());
        holder.spesialis.setText(dokter.getSpesialis());
        holder.foto.setImageResource(dokter.getFoto());

        holder.itemView.setOnClickListener(v -> listener.onDokterClick(dokter));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, spesialis;
        ImageView foto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.txtNamaDokter);
            spesialis = itemView.findViewById(R.id.txtSpesialisDokter);
            foto = itemView.findViewById(R.id.imgDokter);
        }
    }
}
