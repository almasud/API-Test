package com.almasud.apitest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    @GET("api/test1")
    Call<List<User>> getUsers();
}
