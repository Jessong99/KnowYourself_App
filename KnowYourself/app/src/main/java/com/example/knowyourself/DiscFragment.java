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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DiscFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<DISC> mList;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragmemt_disc,container,false);

        recyclerView = (RecyclerView)view.findViewById(R.id.disc_recycler_view);// use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //retrieve data from firebase
        mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("assessment")
                .child("DISC")
                .child("ques");

        //todo mDatabaseReference.keepSynced(true);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mList = new ArrayList<DISC>();
                DISC d = new DISC();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String ques = dataSnapshot1.getKey();
                    Toast.makeText(getContext(), ques, Toast.LENGTH_SHORT).show();
                    int counter = 1;
                    d.setQues(ques);
                    mList.add(d);
                }
                mAdapter = new DiscAdapter(getContext(),mList);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });


        Toast.makeText(getContext(),"Success", Toast.LENGTH_SHORT).show();
        return view;
    }
}
