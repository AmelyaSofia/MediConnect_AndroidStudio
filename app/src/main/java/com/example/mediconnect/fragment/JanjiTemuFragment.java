package com.example.mediconnect.fragment;

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
import com.example.mediconnect.network.ApiClient;
import com.example.mediconnect.network.ApiService;

import java.util.ArrayList;
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

                        List<AppointmentModel> allAppointments = response.body().getData();

                        if (allAppointments != null && !allAppointments.isEmpty()) {
                            rvJanjiTemu.setAdapter(
                                    new JanjiTemuAdapter(allAppointments)
                            );
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

    private void showEmpty() {
        rvJanjiTemu.setVisibility(View.GONE);
        tvBelumAdaJanji.setVisibility(View.VISIBLE);
    }
}
