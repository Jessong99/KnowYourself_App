package com.example.knowyourself;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private TextView mTextViewEmail;
    private Button btnLogOut;
    FirebaseAuth mFirebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        //check if user currently log in
        mFirebaseAuth = FirebaseAuth.getInstance();

        mTextViewEmail = (TextView) view.findViewById(R.id.textViewUserEmail);
        btnLogOut = (Button) view.findViewById(R.id.btn_logOut);

        if (mFirebaseAuth.getCurrentUser() != null) {
            //get current user email and display on profile
            FirebaseUser user = mFirebaseAuth.getCurrentUser();
            mTextViewEmail.setText(
                    "" + user.getEmail());

            btnLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFirebaseAuth.signOut();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment()).addToBackStack(null).commit();
                }
            });
        }else{
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new SignInFragment()).addToBackStack(null).commit();
            Toast.makeText(getContext(),"Please sign in to view profile.",Toast.LENGTH_SHORT).show();

        }
        return view;
    }
}
