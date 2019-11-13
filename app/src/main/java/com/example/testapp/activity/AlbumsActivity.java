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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testapp.adapter.AlbumAdapter;
import com.example.testapp.common.ServicesUrl;
import com.example.testapp.model.AlbumsModel;
import com.example.testapp.R;
import com.example.testapp.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AlbumsActivity extends AppCompatActivity implements AlbumAdapter.AlbumListener {
    private String userid;
    private RecyclerView rvAlbums;
    private LinearLayout linearLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        init();
        getAlbamFromServer(userid);
    }


    private void init() {
        linearLoading=findViewById(R.id.linear_loading);
        rvAlbums = findViewById(R.id.rvalbums);
        UserModel userModel = new Gson().fromJson(getIntent().getStringExtra("userdata"), new TypeToken<UserModel>() {
        }.getType());

        if(userModel!=null)
        userid = userModel.getId().toString();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void getAlbamFromServer(final String userid) {
        linearLoading.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, new ServicesUrl().url_showalbums+"?userId="+userid, new Response.Listener<String>() {
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
        ArrayList<AlbumsModel> albumsModelArrayList = new Gson().fromJson(response, new TypeToken<List<AlbumsModel>>() {}.getType());
        AlbumAdapter albumAdapter = new AlbumAdapter(AlbumsActivity.this, albumsModelArrayList, this);
        rvAlbums.setAdapter(albumAdapter);


    }

    @Override
    public void itemClick(AlbumsModel albumsModel) {

        Intent intent=new Intent(AlbumsActivity.this,ImageActivity.class);
        intent.putExtra("albumdata",new Gson().toJson(albumsModel));
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
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;

            case R.id.search:
                Toast.makeText(AlbumsActivity.this,"search",Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }
}
