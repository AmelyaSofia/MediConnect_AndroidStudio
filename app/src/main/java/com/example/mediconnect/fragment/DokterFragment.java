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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mediconnect.DetailDokterActivity;
import com.example.mediconnect.R;
import com.example.mediconnect.adapter.DokterAdapter;
import com.example.mediconnect.model.DokterModel;
import com.example.mediconnect.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DokterFragment extends Fragment {

    private RecyclerView recyclerView;
    private DokterAdapter adapter;
    private final ArrayList<DokterModel> listDokter = new ArrayList<>();

    private static final String URL_DOKTER =
            "http://10.0.2.2:8000/api/doctors";

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

        Log.d("TOKEN", token);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL_DOKTER,
                null,
                response -> {
                    if (!isAdded()) return;

                    try {
                        JSONArray data = response.getJSONArray("data");
                        listDokter.clear();

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject d = data.getJSONObject(i);

                            listDokter.add(new DokterModel(
                                    d.getInt("id"),
                                    d.getString("name"),
                                    d.getString("specialization"),
                                    d.optString("phone"),
                                    d.getString("schedule"),
                                    d.optString("description"),
                                    d.optString("photo")
                            ));
                        }

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Toast.makeText(requireActivity(),
                                "Gagal parsing data dokter",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    if (!isAdded()) return;

                    Toast.makeText(requireActivity(),
                            "Gagal mengambil data dokter",
                            Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        VolleySingleton.getInstance(requireContext())
                .addToRequestQueue(request);
    }
}