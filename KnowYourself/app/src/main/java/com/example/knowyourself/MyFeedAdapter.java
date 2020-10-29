package com.example.knowyourself;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyFeedAdapter extends RecyclerView.Adapter<MyFeedAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<MyFeed> myFeed;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();

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
        String fileName = myFeed.get(position).getFileName();

        storageRef.child("/"+fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri url) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(mContext).load(url).into(holder.photo);
                Toast.makeText(mContext,"Success" ,Toast.LENGTH_SHORT).show();            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

    @Override
    public int getItemCount() {
        return myFeed.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,article;
        ImageView photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_feedTitle);
            article = (TextView) itemView.findViewById(R.id.tv_feedContent);
            photo = (ImageView) itemView.findViewById(R.id.imageViewFeed);
        }
    }
}
