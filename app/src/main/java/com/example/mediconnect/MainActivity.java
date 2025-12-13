package com.example.mediconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.mediconnect.fragment.HomeFragment;
import com.example.mediconnect.fragment.DokterFragment;
import com.example.mediconnect.fragment.JanjiTemuFragment;
import com.example.mediconnect.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (id == R.id.nav_dokter) {
                    selectedFragment = new DokterFragment();
                } else if (id == R.id.nav_janji) {
                    selectedFragment = new JanjiTemuFragment();
                } else if (id == R.id.nav_profile) {
                    selectedFragment = ProfileFragment.newInstance(false); // user biasa = false
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }
                return true;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }
}
