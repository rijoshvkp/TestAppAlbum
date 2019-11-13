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
import com.example.testapp.model.UserModel;
import com.github.akashandroid90.imageletter.MaterialLetterIcon;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyHolder> {
    private Context context;
    private ArrayList<UserModel>userModelArrayList;
    private UserListener userListener;

    public UserAdapter(Context context, ArrayList<UserModel> userModelArrayList,UserListener userListener) {
        this.context = context;
        this.userModelArrayList = userModelArrayList;
        this.userListener=userListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_item,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvUsername.setText(userModelArrayList.get(position).getName());
        holder.tvUseremail.setText(userModelArrayList.get(position).getEmail());
        holder.tvUserMobile.setText(userModelArrayList.get(position).getPhone());

        holder.materialLetterIcon.setLetter(userModelArrayList.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return userModelArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tvUsername,tvUseremail,tvUserMobile;
        MaterialLetterIcon materialLetterIcon;
        LinearLayout linearClick;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername=itemView.findViewById(R.id.tvusername);
            tvUseremail=itemView.findViewById(R.id.tvuseremail);
            tvUserMobile=itemView.findViewById(R.id.tvusernumber);
            materialLetterIcon=itemView.findViewById(R.id.materialicon);
            linearClick=itemView.findViewById(R.id.liner_itemclick);

            linearClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userListener.itemClick(userModelArrayList.get(getAdapterPosition()));
                }
            });
        }
    }


   public interface UserListener{

        void itemClick(UserModel userModel);

    }
}
