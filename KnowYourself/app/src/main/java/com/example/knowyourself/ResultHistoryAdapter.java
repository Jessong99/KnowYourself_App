package com.example.knowyourself;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ResultHistoryAdapter extends RecyclerView.Adapter<ResultHistoryAdapter.MyViewHolder> {

    //Shared Preferences
    private SharedPreferences mPreferences;
    private String spFileName = "com.example.sharedpreference" ;

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

        //initialize shared preferences
        mPreferences = this.mContext.getSharedPreferences(spFileName, MODE_PRIVATE);

        holder.timestamp.setText("Test taken on: " + result.get(position).getTimestamp());
        holder.result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialization of editor
                final SharedPreferences.Editor spEditor = mPreferences.edit();
                //put key-value pair
                spEditor.putString("resultTimeStamp",result.get(position).getTimestamp());
                //save the preferences
                spEditor.apply();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SingleResultFragment()).addToBackStack(null).commit();
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
