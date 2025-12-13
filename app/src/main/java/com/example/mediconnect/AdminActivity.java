package com.example.mediconnect;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediconnect.adapter.UserAdapter;
import com.example.mediconnect.fragment.ProfileFragment;
import com.example.mediconnect.model.UserModel;
import com.example.mediconnect.model.UsersResponse;
import com.example.mediconnect.network.ApiClient;
import com.example.mediconnect.network.ApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {

    RecyclerView rvUsers;
    Button btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnProfile = findViewById(R.id.btnProfile);
        rvUsers = findViewById(R.id.rvUsers);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));

        // Load semua user
        loadUsers();

        // Tombol untuk menampilkan ProfileFragment
        btnProfile.setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentProfileContainer, new ProfileFragment())
                    .addToBackStack(null) // agar bisa kembali
                    .commit();
        });
    }

    private void loadUsers() {
        SharedPreferences prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        if (token == null) {
            Toast.makeText(this, "User tidak terautentikasi", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService api = ApiClient.getClient().create(ApiService.class);
        Call<UsersResponse> call = api.getAllUsers("Bearer " + token);
        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<UserModel> users = response.body().data;
                    UserAdapter adapter = new UserAdapter(users);
                    rvUsers.setAdapter(adapter);
                } else {
                    Toast.makeText(AdminActivity.this, "Gagal memuat data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                Toast.makeText(AdminActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}