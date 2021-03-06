package com.example.knowyourself;

import android.app.ProgressDialog;
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
import java.util.Date;

public class DiscFragment extends Fragment {

    //RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<DISC> mList;

    private int totalQue = 0;
    //Firebase
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference, mDatabaseReference2;

    private Button btnSubmitTest;
    private ProgressDialog mProgressDialog;
    String signIn = "No";
    //Shared Preferences
    private SharedPreferences mPreferences;
    private String spFileName = "com.example.sharedpreference" ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragmemt_disc,container,false);

        //initialize shared preferences
        mPreferences = this.getActivity().getSharedPreferences(spFileName, getContext().MODE_PRIVATE);
        signIn = mPreferences.getString("signIn", "No");

        if (signIn.equals("Yes")) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Loading Test...");
            mProgressDialog.show();

            //new saved preference
            mPreferences.edit().clear().apply();
            //initialization of editor
            final SharedPreferences.Editor spEditor = mPreferences.edit();
            //put key-value pair
            spEditor.putString("signIn","Yes");
            //save the preferences
            spEditor.commit();

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
                        totalQue++;
                    }
                    mAdapter = new DiscAdapter(getContext(),mList);
                    recyclerView.setAdapter(mAdapter);
                    mProgressDialog.dismiss();
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
                    int noD = 0;
                    int noI = 0;
                    int noS = 0;
                    int noC = 0;

                    for (int m = 0; m <= totalQue; m++) {
                        String position = String.valueOf(m);
                        String getType = mPreferences.getString(position, "none");

                        //Count score for each type
                        switch (getType) {
                            case "D":
                                noD++;
                                break;
                            case "I":
                                noI++;
                                break;
                            case "S":
                                noS++;
                                break;
                            case "C":
                                noC++;
                                break;
                            default:
                                break;
                        }
                    }

                    int totalSelected = noD + noI + noS + noC;


                    String q = Integer.toString(totalSelected);
                    //make sure all question is completed
                    if (totalSelected != totalQue) {
                        Toast.makeText(getContext(), "Please complete all questions.", Toast.LENGTH_SHORT).show();
                    }else {

                        //check if user currently log in
                        mFirebaseAuth = FirebaseAuth.getInstance();
                        if (mFirebaseAuth.getCurrentUser() != null) {
                            //get current userID
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            String uid = user.getUid();
                            //get current timeStamp
                            Long tsLong = new Date().getTime();
                            String ts = tsLong.toString();

                            mDatabaseReference2 = FirebaseDatabase.getInstance().getReference()
                                    .child("assessment")
                                    .child("DISC")
                                    .child("result")
                                    .child(uid)
                                    .child(ts);

                            //save DISC test result into Firebase
                            mDatabaseReference2.child("D").setValue(noD);
                            mDatabaseReference2.child("I").setValue(noI);
                            mDatabaseReference2.child("S").setValue(noS);
                            mDatabaseReference2.child("C").setValue(noC);

                            //initialization of editor
                            final SharedPreferences.Editor spEditor = mPreferences.edit();
                            //put key-value pair
                            spEditor.putString("resultTimeStamp",ts);
                            //save the preferences
                            spEditor.apply();

                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, new SingleResultFragment()).addToBackStack(null).commit();

                        }
                    }
                }
            }));
        }else {
            Toast.makeText(getContext(),"Please sign in to take test.",Toast.LENGTH_SHORT).show();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new SignInFragment()).addToBackStack(null).commit();
        }

        return view;
    }
}
