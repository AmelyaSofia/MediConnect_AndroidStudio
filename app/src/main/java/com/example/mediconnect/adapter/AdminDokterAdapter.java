package com.example.mediconnect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mediconnect.R;
import com.example.mediconnect.model.DokterModel;

import java.util.List;

public class AdminDokterAdapter
        extends RecyclerView.Adapter<AdminDokterAdapter.ViewHolder> {

    public interface OnActionListener {
        void onEdit(DokterModel dokter);
        void onDelete(DokterModel dokter);
    }

    private List<DokterModel> list;
    private OnActionListener listener;

    public AdminDokterAdapter(List<DokterModel> list, OnActionListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_dokter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {
        DokterModel dokter = list.get(position);

        holder.txtNama.setText(dokter.getName());
        holder.txtSpesialis.setText(dokter.getSpecialization());
        holder.txtJadwal.setText(dokter.getSchedule());

        Glide.with(holder.itemView.getContext())
                .load("http://10.0.2.2:8000/" + dokter.getPhoto())
                .placeholder(R.drawable.ic_doctor)
                .error(R.drawable.ic_doctor)
                .into(holder.imgDokter);

        holder.btnEdit.setOnClickListener(v ->
                listener.onEdit(dokter)
        );

        holder.btnDelete.setOnClickListener(v ->
                listener.onDelete(dokter)
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDokter;
        TextView txtNama, txtSpesialis, txtJadwal;
        ImageButton btnEdit, btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgDokter = itemView.findViewById(R.id.imgDokter);
            txtNama = itemView.findViewById(R.id.txtNamaDokter);
            txtSpesialis = itemView.findViewById(R.id.txtSpesialisDokter);
            txtJadwal = itemView.findViewById(R.id.txtJadwalDokter);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
