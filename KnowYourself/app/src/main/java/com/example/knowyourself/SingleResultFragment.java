package com.example.knowyourself;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SingleResultFragment extends Fragment {

    FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference, mDatabaseContent;

    //Shared Preferences
    private SharedPreferences mPreferences;
    private String spFileName = "com.example.sharedpreference" ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_single_result,container,false);

        //check if user currently log in
        mFirebaseAuth = FirebaseAuth.getInstance();

        //initialize shared preferences
        mPreferences = this.getActivity().getSharedPreferences(spFileName, getContext().MODE_PRIVATE);

        if (mFirebaseAuth.getCurrentUser() != null) {
            //get current userID
            FirebaseUser user = mFirebaseAuth.getCurrentUser();
            String uid = user.getUid();

            //get timeStamp of result
            String ts = mPreferences.getString("resultTimeStamp","none");

            mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("assessment")
                    .child("DISC")
                    .child("result")
                    .child(uid)
                    .child(ts);

            mDatabaseContent = FirebaseDatabase.getInstance().getReference()
                    .child("assessment")
                    .child("DISC")
                    .child("personality");

            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int noD = Integer.parseInt(dataSnapshot.child("D").getValue().toString());
                    int noI = Integer.parseInt(dataSnapshot.child("I").getValue().toString());
                    int noS = Integer.parseInt(dataSnapshot.child("S").getValue().toString());
                    int noC = Integer.parseInt(dataSnapshot.child("C").getValue().toString());

                    HashMap<String,Integer> map=new HashMap<String, Integer>();
                    map.put("D", noD);
                    map.put("I", noI);
                    map.put("S", noS);
                    map.put("C", noC);
                    int maxValueInMap=(Collections.max(map.values()));  // This will return max value in the Hashmap
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {  // Itrate through hashmap
                        if (entry.getValue()==maxValueInMap) {
                            // Print the key with max value
                            mDatabaseContent.child(entry.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            Toast.makeText(getContext(),"Result Saved", Toast.LENGTH_SHORT).show();

        }else{
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new SignInFragment()).addToBackStack(null).commit();
        }

        return view;
    }
}
