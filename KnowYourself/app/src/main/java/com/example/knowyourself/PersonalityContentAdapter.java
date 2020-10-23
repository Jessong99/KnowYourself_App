package com.example.knowyourself;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonalityContentAdapter extends RecyclerView.Adapter<PersonalityContentAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<PersonalityContent> pContent;

    public PersonalityContentAdapter(Context c, ArrayList<PersonalityContent> list) {
        mContext = c;
        pContent = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonalityContentAdapter.MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_content_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(pContent.get(position).getTitle());
        holder.content.setText(pContent.get(position).getContent());
    }


    @Override
    public int getItemCount() {
        return pContent.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,content;

        public  MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
