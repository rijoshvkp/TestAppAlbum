package com.example.testapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
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
import com.example.testapp.common.ServicesUrl;
import com.example.testapp.model.AlbumsModel;
import com.example.testapp.adapter.ImageAdapter;
import com.example.testapp.model.ImageModel;
import com.example.testapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    private String albumid;
    private RecyclerView rvImage;
    private LinearLayout linearLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        init();
        getImageFromServer(albumid);
    }

    private void init() {
        linearLoading=findViewById(R.id.linear_loading);
        rvImage = findViewById(R.id.rvimg);
        AlbumsModel albumsModel = new Gson().fromJson(getIntent().getStringExtra("albumdata"), new TypeToken<AlbumsModel>() {
        }.getType());
        if(albumsModel!=null)
        albumid = albumsModel.getId().toString();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void getImageFromServer(final String albumid) {
        linearLoading.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, new ServicesUrl().url_showImage+"?albumId="+albumid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                new DogetImage(getApplicationContext(),rvImage,linearLoading).execute(response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                linearLoading.setVisibility(View.GONE);


            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }



//////////////////////////////////////Async task for set image to recyclerview//////////////////////////

    private static class DogetImage extends AsyncTask<String,Void,ArrayList<ImageModel>> {

        WeakReference<Context> contextWeakReference;
        WeakReference<RecyclerView>  rvdata;
        ArrayList<ImageModel> imageModels;
        WeakReference<LinearLayout>  linearLayout;
        private DogetImage(Context contextWeakReference,RecyclerView rvdata,LinearLayout linearLayout) {
            this.contextWeakReference = new WeakReference<>(contextWeakReference);
            this.rvdata= new WeakReference<>(rvdata);
            this.linearLayout= new WeakReference<>(linearLayout);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            linearLayout.get().setVisibility(View.GONE);

        }

        @Override
        protected ArrayList<ImageModel> doInBackground(String... strings) {
            imageModels = new Gson().fromJson(strings[0], new TypeToken<List<ImageModel>>() {}.getType());
            return imageModels;
        }

        @Override
        protected void onPostExecute(ArrayList<ImageModel> imageModels) {
            super.onPostExecute(imageModels);

            ImageAdapter albumAdapter = new ImageAdapter(contextWeakReference.get(), imageModels);
            GridLayoutManager manager = new GridLayoutManager(contextWeakReference.get(), 2, GridLayoutManager.VERTICAL, false);
            rvdata.get().setLayoutManager(manager);
            rvdata.get().setAdapter(albumAdapter);
        }

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
                Toast.makeText(ImageActivity.this,"search",Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }
}
