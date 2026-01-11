package com.example.mediconnect.fragmentadmin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.R;
import com.example.mediconnect.adapter.AdminAppointmentAdapter;
import com.example.mediconnect.model.AdminAppointmentResponse;
import com.example.mediconnect.model.SimpleResponse;
import com.example.mediconnect.model.UpdateStatusRequest;
import com.example.mediconnect.network.ApiClient;
import com.example.mediconnect.network.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAppointmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdminAppointmentAdapter adapter;

    private ApiService apiService;
    private String token;

    private final ArrayList listAppointment = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(
                R.layout.fragment_admin_appointment,
                container,
                false
        );

        recyclerView = view.findViewById(R.id.recyclerAdminAppointment);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        SharedPreferences prefs =
                requireContext().getSharedPreferences(
                        "AUTH",
                        Context.MODE_PRIVATE
                );

        token = prefs.getString("token", "");

        apiService = ApiClient.getClient().create(ApiService.class);

        adapter = new AdminAppointmentAdapter(
                listAppointment,
                new AdminAppointmentAdapter.OnAdminAction() {
                    @Override
                    public void onApprove(int id) {
                        updateStatus(id, "approved");
                    }

                    @Override
                    public void onReject(int id) {
                        updateStatus(id, "rejected");
                    }
                }
        );

        recyclerView.setAdapter(adapter);

        loadAppointments();

        return view;
    }

    private void loadAppointments() {
        apiService.getAllAppointmentsAdmin(
                "Bearer " + token
        ).enqueue(new Callback<AdminAppointmentResponse>() {
            @Override
            public void onResponse(
                    @NonNull Call<AdminAppointmentResponse> call,
                    @NonNull Response<AdminAppointmentResponse> response
            ) {
                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().isSuccess()) {

                    listAppointment.clear();
                    listAppointment.addAll(
                            response.body().getData()
                    );
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<AdminAppointmentResponse> call,
                    @NonNull Throwable t
            ) {
                Toast.makeText(
                        requireContext(),
                        "Gagal memuat janji temu",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void updateStatus(int id, String status) {

        UpdateStatusRequest body =
                new UpdateStatusRequest(status);

        apiService.updateAppointmentStatus(
                "Bearer " + token,
                id,
                body
        ).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(
                    @NonNull Call<SimpleResponse> call,
                    @NonNull Response<SimpleResponse> response
            ) {
                if (response.isSuccessful()) {
                    loadAppointments();
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<SimpleResponse> call,
                    @NonNull Throwable t
            ) {
                t.printStackTrace();
            }
        });
    }
}
