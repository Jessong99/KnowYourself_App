package com.example.knowyourself;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import androidx.appcompat.app.AlertDialog;
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
    String gender = "", signIn = "No";
    private ImageView avatar;
    private Button btnLogOut, btnViewHistory;
    private ProgressDialog mProgressDialog;
    AlertDialog.Builder builder;

    FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    //Shared Preferences
    private SharedPreferences mPreferences;
    private String spFileName = "com.example.sharedpreference" ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        //initialize shared preferences
        mPreferences = this.getActivity().getSharedPreferences(spFileName, getContext().MODE_PRIVATE);
        signIn = mPreferences.getString("signIn", "No");

        mProgressDialog = new ProgressDialog(getActivity());

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

        if (signIn.equals("Yes")) {
            if (mFirebaseAuth.getCurrentUser() != null) {

                mProgressDialog.setMessage("Loading Profile...");
                mProgressDialog.show();

                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                //get current user id
                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");

                //retrieved data from firebase based on given userID
                mDatabaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        gender = dataSnapshot.child("gender").getValue().toString();
                        //set text in text view
                        mTextViewUser.setText("Welcome " + dataSnapshot.child("firstName").getValue().toString());
                        tvFullName.setText(dataSnapshot.child("firstName").getValue().toString() + " " +
                                dataSnapshot.child("lastName").getValue().toString());
                        tvGender.setText(gender);
                        tvBirthdate.setText(dataSnapshot.child("birthDate").getValue().toString() + " - " +
                                dataSnapshot.child("birthMonth").getValue().toString() + " - " +
                                dataSnapshot.child("birthYear").getValue().toString());

                        if (gender == "Male") {
                            avatar.setImageResource(R.drawable.ic_avatar_male);
                        }

                        if (gender.equals("Female")) {
                            avatar.setImageResource(R.drawable.ic_avatar_female);
                        }
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                tvEmail.setText("" + user.getEmail());

                btnLogOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //initialization of editor
                        final SharedPreferences.Editor spEditor = mPreferences.edit();
                        //put key-value pair
                        spEditor.putString("signIn", "No");
                        //save the preferences
                        spEditor.apply();

                        //alert box before delete action
                        builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Log Out");
                        builder.setMessage("Are you sure you want to log out ?")
                                .setIcon(R.drawable.ic_exit_to_app_black_24dp)
                                .setPositiveButton("LOG OUT", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        mFirebaseAuth.signOut();
                                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment()).addToBackStack(null).commit();
                                    }
                                })
                                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User select cancel and close the dialog box
                                    }
                                });
                        builder.create();
                        builder.show();

                    }
                });

                btnViewHistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, new ResultHistoryFragment()).addToBackStack(null).commit();
                    }
                });

            } else {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new SignInFragment()).addToBackStack(null).commit();
                Toast.makeText(getContext(), "Please sign in to view profile.", Toast.LENGTH_SHORT).show();

            }
        }else {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new SignInFragment()).addToBackStack(null).commit();
            Toast.makeText(getContext(), "Please sign in to view profile.", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

}
