package com.example.knowyourself;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyFeedAdapter extends RecyclerView.Adapter<MyFeedAdapter.MyViewHolder> {


    Context mContext;
    ArrayList<MyFeed> myFeed;

    public MyFeedAdapter(Context c, ArrayList<MyFeed> list){
        mContext = c;
        myFeed = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyFeedAdapter.MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_myfeed_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.title.setText(myFeed.get(position).getTitle());
        holder.article.setText(myFeed.get(position).getArticle());
    }

    @Override
    public int getItemCount() {
        return myFeed.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,article;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_feedTitle);
            article = (TextView) itemView.findViewById(R.id.tv_feedContent);
        }
    }
}
