package com.example.mediconnect.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mediconnect.R;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private TextView tvWelcome, tvSubWelcome;
    private ImageView imgLogo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvWelcome = view.findViewById(R.id.tvWelcome);
        tvSubWelcome = view.findViewById(R.id.tvSubWelcome);
        imgLogo = view.findViewById(R.id.imgLogo);

        SharedPreferences pref = requireContext().getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        String namaUser = pref.getString("name", "User");
        tvWelcome.setText("Halo, " + namaUser + " 👋");

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        String greet;
        if (hour >= 5 && hour < 11) {
            greet = "Selamat pagi 🌤️";
        } else if (hour >= 11 && hour < 15) {
            greet = "Selamat siang ☀️";
        } else if (hour >= 15 && hour < 19) {
            greet = "Selamat sore 🌇";
        } else {
            greet = "Selamat malam 🌙";
        }

        tvSubWelcome.setText(greet + " di MediConnect");

        LinearLayout btnLokasi = view.findViewById(R.id.btnLokasi);

        if (btnLokasi != null) {
            btnLokasi.setOnClickListener(v -> {
                String uri = "geo:0,0?q=RSJ+Menur,+Jl.+Raya+Menur+No.120,+Surabaya";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            });
        }

        LinearLayout btnDarurat = view.findViewById(R.id.btnDarurat);
        if (btnDarurat != null) {
            btnDarurat.setOnClickListener(v -> {
                String nomorRS = "0881027529811";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + nomorRS));
                startActivity(intent);
            });
        }
        return view;
    }
}
