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

    private ImageView imgDokter;
    private TextView tvNamaDokter, tvSpesialisDokter, tvDeskripsiDokter, tvJadwal;
    private Button btnAturJanjiTemu;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dokter);

        // Init view
        btnBack = findViewById(R.id.btnBack);
        btnAturJanjiTemu = findViewById(R.id.btnAturJanjiTemu);
        imgDokter = findViewById(R.id.imgDetailDokter);
        tvNamaDokter = findViewById(R.id.tvDetailNamaDokter);
        tvSpesialisDokter = findViewById(R.id.tvDetailSpesialis);
        tvDeskripsiDokter = findViewById(R.id.tvDetailDeskripsi);
        tvJadwal = findViewById(R.id.tvDetailJadwal); // pastikan ada di XML

        // Ambil data dari intent
        Intent intent = getIntent();
        String nama = intent.getStringExtra(EXTRA_NAMA);
        String spesialis = intent.getStringExtra(EXTRA_SPESIALIS);
        String deskripsi = intent.getStringExtra(EXTRA_DESKRIPSI);
        String fotoUrl = intent.getStringExtra(EXTRA_FOTO);
        String jadwal = intent.getStringExtra(EXTRA_JADWAL);

        // Set data ke view
        tvNamaDokter.setText(nama);
        tvSpesialisDokter.setText(spesialis);
        tvDeskripsiDokter.setText(deskripsi);
        tvJadwal.setText(jadwal);

        // Load foto dari API (URL)
        Glide.with(this)
                .load("http://10.0.2.2:8000/storage/" + fotoUrl)
                .placeholder(R.drawable.ic_doctor)
                .error(R.drawable.ic_doctor)
                .into(imgDokter);

        // Tombol back
        btnBack.setOnClickListener(v -> finish());

        // Tombol atur janji temu
        btnAturJanjiTemu.setOnClickListener(v -> {
            Intent i = new Intent(DetailDokterActivity.this, JanjiTemuActivity.class);
            i.putExtra("nama_dokter", nama);
            i.putExtra("spesialis", spesialis);
            startActivity(i);
        });
    }
}
