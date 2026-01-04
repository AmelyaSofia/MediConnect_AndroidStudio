package com.example.mediconnect.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.DetailDokterActivity;
import com.example.mediconnect.R;
import com.example.mediconnect.adapter.DokterAdapter;
import com.example.mediconnect.model.DokterModel;
import com.example.mediconnect.model.DokterResponse;
import com.example.mediconnect.network.ApiClient;
import com.example.mediconnect.network.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DokterFragment extends Fragment {

    private RecyclerView recyclerView;
    private DokterAdapter adapter;
    private final ArrayList<DokterModel> listDokter = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_dokter, container, false);

        recyclerView = view.findViewById(R.id.recyclerDokter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new DokterAdapter(listDokter, dokter -> {
            Intent intent = new Intent(requireActivity(), DetailDokterActivity.class);
            intent.putExtra(DetailDokterActivity.EXTRA_DOCTOR_ID, dokter.getId());
            intent.putExtra("nama", dokter.getName());
            intent.putExtra("spesialis", dokter.getSpecialization());
            intent.putExtra("deskripsi", dokter.getDescription());
            intent.putExtra("foto", dokter.getPhoto());
            intent.putExtra("jadwal", dokter.getSchedule());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        loadDokter();

        return view;
    }

    private void loadDokter() {

        SharedPreferences prefs =
                requireContext().getSharedPreferences("AUTH", Context.MODE_PRIVATE);

        String token = prefs.getString("token", null);

        if (token == null || token.isEmpty()) {
            Toast.makeText(requireActivity(),
                    "Silakan login ulang",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<DokterResponse> call =
                apiService.getAllDoctors("Bearer " + token);

        call.enqueue(new Callback<DokterResponse>() {
            @Override
            public void onResponse(
                    @NonNull Call<DokterResponse> call,
                    @NonNull Response<DokterResponse> response
            ) {
                if (!isAdded()) return;

                if (response.isSuccessful() && response.body() != null) {
                    listDokter.clear();
                    listDokter.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(requireActivity(),
                            "Gagal mengambil data dokter",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<DokterResponse> call,
                    @NonNull Throwable t
            ) {
                if (!isAdded()) return;

                Log.e("DOKTER_ERROR", t.getMessage());
                Toast.makeText(requireActivity(),
                        "Koneksi bermasalah",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
