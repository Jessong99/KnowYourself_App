package com.example.knowyourself;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultHistoryAdapter extends RecyclerView.Adapter<ResultHistoryAdapter.MyViewHolder> {


    Context mContext;
    ArrayList<ResultHistory> result;

    public ResultHistoryAdapter(Context c, ArrayList<ResultHistory> list){
        mContext = c;
        result = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultHistoryAdapter.MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_history_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.timestamp.setText("Test taken on: " + result.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {return result.size();}

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView timestamp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timestamp = (TextView) itemView.findViewById(R.id.history_ts);
        }
    }
}
