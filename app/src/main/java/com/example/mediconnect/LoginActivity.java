package com.example.mediconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediconnect.network.ApiClient;
import com.example.mediconnect.network.ApiService;
import com.example.mediconnect.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin, btnGoRegister;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("AUTH", MODE_PRIVATE);

        String token = prefs.getString("token", null);
        if (token != null) {
            String role = prefs.getString("role", "user");

            if ("admin".equals(role)) {
                startActivity(new Intent(LoginActivity.this, AdminActivity.class));
            } else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
            finish();
            return;
        }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Email & password wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(email, pass);
        });

        btnGoRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void loginUser(String email, String password) {
        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.login(email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    LoginResponse res = response.body();

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("token", res.data.token);
                    editor.putInt("user_id", res.data.user.id);
                    editor.putString("name", res.data.user.name);
                    editor.putString("email", res.data.user.email);
                    editor.putString("role", res.data.user.role);
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();

                    // Cek role dan pindah ke Activity sesuai role
                    switch (res.data.user.role) {
                        case "admin":
                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                            break;
                        case "user":
                        default:
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            break;
                    }

                    finish();

                } else {
                    Toast.makeText(LoginActivity.this,
                            "Email atau password salah",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,
                        "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
