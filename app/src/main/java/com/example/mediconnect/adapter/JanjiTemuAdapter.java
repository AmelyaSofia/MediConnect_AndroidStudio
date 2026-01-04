package com.example.mediconnect.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.R;
import com.example.mediconnect.model.AppointmentModel;
import com.example.mediconnect.model.DokterModel;

import java.util.List;

public class JanjiTemuAdapter extends RecyclerView.Adapter<JanjiTemuAdapter.ViewHolder> {

    private final List<AppointmentModel> list;

    public JanjiTemuAdapter(List<AppointmentModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_janji, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {
        AppointmentModel janji = list.get(position);
        DokterModel dokter = janji.getDoctor();

        // Nama dokter
        holder.tvNamaDokter.setText(
                dokter != null
                        ? "Dokter: " + dokter.getName()
                        : "Dokter: -"
        );

        // Poli / Spesialis
        holder.tvPoli.setText(
                dokter != null
                        ? dokter.getSpecialization()
                        : "-"
        );

        // Jadwal
        holder.tvJadwal.setText("Tanggal: " + janji.getAppointmentDate());
        holder.tvJam.setText("Jam: " + janji.getAppointmentTime());

        // Status
        String status = janji.getStatus() != null
                ? janji.getStatus().toUpperCase()
                : "-";

        holder.tvStatus.setText("Status: " + status);

        // Warna status
        switch (status) {
            case "APPROVED":
                holder.tvStatus.setTextColor(Color.parseColor("#2E7D32")); // hijau
                break;
            case "PENDING":
                holder.tvStatus.setTextColor(Color.parseColor("#F9A825")); // kuning
                break;
            case "REJECTED":
                holder.tvStatus.setTextColor(Color.parseColor("#C62828")); // merah
                break;
            default:
                holder.tvStatus.setTextColor(Color.DKGRAY);
        }

        // Note (opsional)
        if (janji.getNote() != null && !janji.getNote().isEmpty()) {
            holder.tvNote.setVisibility(View.VISIBLE);
            holder.tvNote.setText("Catatan: " + janji.getNote());
        } else {
            holder.tvNote.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPoli, tvNamaDokter, tvStatus, tvJadwal, tvJam, tvNote;
        CardView cardJanji;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPoli = itemView.findViewById(R.id.tvPoliTitle);
            tvNamaDokter = itemView.findViewById(R.id.tvNamaDokter);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvJadwal = itemView.findViewById(R.id.tvJadwal);
            tvJam = itemView.findViewById(R.id.tvJam);
            tvNote = itemView.findViewById(R.id.tvNote); // WAJIB ADA DI XML
            cardJanji = itemView.findViewById(R.id.cardJanjiTemu);
        }
    }
}
