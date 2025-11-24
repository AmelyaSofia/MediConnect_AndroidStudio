package com.example.mediconnect.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mediconnect.DetailDokterActivity;
import com.example.mediconnect.R;

public class DokterFragment extends Fragment {

    private LinearLayout cardDokter1, cardDokter2, cardDokter3, cardDokter4, cardDokter5, cardDokter6;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dokter, container, false);

        cardDokter1 = view.findViewById(R.id.cardDokter1);
        cardDokter2 = view.findViewById(R.id.cardDokter2);
        cardDokter3 = view.findViewById(R.id.cardDokter3);
        cardDokter4 = view.findViewById(R.id.cardDokter4);
        cardDokter5 = view.findViewById(R.id.cardDokter5);
        cardDokter6 = view.findViewById(R.id.cardDokter6);

        cardDokter1.setOnClickListener(v -> bukaDetail(
                "Dr. Amelya Sofia",
                "Spesialis Anak",
                R.drawable.doktersofia,
                "Dr. Amelya Sofia adalah dokter spesialis anak dengan pengalaman lebih dari 10 tahun dalam perawatan kesehatan balita dan anak-anak. Beliau memiliki keahlian dalam menangani penyakit infeksi, pertumbuhan dan perkembangan anak, imunisasi, serta masalah gizi. Dr. Amelya dikenal ramah, teliti, dan selalu mengutamakan kenyamanan pasien muda dalam setiap konsultasi."));

        cardDokter2.setOnClickListener(v -> bukaDetail(
                "Dr. Muhammad Habibin",
                "Spesialis Jantung",
                R.drawable.dokterhabib,
                "Dr. Muhammad Habibin, Sp.JP, merupakan dokter spesialis jantung dengan pengalaman klinis lebih dari 12 tahun. Beliau ahli dalam diagnosis dan penanganan penyakit jantung bawaan maupun degeneratif, termasuk hipertensi, gagal jantung, dan gangguan irama jantung. Dr. Habibin memiliki pendekatan menyeluruh untuk memastikan kesehatan jantung pasien secara optimal."));

        cardDokter3.setOnClickListener(v -> bukaDetail(
                "Dr. Aisyah Aida",
                "Spesialis Gigi",
                R.drawable.dokterais,
                "Dr. Aisyah Aida, Sp.Pros, adalah dokter gigi yang berpengalaman dalam perawatan restoratif, ortodontik, dan estetika gigi. Dengan pengalaman lebih dari 8 tahun, beliau fokus pada kesehatan mulut, pencegahan penyakit gigi, serta pemeliharaan senyum estetik. Dr. Aisyah dikenal teliti dan memberikan edukasi lengkap mengenai perawatan gigi jangka panjang."));

        cardDokter4.setOnClickListener(v -> bukaDetail(
                "Dr. Lutfi Shidqi",
                "Spesialis Kulit",
                R.drawable.dokterlutfi,
                "Dr. Lutfi Shidqi, Sp.KK, adalah dokter spesialis kulit dan kelamin dengan pengalaman 10 tahun. Beliau menangani berbagai penyakit kulit, alergi, gangguan rambut dan kuku, serta masalah kosmetik dermatologi. Dr. Lutfi memiliki pendekatan klinis yang komprehensif, memastikan diagnosis akurat dan perawatan yang sesuai untuk setiap pasien."));

        cardDokter5.setOnClickListener(v -> bukaDetail(
                "Dr. Maria Yesinta",
                "Spesialis THT",
                R.drawable.doktermaria,
                "Dr. Maria Yesinta, Sp.THT-KL, memiliki pengalaman lebih dari 9 tahun dalam menangani gangguan telinga, hidung, dan tenggorokan. Beliau berkompeten dalam penanganan sinusitis, infeksi telinga, gangguan pendengaran, serta prosedur operasi THT. Dr. Maria dikenal komunikatif dan telaten dalam memberikan penjelasan mengenai kondisi pasien."));

        cardDokter6.setOnClickListener(v -> bukaDetail(
                "Dr. Iqtifarsya Dewita",
                "Spesialis Mata",
                R.drawable.dokterfarsya,
                "Dr. Iqtifarsya Dewita, Sp.M, adalah dokter spesialis mata dengan pengalaman lebih dari 11 tahun. Beliau memiliki keahlian dalam pemeriksaan mata rutin, diagnosis penyakit mata, serta prosedur bedah seperti katarak dan refraktif. Dr. Iqtifarsya selalu memastikan pasien memahami kondisi dan rencana perawatan dengan jelas dan profesional."));
        return view;
    }

    private void bukaDetail(String nama, String spesialis, int fotoResId, String deskripsi) {
        Intent intent = new Intent(getContext(), DetailDokterActivity.class);
        intent.putExtra(DetailDokterActivity.EXTRA_NAMA, nama);
        intent.putExtra(DetailDokterActivity.EXTRA_SPESIALIS, spesialis);
        intent.putExtra(DetailDokterActivity.EXTRA_FOTO, fotoResId);
        intent.putExtra(DetailDokterActivity.EXTRA_DESKRIPSI, deskripsi);
        startActivity(intent);
    }
}
