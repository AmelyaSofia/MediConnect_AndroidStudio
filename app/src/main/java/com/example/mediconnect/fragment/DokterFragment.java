package com.example.mediconnect.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.DetailDokterActivity;
import com.example.mediconnect.R;
import com.example.mediconnect.adapter.DokterAdapter;
import com.example.mediconnect.model.DokterModel;

import java.util.ArrayList;

public class DokterFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<DokterModel> listDokter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dokter, container, false);

        recyclerView = view.findViewById(R.id.recyclerDokter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listDokter = new ArrayList<>();
        isiDataDokter();

        DokterAdapter adapter = new DokterAdapter(listDokter, dokter -> {
            Intent intent = new Intent(getContext(), DetailDokterActivity.class);
            intent.putExtra(DetailDokterActivity.EXTRA_NAMA, dokter.getNama());
            intent.putExtra(DetailDokterActivity.EXTRA_SPESIALIS, dokter.getSpesialis());
            intent.putExtra(DetailDokterActivity.EXTRA_FOTO, dokter.getFoto());
            intent.putExtra(DetailDokterActivity.EXTRA_DESKRIPSI, dokter.getDeskripsi());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        return view;
    }

    private void isiDataDokter() {
        listDokter.add(new DokterModel(
                "Dr. Amelya Sofia",
                "Spesialis Anak",
                R.drawable.doktersofia,
                "Dr. Amelya Sofia adalah dokter spesialis anak dengan pengalaman lebih dari 10 tahun dalam perawatan kesehatan balita dan anak-anak. Beliau memiliki keahlian dalam menangani penyakit infeksi, pertumbuhan dan perkembangan anak, imunisasi, serta masalah gizi. Dr. Amelya dikenal ramah, teliti, dan selalu mengutamakan kenyamanan pasien muda dalam setiap konsultasi."
        ));

        listDokter.add(new DokterModel(
                "Dr. Muhammad Habibin",
                "Spesialis Jantung",
                R.drawable.dokterhabib,
                "Dr. Muhammad Habibin, Sp.JP, merupakan dokter spesialis jantung dengan pengalaman klinis lebih dari 12 tahun. Beliau ahli dalam diagnosis dan penanganan penyakit jantung bawaan maupun degeneratif, termasuk hipertensi, gagal jantung, dan gangguan irama jantung. Dr. Habibin memiliki pendekatan menyeluruh untuk memastikan kesehatan jantung pasien secara optimal.\n"
        ));

        listDokter.add(new DokterModel(
                "Dr. Aisyah Aida",
                "Spesialis Gigi",
                R.drawable.dokterais,
                "Dr. Aisyah Aida, Sp.Pros, adalah dokter gigi yang berpengalaman dalam perawatan restoratif, ortodontik, dan estetika gigi. Dengan pengalaman lebih dari 8 tahun, beliau fokus pada kesehatan mulut, pencegahan penyakit gigi, serta pemeliharaan senyum estetik. Dr. Aisyah dikenal teliti dan memberikan edukasi lengkap mengenai perawatan gigi jangka panjang."
        ));

        listDokter.add(new DokterModel(
                "Dr. Lutfi Shidqi",
                "Spesialis Kulit",
                R.drawable.dokterlutfi,
                "Dr. Lutfi Shidqi, Sp.KK, adalah dokter spesialis kulit dan kelamin dengan pengalaman 10 tahun. Beliau menangani berbagai penyakit kulit, alergi, gangguan rambut dan kuku, serta masalah kosmetik dermatologi. Dr. Lutfi memiliki pendekatan klinis yang komprehensif, memastikan diagnosis akurat dan perawatan yang sesuai untuk setiap pasien."
        ));

        listDokter.add(new DokterModel(
                "Dr. Maria Yesinta",
                "Spesialis THT",
                R.drawable.doktermaria,
                "Dr. Maria Yesinta, Sp.THT-KL, memiliki pengalaman lebih dari 9 tahun dalam menangani gangguan telinga, hidung, dan tenggorokan. Beliau berkompeten dalam penanganan sinusitis, infeksi telinga, gangguan pendengaran, serta prosedur operasi THT. Dr. Maria dikenal komunikatif dan telaten dalam memberikan penjelasan mengenai kondisi pasien."
        ));

        listDokter.add(new DokterModel(
                "Dr. Iqtifarsya Dewita",
                "Spesialis Mata",
                R.drawable.dokterfarsya,
                "Dr. Iqtifarsya Dewita, Sp.M, adalah dokter spesialis mata dengan pengalaman lebih dari 11 tahun. Beliau memiliki keahlian dalam pemeriksaan mata rutin, diagnosis penyakit mata, serta prosedur bedah seperti katarak dan refraktif. Dr. Iqtifarsya selalu memastikan pasien memahami kondisi dan rencana perawatan dengan jelas dan profesional."
        ));

        listDokter.add(new DokterModel(
                "Dr. Vania Putri",
                "Spesialis Kandungan",
                R.drawable.doktervania,
                "Dr. Vania Putri, Sp.OG, adalah dokter spesialis obstetri dan ginekologi dengan pengalaman lebih dari 8 tahun. Beliau ahli dalam pemeriksaan kehamilan, persalinan, kesehatan reproduksi wanita, serta penanganan gangguan hormonal. Dr. Vania dikenal lembut, komunikatif, dan sangat mendukung kenyamanan pasien dalam setiap pemeriksaan."
        ));

        listDokter.add(new DokterModel(
                "Dr. Fizo Ramadhan",
                "Spesialis Saraf",
                R.drawable.dokterfizo,
                "Dr. Fizo Ramadhan, Sp.N, merupakan dokter spesialis saraf berpengalaman dalam menangani migrain, stroke, epilepsi, neuropati, dan gangguan sistem saraf pusat. Dengan lebih dari 10 tahun pengalaman, Dr. Fizo dikenal sangat teliti dan detail dalam observasi serta penanganan pasien."
        ));

        listDokter.add(new DokterModel(
                "Dr. Arman Pratama",
                "Spesialis Bedah",
                R.drawable.dokterarman,
                "Dr. Arman Pratama, Sp.B, adalah dokter spesialis bedah umum dengan pengalaman lebih dari 9 tahun dalam prosedur operasi abdomen, hernia, trauma, dan perawatan luka. Dr. Arman memiliki pendekatan humanis, mampu menjelaskan setiap prosedur dengan jelas sehingga pasien merasa tenang sebelum tindakan."
        ));

        listDokter.add(new DokterModel(
                "Dr. Uan Mahendra",
                "Spesialis Paru",
                R.drawable.dokteruan,
                "Dr. Uan Mahendra, Sp.P, adalah dokter spesialis paru yang berpengalaman dalam menangani asma, infeksi paru, PPOK, alergi saluran napas, hingga terapi pernapasan lanjutan. Dr. Uan dikenal sangat sabar dan detail dalam memberikan edukasi terkait kesehatan pernapasan kepada pasien."
        ));
    }
}
