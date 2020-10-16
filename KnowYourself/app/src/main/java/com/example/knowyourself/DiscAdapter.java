package com.example.knowyourself;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiscAdapter extends RecyclerView.Adapter<DiscAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<DISC> disc;

    public DiscAdapter(Context c, ArrayList<DISC> list) {
        mContext = c;
        disc = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewQues.setText(disc.get(position).getQues());
    }

    @Override
    public int getItemCount() {
        return disc.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewQues;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            textViewQues = (TextView) itemView.findViewById(R.id.textViewQue);
        }
    }
}
