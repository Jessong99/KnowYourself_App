package com.example.knowyourself;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyFeedFragment extends Fragment {
    //RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference mDatabaseReference;

    private ArrayList<MyFeed> mList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_myfeed,container,false);

        //Set Up recyclerView
        recyclerView = (RecyclerView)view.findViewById(R.id.myfeed_recycler_view);// use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("myFeed");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mList = new ArrayList<MyFeed>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MyFeed f = new MyFeed();

                    String ts = (String) dataSnapshot1.getKey();
                    String feedTitle = (String) dataSnapshot1.child("title").getValue();
                    String feedContent = (String) dataSnapshot1.child("article").getValue();
                    String fileName = (String) dataSnapshot1.child("fileName").getValue();

                    f.setTimeStamp(ts);
                    f.setArticle(feedContent);
                    f.setTitle(feedTitle);
                    f.setFileName(fileName);

                    mList.add(f);
                }
                mAdapter = new MyFeedAdapter(getContext(),mList);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}
