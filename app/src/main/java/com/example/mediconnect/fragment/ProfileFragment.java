    package com.example.mediconnect.fragment;

    import android.app.AlertDialog;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.fragment.app.Fragment;

    import com.example.mediconnect.R;
    import com.example.mediconnect.network.ApiClient;
    import com.example.mediconnect.network.ApiService;
    import com.example.mediconnect.model.UpdateUserResponse;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class ProfileFragment extends Fragment {

        private TextView tvName, tvEmail;
        private Button btnLogout, btnUpdate, btnDelete, btnBack;

        private boolean isAdmin = false;

        public ProfileFragment() {
            // Required empty public constructor
        }

        public static ProfileFragment newInstance(boolean isAdmin) {
            ProfileFragment fragment = new ProfileFragment();
            Bundle args = new Bundle();
            args.putBoolean("isAdmin", isAdmin);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                isAdmin = getArguments().getBoolean("isAdmin", false);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_profile, container, false);

            tvName = view.findViewById(R.id.tvName);
            tvEmail = view.findViewById(R.id.tvEmail);
            btnLogout = view.findViewById(R.id.btnLogout);
            btnUpdate = view.findViewById(R.id.btnUpdate);
            btnDelete = view.findViewById(R.id.btnDelete);
            btnBack = view.findViewById(R.id.btnBack);

            if (isAdmin) {
                btnBack.setVisibility(View.VISIBLE);
                btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
            } else {
                btnBack.setVisibility(View.GONE);
            }

            SharedPreferences prefs = requireActivity().getSharedPreferences("AUTH", Context.MODE_PRIVATE);
            String name = prefs.getString("name", "Belum ada Akun");
            String email = prefs.getString("email", "Belum ada Akun");

            tvName.setText(name);
            tvEmail.setText(email);

            btnUpdate.setOnClickListener(v -> showUpdateDialog(prefs));
            btnLogout.setOnClickListener(v -> logoutUser(prefs));
            btnDelete.setOnClickListener(v -> deleteUser(prefs));


            // Tombol logout
            btnLogout.setOnClickListener(v -> {
                new AlertDialog.Builder(getContext())
                        .setTitle("Logout")
                        .setMessage("Apakah kamu yakin ingin logout?")
                        .setPositiveButton("Ya", (dialog, which) -> {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.clear();
                            editor.apply();

                            startActivity(new android.content.Intent(getActivity(), com.example.mediconnect.LoginActivity.class)
                                    .addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP | android.content.Intent.FLAG_ACTIVITY_NEW_TASK));
                            requireActivity().finish();
                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            });

            btnDelete.setOnClickListener(v -> {
                new AlertDialog.Builder(getContext())
                        .setTitle("Hapus Akun")
                        .setMessage("Apakah kamu yakin ingin menghapus akun ini? Data akan hilang permanen!")
                        .setPositiveButton("Ya", (dialog, which) -> deleteUser(prefs))
                        .setNegativeButton("Tidak", null)
                        .show();
            });

            btnBack.setOnClickListener(v -> {
                // Tutup fragment ini dan kembali ke daftar user
                getParentFragmentManager().popBackStack();
            });

            return view;
        }

        private void showUpdateDialog(SharedPreferences prefs) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Update Data User");

            View dialogView = getLayoutInflater().inflate(R.layout.dialog_update_user, null);
            EditText etName = dialogView.findViewById(R.id.etUpdateName);
            EditText etEmail = dialogView.findViewById(R.id.etUpdateEmail);

            etName.setText(prefs.getString("name", ""));
            etEmail.setText(prefs.getString("email", ""));

            builder.setView(dialogView);

            builder.setPositiveButton("Update", (dialog, which) -> {
                String newName = etName.getText().toString().trim();
                String newEmail = etEmail.getText().toString().trim();

                if (newName.isEmpty() || newEmail.isEmpty()) {
                    Toast.makeText(getContext(), "Nama dan email wajib diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                updateUserToServer(prefs, newName, newEmail);
            });

            builder.setNegativeButton("Batal", null);
            builder.show();
        }

        private void updateUserToServer(SharedPreferences prefs, String name, String email) {
            String token = prefs.getString("token", null);

            if (token == null) {
                Toast.makeText(getContext(), "User tidak terautentikasi", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiService api = ApiClient.getClient().create(ApiService.class);
            api.updateUser("Bearer " + token, name, email).enqueue(new Callback<UpdateUserResponse>() {
                @Override
                public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(getContext(), "Update berhasil", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("name", name);
                        editor.putString("email", email);
                        editor.apply();

                        tvName.setText(name);
                        tvEmail.setText(email);
                    } else {
                        Toast.makeText(getContext(), "Gagal update data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void deleteUser(SharedPreferences prefs) {
            String token = prefs.getString("token", null);

            if (token == null) {
                Toast.makeText(getContext(), "User tidak terautentikasi", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiService api = ApiClient.getClient().create(ApiService.class);
            api.deleteUser("Bearer " + token).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.clear();
                        editor.apply();

                        Toast.makeText(getContext(), "Akun berhasil dihapus", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getActivity(), com.example.mediconnect.LoginActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        requireActivity().finish();
                    } else {
                        Toast.makeText(getContext(), "Gagal menghapus akun", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void logoutUser(SharedPreferences prefs) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Logout")
                    .setMessage("Apakah kamu yakin ingin logout?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.clear();
                        editor.apply();

                        startActivity(new Intent(getActivity(), com.example.mediconnect.LoginActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        requireActivity().finish();
                    })
                    .setNegativeButton("Tidak", null)
                    .show();
        }

    }
