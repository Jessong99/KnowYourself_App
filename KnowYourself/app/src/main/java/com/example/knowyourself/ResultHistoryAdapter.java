package com.example.knowyourself;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.timestamp.setText("Test taken on: " + result.get(position).getTimestamp());
        holder.result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Click"+position, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {return result.size();}

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView timestamp;
        CardView result;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timestamp = (TextView) itemView.findViewById(R.id.history_ts);
            result = (CardView) itemView.findViewById(R.id.cardview_result);
        }
    }
}
