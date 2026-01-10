package com.example.mediconnect.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.R;
import com.example.mediconnect.model.AppointmentModel;
import com.example.mediconnect.model.DokterModel;

import java.util.List;

public class JanjiTemuAdapter
        extends RecyclerView.Adapter<JanjiTemuAdapter.ViewHolder> {

    private final List<AppointmentModel> list;
    private OnAppointmentActionListener listener;

    public interface OnAppointmentActionListener {
        void onCancel(int appointmentId);
        void onDelete(int appointmentId);
    }

    public JanjiTemuAdapter(List<AppointmentModel> list) {
        this.list = list;
    }

    public void setOnAppointmentActionListener(
            OnAppointmentActionListener listener
    ) {
        this.listener = listener;
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

        holder.tvNamaDokter.setText(
                dokter != null
                        ? "Dokter: " + dokter.getName()
                        : "Dokter: -"
        );

        holder.tvPoli.setText(
                dokter != null
                        ? dokter.getSpecialization()
                        : "-"
        );

        holder.tvJadwal.setText("Tanggal: " + janji.getAppointmentDate());
        holder.tvJam.setText("Jam: " + janji.getAppointmentTime());

        String status = janji.getStatus() != null
                ? janji.getStatus().toUpperCase()
                : "-";

        holder.tvStatus.setText("Status: " + status);

        switch (status) {
            case "APPROVED":
                holder.tvStatus.setTextColor(Color.parseColor("#2E7D32"));
                break;
            case "PENDING":
                holder.tvStatus.setTextColor(Color.parseColor("#F9A825"));
                break;
            case "REJECTED":
                holder.tvStatus.setTextColor(Color.parseColor("#C62828"));
                break;
            case "CANCELLED":
                holder.tvStatus.setTextColor(Color.GRAY);
                break;
            default:
                holder.tvStatus.setTextColor(Color.DKGRAY);
        }

        if (janji.getNote() != null && !janji.getNote().isEmpty()) {
            holder.tvNote.setVisibility(View.VISIBLE);
            holder.tvNote.setText("Catatan: " + janji.getNote());
        } else {
            holder.tvNote.setVisibility(View.GONE);
        }

        holder.btnCancel.setVisibility(View.GONE);
        holder.btnDelete.setVisibility(View.GONE);

        if (listener != null) {

            if (status.equals("PENDING")) {
                holder.btnCancel.setVisibility(View.VISIBLE);
                holder.btnCancel.setOnClickListener(v ->
                        listener.onCancel(janji.getId()));
            }

            if (status.equals("CANCELLED")) {
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnDelete.setOnClickListener(v ->
                        listener.onDelete(janji.getId()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPoli, tvNamaDokter, tvStatus, tvJadwal, tvJam, tvNote;
        Button btnCancel, btnDelete;
        CardView cardJanji;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPoli = itemView.findViewById(R.id.tvPoliTitle);
            tvNamaDokter = itemView.findViewById(R.id.tvNamaDokter);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvJadwal = itemView.findViewById(R.id.tvJadwal);
            tvJam = itemView.findViewById(R.id.tvJam);
            tvNote = itemView.findViewById(R.id.tvNote);

            btnCancel = itemView.findViewById(R.id.btnCancel);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            cardJanji = itemView.findViewById(R.id.cardJanjiTemu);
        }
    }
}
