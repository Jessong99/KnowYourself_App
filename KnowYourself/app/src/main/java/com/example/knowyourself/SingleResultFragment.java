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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingleResultFragment extends Fragment {

    FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_single_result,container,false);

        //check if user currently log in
        mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth.getCurrentUser() != null) {
            //get current userID
            FirebaseUser user = mFirebaseAuth.getCurrentUser();
            String uid = user.getUid();Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();

            mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("assessment")
                    .child("DISC")
                    .child("result")
                    .child(uid)
                    .child(ts);


            Toast.makeText(getContext(),"Result Saved", Toast.LENGTH_SHORT).show();

        }else{
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new SignInFragment()).addToBackStack(null).commit();
        }

        return view;
    }
}
