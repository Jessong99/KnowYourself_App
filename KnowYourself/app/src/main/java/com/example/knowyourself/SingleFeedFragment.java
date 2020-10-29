package com.example.knowyourself;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleFeedFragment extends Fragment {

    TextView tvTitle, tvFeed, tvTS;

    //Shared Preferences
    private SharedPreferences mPreferences;
    private String spFileName = "com.example.sharedpreference";

    private DatabaseReference mDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_single_feed, container, false);

        //initialize shared preferences
        mPreferences = this.getActivity().getSharedPreferences(spFileName, getContext().MODE_PRIVATE);

        //get timeStamp of result
        String ts = mPreferences.getString("feedTimeStamp","none");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("myFeed");

        mDatabaseReference.child(ts).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String title = (String) snapshot.child("title").getValue();
                String article = (String) snapshot.child("article").getValue();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}
