package com.example.knowyourself;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class ProfileFragment extends Fragment {

    private TextView mTextViewUser, tvFullName, tvEmail, tvGender, tvBirthdate;
    String gender = "";
    private ImageView avatar;
    private Button btnLogOut, btnViewHistory;

    FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        //check if user currently log in
        mFirebaseAuth = FirebaseAuth.getInstance();

        mTextViewUser = (TextView) view.findViewById(R.id.textViewUserName);
        tvFullName = (TextView) view.findViewById(R.id.tv_profile_fullname);
        tvEmail = (TextView) view.findViewById(R.id.tv_profile_email);
        tvGender = (TextView) view.findViewById(R.id.tv_profile_gender);
        tvBirthdate = (TextView) view.findViewById(R.id.tv_profile_date);
        avatar = (ImageView) view.findViewById(R.id.imageView8);

        btnViewHistory = (Button) view.findViewById(R.id.btn_viewResult);
        btnLogOut = (Button) view.findViewById(R.id.btn_logOut);

        if (mFirebaseAuth.getCurrentUser() != null) {
            //get current user email and display on profile
            FirebaseUser user = mFirebaseAuth.getCurrentUser();
            //get current user id
            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");

            //retrieved data from firebase based on given userID
            mDatabaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("noteText").getValue()!=null) {
                        gender = dataSnapshot.child("gender").getValue().toString();
                        //set text in text view
                        mTextViewUser.setText("Welcome " + dataSnapshot.child("firstName").getValue().toString());
                        tvFullName.setText(dataSnapshot.child("firstName").getValue().toString() + " " +
                                                dataSnapshot.child("lastName").getValue().toString());
                        tvGender.setText(gender);
                        tvBirthdate.setText(dataSnapshot.child("birthDate").getValue().toString() + " - " +
                                                dataSnapshot.child("birthMonth").getValue().toString() + " - " +
                                                dataSnapshot.child("birthYear").getValue().toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            if (gender.equals("Male")){
                avatar.setImageResource(R.drawable.ic_avatar_male);
            }

            if (gender.equals("Female")){
                avatar.setImageResource(R.drawable.ic_avatar_female);
            }

            tvEmail.setText("" + user.getEmail());

            btnLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFirebaseAuth.signOut();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment()).addToBackStack(null).commit();
                }
            });

            btnViewHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new ResultHistoryFragment()).addToBackStack(null).commit();
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
