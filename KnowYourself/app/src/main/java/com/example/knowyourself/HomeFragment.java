package com.example.knowyourself;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    private Button btnDISCTest, btnResultHisotry, btnMyFeed, btnPersonalityProfile;
    FirebaseAuth mFirebaseAuth;

    //Shared Preferences
    private SharedPreferences mPreferences;
    private String spFileName = "com.example.sharedpreference" ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_home,container,false);
        //check if user currently log in
        mFirebaseAuth = FirebaseAuth.getInstance();

        btnDISCTest = (Button) view.findViewById(R.id.btn_DISCTest);
        btnResultHisotry = (Button) view.findViewById(R.id.btn_DISCResult);
        btnPersonalityProfile = (Button) view.findViewById(R.id.btn_profile);
        btnMyFeed = (Button) view.findViewById(R.id.btn_myFeed);

        if (mFirebaseAuth.getCurrentUser() != null) {
            //implement DISC test
            btnDISCTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //initialize shared preferences
                    mPreferences = getContext().getSharedPreferences(spFileName, getContext().MODE_PRIVATE);
                    //new saved preference
                    mPreferences.edit().clear().apply();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new DiscFragment()).addToBackStack(null).commit();
                }
            });

            //display result history
            btnResultHisotry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new ResultHistoryFragment()).addToBackStack(null).commit();
                }
            });

            //display personality profile
            btnPersonalityProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            //redirect to MyFeed section
            btnMyFeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new MyFeedFragment()).addToBackStack(null).commit();
                }
            });

        }else{
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new SignInFragment()).addToBackStack(null).commit();

        }
        return view;
    }
}
