package com.example.mediconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediconnect.model.LoginResponse;
import com.example.mediconnect.network.ApiClient;
import com.example.mediconnect.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmail, etName, etPassword;
    Button btnRegister, btnBackLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnBackLogin = findViewById(R.id.btnBackLogin);

        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (email.isEmpty() || name.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            registerUser(name, email, pass);
        });

        btnBackLogin.setOnClickListener(v -> finish());
    }

    private void registerUser(String name, String email, String password) {
        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.register(name, email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Toast.makeText(RegisterActivity.this,
                            "Register berhasil! Silakan login.",
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();

                } else {
                    Toast.makeText(RegisterActivity.this,
                            "Gagal register — email mungkin sudah terdaftar",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,
                        "Gagal terhubung ke server",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
