package com.example.mediconnect.fragmentadmin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.R;
import com.example.mediconnect.adapter.UserAdapter;
import com.example.mediconnect.model.UsersResponse;
import com.example.mediconnect.network.ApiClient;
import com.example.mediconnect.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUsersFragment extends Fragment {

    private RecyclerView rvUsers;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_admin_users, container, false);

        rvUsers = view.findViewById(R.id.rvUsers);
        rvUsers.setLayoutManager(new LinearLayoutManager(requireContext()));

        loadUsers();

        return view;
    }

    private void loadUsers() {
        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("AUTH", Context.MODE_PRIVATE);

        String token = prefs.getString("token", null);
        if (token == null) return;

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.getAllUsers("Bearer " + token)
                .enqueue(new Callback<UsersResponse>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<UsersResponse> call,
                            @NonNull Response<UsersResponse> response
                    ) {
                        if (response.isSuccessful() && response.body() != null) {
                            rvUsers.setAdapter(
                                    new UserAdapter(response.body().getData())
                            );
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<UsersResponse> call,
                            @NonNull Throwable t
                    ) {
                        Log.e("AdminUsers", t.getMessage());
                    }
                });
    }
}
