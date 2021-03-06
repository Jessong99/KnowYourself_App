package com.example.knowyourself;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MyFeedAdapter extends RecyclerView.Adapter<MyFeedAdapter.MyViewHolder> implements Filterable {

    Context mContext;
    ArrayList<MyFeed> myFeed;
    ArrayList<MyFeed> myFeedFull;

    //Shared Preferences
    private SharedPreferences mPreferences;
    private String spFileName = "com.example.sharedpreference" ;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();

    public MyFeedAdapter(Context c, ArrayList<MyFeed> list){
        mContext = c;
        myFeed = list;
        myFeedFull = new ArrayList<>(myFeed);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyFeedAdapter.MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_myfeed_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.title.setText(myFeed.get(position).getTitle());
        holder.article.setText(myFeed.get(position).getArticle());
        String fileName = myFeed.get(position).getFileName();

        //initialize shared preferences
        mPreferences = this.mContext.getSharedPreferences(spFileName, MODE_PRIVATE);


        storageRef.child("/"+fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri url) {
                Glide.with(mContext).load(url).into(holder.photo);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        holder.cardview_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //initialization of editor
                final SharedPreferences.Editor spEditor = mPreferences.edit();
                //put key-value pair
                spEditor.putString("feedTimeStamp",myFeed.get(position).getTimeStamp());
                //save the preferences
                spEditor.apply();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SingleFeedFragment()).addToBackStack(null).commit();
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
        CardView cardview_feed;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_feedTitle);
            article = (TextView) itemView.findViewById(R.id.tv_feedContent);
            photo = (ImageView) itemView.findViewById(R.id.imageViewFeed);
            cardview_feed = (CardView) itemView.findViewById(R.id.cardview_feed);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MyFeed> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(myFeedFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (MyFeed item : myFeedFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            myFeed.clear();
            myFeed.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
