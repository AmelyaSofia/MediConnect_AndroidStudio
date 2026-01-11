package com.example.mediconnect;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mediconnect.fragment.ProfileFragment;
import com.example.mediconnect.fragmentadmin.AdminAppointmentFragment;
import com.example.mediconnect.fragmentadmin.AdminDoctorsFragment;
import com.example.mediconnect.fragmentadmin.AdminUsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bottomNav = findViewById(R.id.bottomNavAdmin);

        loadFragment(new AdminUsersFragment());

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;

            int id = item.getItemId();

            if (id == R.id.menu_admin_users) {
                fragment = new AdminUsersFragment();
            } else if (id == R.id.menu_admin_doctors) {
                fragment = new AdminDoctorsFragment();
            } else if (id == R.id.menu_admin_appointments) {
                fragment = new AdminAppointmentFragment();
            } else if (id == R.id.menu_admin_profile) {
                fragment = new ProfileFragment();
            }

            return loadFragment(fragment);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment == null) return false;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.adminFragmentContainer, fragment)
                .commit();

        return true;
    }
}
