package com.example.knowyourself;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    //RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<DISC> mList;

    //Firebase
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    private Button btnSubmitTest;
    //Shared Preferences
    private SharedPreferences mPreferences;
    private String spFileName = "com.example.sharedpreference" ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragmemt_disc,container,false);

        //initialize shared preferences
        mPreferences = this.getActivity().getSharedPreferences(spFileName, getContext().MODE_PRIVATE);

        //Set Up recyclerView
        recyclerView = (RecyclerView)view.findViewById(R.id.disc_recycler_view);// use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //retrieve data from firebase
        mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("assessment")
                .child("DISC")
                .child("ques");

        //todo mDatabaseReference.keepSynced(true);
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mList = new ArrayList<DISC>();

                //get ques children
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    DISC d = new DISC();
                    String ques = dataSnapshot1.getKey();
                    int counter = 1;
                    //get all selection & type in ques
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        String sel = (String) dataSnapshot2.child("selection").getValue();
                        String type = (String) dataSnapshot2.child("type").getValue();
                        //save into arrayList
                        switch (counter){
                            case 1:
                                d.setSel1(sel);
                                d.setType1(type);
                                break;
                            case 2:
                                d.setSel2(sel);
                                d.setType2(type);
                                break;
                            case 3:
                                d.setSel3(sel);
                                d.setType3(type);
                                break;
                            case 4:
                                d.setSel4(sel);
                                d.setType4(type);
                                break;
                            default:
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        counter++;
                    }
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

        btnSubmitTest = (Button)view.findViewById(R.id.btnSubmitTest);
        btnSubmitTest.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hi = mPreferences.getString("0","none");
                Toast.makeText(getContext(), hi, Toast.LENGTH_SHORT).show();
            }
        }));

        return view;
    }
}
