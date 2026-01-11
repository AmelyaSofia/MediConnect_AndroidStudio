package com.example.mediconnect.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.R;
import com.example.mediconnect.model.AppointmentModel;

import java.util.List;

public class AdminAppointmentAdapter
        extends RecyclerView.Adapter<AdminAppointmentAdapter.ViewHolder> {

    public interface OnAdminAction {
        void onApprove(int id);
        void onReject(int id);
    }

    private final List<AppointmentModel> list;
    private final OnAdminAction listener;

    public AdminAppointmentAdapter(
            List<AppointmentModel> list,
            OnAdminAction listener
    ) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_appointment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder h,
            int position
    ) {
        AppointmentModel a = list.get(position);

        h.tvUser.setText(
                a.getUser() != null ? a.getUser().getName() : "-"
        );

        h.tvDoctor.setText(
                a.getDoctor() != null ? a.getDoctor().getName() : "-"
        );

        h.tvDate.setText("Tanggal: " + a.getAppointmentDate());
        h.tvTime.setText("Jam: " + a.getAppointmentTime());

        String status = a.getStatus() != null ? a.getStatus().toUpperCase() : "-";
        h.tvStatus.setText(status);

        switch (status) {
            case "PENDING":
                h.tvStatus.setTextColor(Color.parseColor("#F9A825"));
                break;
            case "APPROVED":
                h.tvStatus.setTextColor(Color.parseColor("#2E7D32"));
                break;
            case "REJECTED":
                h.tvStatus.setTextColor(Color.parseColor("#C62828"));
                break;
            default:
                h.tvStatus.setTextColor(Color.DKGRAY);
        }

        if ("PENDING".equals(status)) {
            h.btnApprove.setVisibility(View.VISIBLE);
            h.btnReject.setVisibility(View.VISIBLE);

            h.btnApprove.setOnClickListener(v ->
                    listener.onApprove(a.getId())
            );

            h.btnReject.setOnClickListener(v ->
                    listener.onReject(a.getId())
            );
        } else {
            h.btnApprove.setVisibility(View.GONE);
            h.btnReject.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUser, tvDoctor, tvDate, tvTime, tvStatus;
        ImageView btnApprove, btnReject;

        ViewHolder(@NonNull View v) {
            super(v);
            tvUser = v.findViewById(R.id.tvUser);
            tvDoctor = v.findViewById(R.id.tvDoctor);
            tvDate = v.findViewById(R.id.tvDate);
            tvTime = v.findViewById(R.id.tvTime);
            tvStatus = v.findViewById(R.id.tvStatus);
            btnApprove = v.findViewById(R.id.btnApprove);
            btnReject = v.findViewById(R.id.btnReject);
        }
    }
}
