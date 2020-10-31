package com.example.knowyourself;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class SingleFeedFragment extends Fragment {

    TextView tvTitle, tvFeed, tvTS;
    ImageView ivFeed;

    //Shared Preferences
    private SharedPreferences mPreferences;
    private String spFileName = "com.example.sharedpreference";

    private DatabaseReference mDatabaseReference;
    FirebaseStorage storage;
    // Create a storage reference from our app
    StorageReference storageRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_single_feed, container, false);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        tvFeed = (TextView) view.findViewById(R.id.tv_singleFeed);
        tvTitle = (TextView) view.findViewById(R.id.tv_singleTitle);
        tvTS = (TextView) view.findViewById(R.id.tv_singleTS);
        ivFeed = (ImageView) view.findViewById(R.id.iv_singleFeed);

        //initialize shared preferences
        mPreferences = this.getActivity().getSharedPreferences(spFileName, getContext().MODE_PRIVATE);

        //get timeStamp of result
        final String ts = mPreferences.getString("feedTimeStamp","none");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("myFeed");

        mDatabaseReference.child(ts).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String title = (String) snapshot.child("title").getValue();
                String article = (String) snapshot.child("article").getValue();
                String fileName = (String) snapshot.child("fileName").getValue();

                Long time = Long. parseLong(ts);
                LocalDateTime triggerTime =
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(time),
                                TimeZone.getDefault().toZoneId());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedDateTime = triggerTime.format(formatter);

                tvTitle.setText(title);
                tvFeed.setText(article);
                tvTS.setText(formattedDateTime);
                storageRef.child("/"+fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri url) {
                        Toast.makeText(view.getContext(),ts,Toast.LENGTH_SHORT);
                        Glide.with(view.getContext()).load(url).into(ivFeed);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}
