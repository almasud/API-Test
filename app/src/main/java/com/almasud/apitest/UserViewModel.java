package com.almasud.apitest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserViewModel extends ViewModel {
    private MutableLiveData<List<User>> mUsersMutableLiveData;
    private List<User> mUsers = new ArrayList<>();

    public LiveData<List<User>> getUserLiveData() {
        if (mUsersMutableLiveData == null) {
            mUsersMutableLiveData = new MutableLiveData<>();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            UserService service = retrofit.create(UserService.class);
            Call<List<User>> call = service.getUsers();
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        mUsers.clear();
                        mUsers = response.body();
                        mUsersMutableLiveData.setValue(mUsers);
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Log.e("UserViewModel", "User data load failed: "+ t.getMessage());
                }
            });
        }

        return mUsersMutableLiveData;
    }

}
