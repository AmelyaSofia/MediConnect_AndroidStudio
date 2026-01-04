package com.example.mediconnect;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mediconnect.model.AppointmentResponse;
import com.example.mediconnect.network.ApiClient;
import com.example.mediconnect.network.ApiService;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JanjiTemuActivity extends AppCompatActivity {

    private Button btnPilihTanggal, btnPilihWaktu, btnKonfirmasiJanji;
    private TextView tvTanggalDipilih, tvWaktuDipilih;
    private TextView tvNamaDokterJanji, tvSpesialisJanji;
    private ImageView imgDokterJanji;
    private EditText etNote;

    private String tanggalJanji;
    private String waktuJanji;
    private String token;
    private int doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_janji_temu);

        initViews();
        loadIntentData();
        setupListeners();
    }

    private void initViews() {
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnPilihTanggal = findViewById(R.id.btnPilihTanggal);
        btnPilihWaktu = findViewById(R.id.btnPilihWaktu);
        btnKonfirmasiJanji = findViewById(R.id.btnKonfirmasiJanji);

        tvTanggalDipilih = findViewById(R.id.tvTanggalDipilih);
        tvWaktuDipilih = findViewById(R.id.tvWaktuDipilih);
        tvNamaDokterJanji = findViewById(R.id.tvNamaDokterJanji);
        tvSpesialisJanji = findViewById(R.id.tvSpesialisJanji);

        imgDokterJanji = findViewById(R.id.imgDokterJanji);
        etNote = findViewById(R.id.etNote);

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadIntentData() {
        doctorId = getIntent().getIntExtra(DetailDokterActivity.EXTRA_DOCTOR_ID, -1);
        String namaDokter = getIntent().getStringExtra(DetailDokterActivity.EXTRA_NAMA);
        String spesialis = getIntent().getStringExtra(DetailDokterActivity.EXTRA_SPESIALIS);
        String foto = getIntent().getStringExtra(DetailDokterActivity.EXTRA_FOTO);

        tvNamaDokterJanji.setText(namaDokter != null ? namaDokter : "-");
        tvSpesialisJanji.setText(spesialis != null ? spesialis : "-");

        Glide.with(this)
                .load(foto != null ? "http://10.0.2.2:8000/" + foto : R.drawable.ic_doctor)
                .placeholder(R.drawable.ic_doctor)
                .error(R.drawable.ic_doctor)
                .into(imgDokterJanji);

        SharedPreferences prefs = getSharedPreferences("AUTH", MODE_PRIVATE);
        token = prefs.getString("token", null);

        if (doctorId == -1 || token == null) {
            Toast.makeText(this, "Data dokter atau token tidak valid", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupListeners() {

        // Pilih tanggal
        btnPilihTanggal.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder
                    .datePicker()
                    .setTitleText("Pilih Tanggal Janji")
                    .build();

            datePicker.show(getSupportFragmentManager(), "DATE_PICKER");

            datePicker.addOnPositiveButtonClickListener(selection -> {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(selection);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                tanggalJanji = year + "-" +
                        String.format("%02d", month) + "-" +
                        String.format("%02d", day);

                tvTanggalDipilih.setText(day + "/" + month + "/" + year);
            });
        });

        // Pilih waktu
        btnPilihWaktu.setOnClickListener(v -> {
            MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Pilih Jam Janji")
                    .build();

            timePicker.show(getSupportFragmentManager(), "TIME_PICKER");

            timePicker.addOnPositiveButtonClickListener(view -> {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                waktuJanji = String.format("%02d:%02d", hour, minute);
                tvWaktuDipilih.setText(waktuJanji);
            });
        });

        // Konfirmasi janji
        btnKonfirmasiJanji.setOnClickListener(v -> {
            if (tanggalJanji == null || waktuJanji == null) {
                Toast.makeText(this, "Harap pilih tanggal dan waktu", Toast.LENGTH_SHORT).show();
                return;
            }
            createAppointment();
        });
    }

    private void createAppointment() {
        String note = etNote.getText().toString().trim();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.createAppointment(
                "Bearer " + token,
                doctorId,
                tanggalJanji,
                waktuJanji,
                note
        ).enqueue(new Callback<AppointmentResponse>() {
            @Override
            public void onResponse(Call<AppointmentResponse> call, Response<AppointmentResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(JanjiTemuActivity.this,
                            "Janji temu berhasil dibuat",
                            Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    try {
                        String error = response.errorBody() != null
                                ? response.errorBody().string()
                                : "Terjadi kesalahan";
                        Toast.makeText(JanjiTemuActivity.this,
                                error,
                                Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppointmentResponse> call, Throwable t) {
                Toast.makeText(JanjiTemuActivity.this,
                        "Koneksi bermasalah: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
