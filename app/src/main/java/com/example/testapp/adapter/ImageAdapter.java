package com.example.testapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testapp.R;
import com.example.testapp.model.ImageModel;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyHolder> {
    private Context context;
    private ArrayList<ImageModel> imageModelArrayList;

    public ImageAdapter(Context context, ArrayList<ImageModel> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row_item,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.albumtitle.setText(imageModelArrayList.get(position).getTitle());
        Glide.with(context)
                .load(imageModelArrayList.get(position).getUrl())
                .centerCrop()
                .into(holder.ivthumb);




    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView albumtitle;
        ImageView ivthumb;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            albumtitle =itemView.findViewById(R.id.iv_title);
            ivthumb =itemView.findViewById(R.id.iv_thumb);



        }
    }


}
