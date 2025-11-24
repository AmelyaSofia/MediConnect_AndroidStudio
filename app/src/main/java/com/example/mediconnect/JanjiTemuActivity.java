package com.example.mediconnect;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class JanjiTemuActivity extends AppCompatActivity {

    Button btnPilihTanggal, btnPilihWaktu, btnKonfirmasiJanji;
    TextView tvTanggalDipilih, tvWaktuDipilih, tvNamaDokterJanji, tvSpesialisJanji;
    ImageView imgDokterJanji;
    RadioGroup rgMetodePembayaran;

    String tanggalJanji, waktuJanji, metodePembayaran;
    String namaDokter, spesialis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_janji_temu);

        btnPilihTanggal = findViewById(R.id.btnPilihTanggal);
        btnPilihWaktu = findViewById(R.id.btnPilihWaktu);
        btnKonfirmasiJanji = findViewById(R.id.btnKonfirmasiJanji);
        tvTanggalDipilih = findViewById(R.id.tvTanggalDipilih);
        tvWaktuDipilih = findViewById(R.id.tvWaktuDipilih);
        tvNamaDokterJanji = findViewById(R.id.tvNamaDokterJanji);
        tvSpesialisJanji = findViewById(R.id.tvSpesialisJanji);
        imgDokterJanji = findViewById(R.id.imgDokterJanji);
        rgMetodePembayaran = findViewById(R.id.rgMetodePembayaran);

        namaDokter = getIntent().getStringExtra("nama_dokter");
        spesialis = getIntent().getStringExtra("spesialis");
        int fotoResId = getIntent().getIntExtra("foto", R.drawable.ic_launcher_foreground);

        tvNamaDokterJanji.setText(namaDokter);
        tvSpesialisJanji.setText(spesialis);
        imgDokterJanji.setImageResource(fotoResId);

        btnPilihTanggal.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    JanjiTemuActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        tanggalJanji = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        tvTanggalDipilih.setText(tanggalJanji);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        btnPilihWaktu.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    JanjiTemuActivity.this,
                    (view, selectedHour, selectedMinute) -> {
                        waktuJanji = String.format("%02d:%02d", selectedHour, selectedMinute);
                        tvWaktuDipilih.setText(waktuJanji);
                    },
                    hour, minute, true
            );
            timePickerDialog.show();
        });

        btnKonfirmasiJanji.setOnClickListener(v -> {
            int selectedId = rgMetodePembayaran.getCheckedRadioButtonId();

            if (tanggalJanji == null || waktuJanji == null || selectedId == -1) {
                Toast.makeText(this, "Harap lengkapi semua data janji terlebih dahulu!", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadio = findViewById(selectedId);
            metodePembayaran = selectedRadio.getText().toString();
            SharedPreferences userPrefs = getSharedPreferences("USER_DATA", MODE_PRIVATE);
            String userEmail = userPrefs.getString("email", "default_user");
            SharedPreferences prefs = getSharedPreferences("janji_data_" + userEmail, MODE_PRIVATE);
            prefs.edit()
                    .putString("nama_dokter", namaDokter)
                    .putString("spesialis", spesialis)
                    .putString("tanggal", tanggalJanji)
                    .putString("waktu", waktuJanji)
                    .putString("metode", metodePembayaran)
                    .apply();

            Toast.makeText(this, "Janji berhasil dikonfirmasi!", Toast.LENGTH_SHORT).show();

            finish();
        });
    }
}
