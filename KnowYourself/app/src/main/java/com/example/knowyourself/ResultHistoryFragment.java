package com.example.knowyourself;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResultHistoryFragment extends Fragment {
    //RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //Set Up Firebase
    FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference, mDatabaseContent;

    private ArrayList<ResultHistory> mList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_result_history,container,false);

        //Set Up recyclerView
        recyclerView = (RecyclerView)view.findViewById(R.id.result_history_recycler_view);// use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //check if user currently log in
        mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser() != null) {
            //get current userID
            FirebaseUser user = mFirebaseAuth.getCurrentUser();
            String uid = user.getUid();

            mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("assessment")
                    .child("DISC")
                    .child("result")
                    .child(uid);

            mDatabaseReference.orderByKey().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mList = new ArrayList<ResultHistory>();

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ResultHistory r = new ResultHistory();
                        String ts = (String) dataSnapshot1.getKey();
                        r.setTimestamp(ts);
                        mList.add(r);
                    }
                    mAdapter = new ResultHistoryAdapter(getContext(),mList);
                    recyclerView.setAdapter(mAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new SignInFragment()).addToBackStack(null).commit();
        }

        return view;
    }
}

