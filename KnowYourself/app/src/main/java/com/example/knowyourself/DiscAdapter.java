package com.example.knowyourself;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
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
        String i = "Q" + position + ". Please select.";
        holder.textViewQues.setText(i);
        holder.rBsel1.setText(disc.get(position).getSel1());
        holder.rBsel2.setText(disc.get(position).getSel2());
        holder.rBsel3.setText(disc.get(position).getSel3());
        holder.rBsel4.setText(disc.get(position).getSel4());
    }

    @Override
    public int getItemCount() {
        return disc.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewQues;
        RadioButton rBsel1,rBsel2,rBsel3,rBsel4;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            textViewQues = (TextView) itemView.findViewById(R.id.textViewQue);
            rBsel1 = (RadioButton) itemView.findViewById(R.id.radioButton);
            rBsel2 = (RadioButton) itemView.findViewById(R.id.radioButton2);
            rBsel3 = (RadioButton) itemView.findViewById(R.id.radioButton3);
            rBsel4 = (RadioButton) itemView.findViewById(R.id.radioButton4);
        }
    }
}
