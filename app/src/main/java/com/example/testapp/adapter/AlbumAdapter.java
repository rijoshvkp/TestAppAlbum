package com.example.testapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.model.AlbumsModel;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyHolder> {
    private Context context;
    private ArrayList<AlbumsModel> albumsModelArrayList;
    private AlbumListener albumListener;

    public AlbumAdapter(Context context, ArrayList<AlbumsModel> albumsModelArrayList, AlbumListener albumListener) {
        this.context = context;
        this.albumsModelArrayList = albumsModelArrayList;
        this.albumListener = albumListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.album_row_item,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.albumtitle.setText(albumsModelArrayList.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return albumsModelArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView albumtitle;
        LinearLayout linearClick;
        private MyHolder(@NonNull View itemView) {
            super(itemView);

            albumtitle =itemView.findViewById(R.id.tvalbumname);

            linearClick=itemView.findViewById(R.id.liner_itemclick);

            linearClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    albumListener.itemClick(albumsModelArrayList.get(getAdapterPosition()));
                }
            });
        }
    }

   public interface AlbumListener {

        void itemClick(AlbumsModel userModel);

    }
}
