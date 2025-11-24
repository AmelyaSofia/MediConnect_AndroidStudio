package com.example.mediconnect.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.example.mediconnect.R;

public class JanjiTemuFragment extends Fragment {

    private TextView tvBelumAdaJanji, tvPoliTitle, tvNamaPasien, tvNamaDokter, tvNoAntrian, tvJadwal, tvJam;
    private CardView cardJanjiTemu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_janji, container, false);

        tvBelumAdaJanji = view.findViewById(R.id.tvBelumAdaJanji);
        tvPoliTitle = view.findViewById(R.id.tvPoliTitle);
        tvNamaPasien = view.findViewById(R.id.tvNamaPasien);
        tvNamaDokter = view.findViewById(R.id.tvNamaDokter);
        tvNoAntrian = view.findViewById(R.id.tvNoAntrian);
        tvJadwal = view.findViewById(R.id.tvJadwal);
        tvJam = view.findViewById(R.id.tvJam);
        cardJanjiTemu = view.findViewById(R.id.cardJanjiTemu);

        SharedPreferences userPrefs = requireActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        String namaPasien = userPrefs.getString("name", "Pasien");
        String userEmail = userPrefs.getString("email", "default_user");

        SharedPreferences janjiPrefs = requireActivity().getSharedPreferences("janji_data_" + userEmail, Context.MODE_PRIVATE);
        String namaDokter = janjiPrefs.getString("nama_dokter", null);
        String tanggal = janjiPrefs.getString("tanggal", null);
        String waktu = janjiPrefs.getString("waktu", null);
        String metode = janjiPrefs.getString("metode", null);
        String poli = janjiPrefs.getString("poli", "Poli Umum");

        if (namaDokter != null) {
            tvBelumAdaJanji.setVisibility(View.GONE);
            cardJanjiTemu.setVisibility(View.VISIBLE);

            tvPoliTitle.setText(poli);
            tvNamaPasien.setText("Nama: " + namaPasien);
            tvNamaDokter.setText("Dokter: " + namaDokter);
            tvNoAntrian.setText("Nomor Antrian: 12A");
            tvJadwal.setText("Jadwal: " + tanggal);
            tvJam.setText("Jam: " + waktu + " (" + metode + ")");
        } else {
            cardJanjiTemu.setVisibility(View.GONE);
            tvBelumAdaJanji.setVisibility(View.VISIBLE);
        }

        cardJanjiTemu.setOnClickListener(v -> {
        });

        return view;
    }
}
