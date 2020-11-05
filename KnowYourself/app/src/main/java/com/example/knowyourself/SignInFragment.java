package com.example.knowyourself;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment{

    //signIn
    private Button btnSignIn;
    private EditText eTextEmail;
    private EditText eTextPassword;
    private TextView btnRegisterNow;

    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;

    private InputMethodManager imm;
    //Shared Preferences
    private SharedPreferences mPreferences;
    private String spFileName = "com.example.sharedpreference" ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_signin, container, false);

        btnSignIn = (Button) view.findViewById(R.id.btn_signIn);
        eTextEmail = (EditText) view.findViewById(R.id.editText_email);
        eTextPassword = (EditText) view.findViewById(R.id.editText_password);
        btnRegisterNow = (TextView) view.findViewById(R.id.register_now);
        imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();

        //initialize shared preferences
        mPreferences = this.getActivity().getSharedPreferences(spFileName, getContext().MODE_PRIVATE);

        btnRegisterNow.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new RegisterFragment()).addToBackStack(null).commit();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                String email = eTextEmail.getText().toString().trim();
                String password = eTextPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    //email is empty
                    Snackbar.make(view, "Please enter the email.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    //password is empty
                    Snackbar.make(view, "Please enter the password.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    //email is invalid format
                    Snackbar.make(view, "Please enter a valid email address.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                mProgressDialog = new ProgressDialog(getActivity());
                mFirebaseAuth = FirebaseAuth.getInstance();

                //if the fields all filled up
                mProgressDialog.setMessage("Signing In...");
                mProgressDialog.show();

                mFirebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProgressDialog.dismiss();
                                if (task.isSuccessful()){

                                    //initialization of editor
                                    final SharedPreferences.Editor spEditor = mPreferences.edit();
                                    //put key-value pair
                                    spEditor.putString("signIn","Yes");
                                    //save the preferences
                                    spEditor.commit();

                                    Toast.makeText(getActivity(),"Sign In Successfully",Toast.LENGTH_SHORT).show();
                                    //Redirect to profile
                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, new HomeFragment())
                                            .addToBackStack(null).commit();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        //updateUI(currentUser);
    }
}
