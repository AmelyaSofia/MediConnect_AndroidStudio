package com.example.mediconnect.fragmentadmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.R;
import com.example.mediconnect.adapter.AdminDokterAdapter;
import com.example.mediconnect.model.DokterModel;
import com.example.mediconnect.model.DokterResponse;
import com.example.mediconnect.network.ApiClient;
import com.example.mediconnect.network.ApiService;
import com.example.mediconnect.utils.FileUtils;
import com.example.mediconnect.utils.SimpleAdminCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDoctorsFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private AdminDokterAdapter adapter;
    private ArrayList<DokterModel> listDokter = new ArrayList<>();

    private ApiService apiService;
    private String token;
    private ImageView imgFotoDokter;

    private Uri selectedImageUri;
    private DokterModel selectedDokter;

    private static final int PICK_IMAGE = 101;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_admin_dokter, container, false);

        recyclerView = view.findViewById(R.id.recyclerDokterAdmin);
        fabAdd = view.findViewById(R.id.fabAddDokter);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new AdminDokterAdapter(listDokter, new AdminDokterAdapter.OnActionListener() {
            @Override
            public void onEdit(DokterModel dokter) {
                showForm(dokter);
            }

            @Override
            public void onDelete(DokterModel dokter) {
                confirmDelete(dokter);
            }
        });

        recyclerView.setAdapter(adapter);

        SharedPreferences prefs =
                requireContext().getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        token = prefs.getString("token", "");

        apiService = ApiClient.getClient().create(ApiService.class);

        fabAdd.setOnClickListener(v -> showForm(null));

        loadDokter();

        return view;
    }

    private void loadDokter() {
        apiService.getAllDoctors("Bearer " + token)
                .enqueue(new Callback<DokterResponse>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<DokterResponse> call,
                            @NonNull Response<DokterResponse> response
                    ) {
                        if (response.isSuccessful() && response.body() != null) {
                            listDokter.clear();
                            listDokter.addAll(response.body().getData());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<DokterResponse> call,
                            @NonNull Throwable t
                    ) {
                        Toast.makeText(requireContext(),
                                "Gagal memuat dokter",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showForm(@Nullable DokterModel dokter) {
        selectedDokter = dokter;
        selectedImageUri = null;

        View v = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_form_dokter, null);

        EditText etNama = v.findViewById(R.id.etNama);
        EditText etSpesialis = v.findViewById(R.id.etSpesialis);
        EditText etPhone = v.findViewById(R.id.etPhone);
        EditText etJadwal = v.findViewById(R.id.etJadwal);
        EditText etDeskripsi = v.findViewById(R.id.etDeskripsi);
        imgFotoDokter = v.findViewById(R.id.imgFotoDokter);


        if (dokter != null) {
            etNama.setText(dokter.getName());
            etSpesialis.setText(dokter.getSpecialization());
            etPhone.setText(dokter.getPhone());
            etJadwal.setText(dokter.getSchedule());
            etDeskripsi.setText(dokter.getDescription());
        }

        imgFotoDokter.setOnClickListener(v1 -> {
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            );
            startActivityForResult(i, PICK_IMAGE);
        });


        new AlertDialog.Builder(requireContext())
                .setTitle(dokter == null ? "Tambah Dokter" : "Edit Dokter")
                .setView(v)
                .setPositiveButton("Simpan", (d, w) -> {
                    if (dokter == null) {
                        createDokter(etNama, etSpesialis, etPhone, etJadwal, etDeskripsi);
                    } else {
                        updateDokter(etNama, etSpesialis, etPhone, etJadwal, etDeskripsi);
                    }
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    private void createDokter(
            EditText n,
            EditText s,
            EditText p,
            EditText j,
            EditText d
    ) {
        apiService.createDoctor(
                "Bearer " + token,
                rb(n), rb(s), rb(p), rb(j), rb(d),
                buildPhoto()
        ).enqueue(new SimpleAdminCallback<>(this::loadDokter));
    }

    private void updateDokter(
            EditText n,
            EditText s,
            EditText p,
            EditText j,
            EditText d
    ) {
        apiService.updateDoctor(
                "Bearer " + token,
                selectedDokter.getId(),
                rb(n), rb(s), rb(p), rb(j), rb(d),
                buildPhoto()
        ).enqueue(new SimpleAdminCallback<>(this::loadDokter));
    }
    private MultipartBody.Part buildPhoto() {
        if (selectedImageUri == null) return null;

        File file = FileUtils.getFileFromUri(requireContext(), selectedImageUri);
        if (file == null) return null;

        RequestBody requestBody =
                RequestBody.create(file, MediaType.parse("image/*"));

        return MultipartBody.Part.createFormData(
                "photo",
                file.getName(),
                requestBody
        );
    }

    private RequestBody rb(EditText e) {
        return RequestBody.create(
                e.getText().toString(),
                MediaType.parse("text/plain")
        );
    }

    private void confirmDelete(DokterModel dokter) {
        new AlertDialog.Builder(requireContext())
                .setMessage("Hapus dokter ini?")
                .setPositiveButton("Hapus", (d, w) -> {
                    apiService.deleteDoctor(
                            "Bearer " + token,
                            dokter.getId()
                    ).enqueue(new SimpleAdminCallback<>(this::loadDokter));
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    @Override
    public void onActivityResult(
            int requestCode,
            int resultCode,
            @Nullable Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE
                && resultCode == Activity.RESULT_OK
                && data != null) {

            selectedImageUri = data.getData();

            if (imgFotoDokter != null) {
                imgFotoDokter.setImageURI(selectedImageUri);
            }
        }
    }
}
