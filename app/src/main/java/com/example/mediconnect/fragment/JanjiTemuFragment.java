package com.example.mediconnect.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.R;
import com.example.mediconnect.adapter.JanjiTemuAdapter;
import com.example.mediconnect.model.AppointmentModel;
import com.example.mediconnect.model.MyAppointmentResponse;
import com.example.mediconnect.model.SimpleResponse;
import com.example.mediconnect.network.ApiClient;
import com.example.mediconnect.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JanjiTemuFragment extends Fragment {

    private RecyclerView rvJanjiTemu;
    private TextView tvBelumAdaJanji;

    public JanjiTemuFragment() {}

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_janji, container, false);

        rvJanjiTemu = view.findViewById(R.id.rvJanjiTemu);
        tvBelumAdaJanji = view.findViewById(R.id.tvBelumAdaJanji);

        rvJanjiTemu.setLayoutManager(new LinearLayoutManager(getContext()));

        loadAppointments();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAppointments();
    }

    private void loadAppointments() {
        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("AUTH", Context.MODE_PRIVATE);

        String token = prefs.getString("token", null);
        if (token == null) {
            showEmpty();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.getMyAppointments("Bearer " + token)
                .enqueue(new Callback<MyAppointmentResponse>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<MyAppointmentResponse> call,
                            @NonNull Response<MyAppointmentResponse> response
                    ) {
                        if (!response.isSuccessful()
                                || response.body() == null
                                || !response.body().isSuccess()) {
                            showEmpty();
                            return;
                        }

                        List<AppointmentModel> allAppointments =
                                response.body().getData();

                        if (allAppointments != null && !allAppointments.isEmpty()) {

                            JanjiTemuAdapter adapter =
                                    new JanjiTemuAdapter(allAppointments);

                            adapter.setOnAppointmentActionListener(
                                    new JanjiTemuAdapter.OnAppointmentActionListener() {

                                        @Override
                                        public void onCancel(int appointmentId) {
                                            showCancelDialog(appointmentId);
                                        }

                                        @Override
                                        public void onDelete(int appointmentId) {
                                            showDeleteDialog(appointmentId);
                                        }
                                    }
                            );

                            rvJanjiTemu.setAdapter(adapter);
                            rvJanjiTemu.setVisibility(View.VISIBLE);
                            tvBelumAdaJanji.setVisibility(View.GONE);

                        } else {
                            showEmpty();
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<MyAppointmentResponse> call,
                            @NonNull Throwable t
                    ) {
                        Log.e("JanjiTemuFragment", t.getMessage());
                        showEmpty();
                    }
                });
    }

    private void showCancelDialog(int appointmentId) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Batalkan Janji Temu")
                .setMessage("Apakah kamu yakin ingin membatalkan janji temu ini?")
                .setPositiveButton("Ya, Batalkan",
                        (dialog, which) -> cancelAppointment(appointmentId))
                .setNegativeButton("Tidak", null)
                .show();
    }

    private void showDeleteDialog(int appointmentId) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Hapus Janji Temu")
                .setMessage("Apakah kamu yakin ingin menghapus janji temu ini?")
                .setPositiveButton("Ya, Hapus",
                        (dialog, which) -> deleteAppointment(appointmentId))
                .setNegativeButton("Tidak", null)
                .show();
    }

    private void cancelAppointment(int appointmentId) {
        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("AUTH", Context.MODE_PRIVATE);

        String token = prefs.getString("token", null);
        if (token == null) return;

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.cancelAppointment("Bearer " + token, appointmentId)
                .enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<SimpleResponse> call,
                            @NonNull Response<SimpleResponse> response
                    ) {
                        loadAppointments();
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<SimpleResponse> call,
                            @NonNull Throwable t
                    ) {
                        Log.e("CancelAppointment", t.getMessage());
                    }
                });
    }

    private void deleteAppointment(int appointmentId) {
        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("AUTH", Context.MODE_PRIVATE);

        String token = prefs.getString("token", null);
        if (token == null) return;

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.deleteAppointment("Bearer " + token, appointmentId)
                .enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<SimpleResponse> call,
                            @NonNull Response<SimpleResponse> response
                    ) {
                        loadAppointments();
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<SimpleResponse> call,
                            @NonNull Throwable t
                    ) {
                        Log.e("DeleteAppointment", t.getMessage());
                    }
                });
    }

    private void showEmpty() {
        rvJanjiTemu.setVisibility(View.GONE);
        tvBelumAdaJanji.setVisibility(View.VISIBLE);
    }
}
