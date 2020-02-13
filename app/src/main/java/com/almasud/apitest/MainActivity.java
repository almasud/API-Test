package com.almasud.apitest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "http://3.17.143.30/";
    private UserListAdapter mUserListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView userRecyclerView = findViewById(R.id.user_recycler_view);
        mUserListAdapter = new UserListAdapter(new ArrayList<User>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        userRecyclerView.setLayoutManager(layoutManager);
        userRecyclerView.setAdapter(mUserListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        LiveData<List<User>> usersLiveData = userViewModel.getUserLiveData();
        usersLiveData.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (!users.isEmpty())
                    mUserListAdapter.updateUsers(users);
            }
        });
    }
}
