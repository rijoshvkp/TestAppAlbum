package com.example.testapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testapp.R;
import com.example.testapp.adapter.UserAdapter;
import com.example.testapp.common.ServicesUrl;
import com.example.testapp.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements UserAdapter.UserListener {


    RecyclerView rvUsers;
    UserAdapter userAdapter;
    LinearLayout linearLoading;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        getDataFromServer();
    }

    private void init() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rvUsers=findViewById(R.id.rvusers);
        linearLoading=findViewById(R.id.linear_loading);


    }


////////////////////////////////////////get userdetails/////////////////////////////////////////////

    private void getDataFromServer() {
        linearLoading.setVisibility(View.VISIBLE);///lotty visibility

        StringRequest stringRequest=new StringRequest(Request.Method.GET, new ServicesUrl().url_showusers, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                setDatatoRecyclerview(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                linearLoading.setVisibility(View.GONE);

            }
        });
       Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

        }

    public void setDatatoRecyclerview(String response) {

        linearLoading.setVisibility(View.GONE);

        ArrayList<UserModel>userModels=new Gson().fromJson(response,new TypeToken<List<UserModel>>(){}.getType());
        userAdapter=new UserAdapter(UserActivity.this,userModels,this);
        rvUsers.setAdapter(userAdapter);

    }

    /////////////////////////////////////////Item click interface/////////////////////////////////////////////////

    @Override
    public void itemClick(UserModel userModel) {

        Intent intent=new Intent(UserActivity.this,AlbumsActivity.class);
        intent.putExtra("userdata",new Gson().toJson(userModel));
        startActivity(intent);
        overridePendingTransition(0,0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

}
