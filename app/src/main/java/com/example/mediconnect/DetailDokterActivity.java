package com.example.mediconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;

public class DetailDokterActivity extends AppCompatActivity {

    public static final String EXTRA_NAMA = "extra_nama";
    public static final String EXTRA_SPESIALIS = "extra_spesialis";
    public static final String EXTRA_FOTO = "extra_foto";
    public static final String EXTRA_DESKRIPSI = "extra_deskripsi";

    private ImageView imgDokter;
    private TextView tvNamaDokter, tvSpesialisDokter, tvDeskripsiDokter;
    private Button btnBack, btnAturJanjiTemu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dokter);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnAturJanjiTemu = findViewById(R.id.btnAturJanjiTemu);
        imgDokter = findViewById(R.id.imgDetailDokter);
        tvNamaDokter = findViewById(R.id.tvDetailNamaDokter);
        tvSpesialisDokter = findViewById(R.id.tvDetailSpesialis);
        tvDeskripsiDokter = findViewById(R.id.tvDetailDeskripsi);

        String nama = getIntent().getStringExtra(EXTRA_NAMA);
        String spesialis = getIntent().getStringExtra(EXTRA_SPESIALIS);
        int fotoResId = getIntent().getIntExtra(EXTRA_FOTO, R.drawable.ic_launcher_foreground);
        String deskripsi = getIntent().getStringExtra(EXTRA_DESKRIPSI);

        tvNamaDokter.setText(nama);
        tvSpesialisDokter.setText(spesialis);
        imgDokter.setImageResource(fotoResId);
        tvDeskripsiDokter.setText(deskripsi);

        btnBack.setOnClickListener(v -> finish());

        btnAturJanjiTemu.setOnClickListener(v -> {
            Intent intent = new Intent(DetailDokterActivity.this, JanjiTemuActivity.class);
            intent.putExtra("nama_dokter", nama);
            intent.putExtra("spesialis", spesialis);
            intent.putExtra("foto", fotoResId);
            startActivity(intent);
        });
    }
}
