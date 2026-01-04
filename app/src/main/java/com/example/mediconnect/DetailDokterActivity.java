package com.example.mediconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailDokterActivity extends AppCompatActivity {

    public static final String EXTRA_NAMA = "nama";
    public static final String EXTRA_SPESIALIS = "spesialis";
    public static final String EXTRA_DESKRIPSI = "deskripsi";
    public static final String EXTRA_FOTO = "foto";
    public static final String EXTRA_JADWAL = "jadwal";
    public static final String EXTRA_DOCTOR_ID = "doctor_id";

    private ImageView imgDokter;
    private TextView tvNamaDokter, tvSpesialisDokter, tvDeskripsiDokter, tvJadwal;
    private Button btnAturJanjiTemu;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dokter);

        btnBack = findViewById(R.id.btnBack);
        btnAturJanjiTemu = findViewById(R.id.btnAturJanjiTemu);
        imgDokter = findViewById(R.id.imgDetailDokter);
        tvNamaDokter = findViewById(R.id.tvDetailNamaDokter);
        tvSpesialisDokter = findViewById(R.id.tvDetailSpesialis);
        tvDeskripsiDokter = findViewById(R.id.tvDetailDeskripsi);
        tvJadwal = findViewById(R.id.tvDetailJadwal);

        Intent intent = getIntent();
        String nama = intent.getStringExtra(EXTRA_NAMA);
        String spesialis = intent.getStringExtra(EXTRA_SPESIALIS);
        String deskripsi = intent.getStringExtra(EXTRA_DESKRIPSI);
        String fotoUrl = intent.getStringExtra(EXTRA_FOTO);
        String jadwal = intent.getStringExtra(EXTRA_JADWAL);
        int doctorId = intent.getIntExtra(EXTRA_DOCTOR_ID, -1);

        // Set data ke UI
        tvNamaDokter.setText(nama != null ? nama : "-");
        tvSpesialisDokter.setText(spesialis != null ? spesialis : "-");
        tvDeskripsiDokter.setText(deskripsi != null ? deskripsi : "-");
        tvJadwal.setText(jadwal != null ? jadwal : "-");

        Glide.with(this)
                .load(fotoUrl != null ? "http://10.0.2.2:8000/" + fotoUrl : R.drawable.ic_doctor)
                .placeholder(R.drawable.ic_doctor)
                .error(R.drawable.ic_doctor)
                .into(imgDokter);

        btnBack.setOnClickListener(v -> finish());

        btnAturJanjiTemu.setOnClickListener(v -> {
            if (doctorId == -1) return;
            Intent i = new Intent(DetailDokterActivity.this, JanjiTemuActivity.class);
            i.putExtra(DetailDokterActivity.EXTRA_DOCTOR_ID, doctorId);
            i.putExtra(DetailDokterActivity.EXTRA_NAMA, nama);
            i.putExtra(DetailDokterActivity.EXTRA_SPESIALIS, spesialis);
            i.putExtra(DetailDokterActivity.EXTRA_FOTO, fotoUrl);
            startActivity(i);
        });
//        btnAturJanjiTemu.setOnClickListener(v -> {
//            Toast.makeText(this, "TOMBOL DIKLIK", Toast.LENGTH_SHORT).show();
//
//            Intent i = new Intent(DetailDokterActivity.this, JanjiTemuActivity.class);
//            i.putExtra(EXTRA_DOCTOR_ID, 1); // HARD CODE DULU
//            i.putExtra(EXTRA_NAMA, "Tes Dokter");
//            i.putExtra(EXTRA_SPESIALIS, "Tes Spesialis");
//            startActivity(i);
//        });

    }
}
