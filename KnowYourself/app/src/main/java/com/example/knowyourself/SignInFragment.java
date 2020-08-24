package com.example.knowyourself;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class SignInFragment extends Fragment{

    //signIn
    private Button btnSignIn;
    private EditText eTextEmail;
    private EditText eTextPassword;
    private TextView btnRegisterNow;
    private SignInButton signInGoogle;


    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;

    private InputMethodManager imm;

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_signin, container, false);

        btnSignIn = (Button) view.findViewById(R.id.btn_signIn);
        eTextEmail = (EditText) view.findViewById(R.id.editText_email);
        eTextPassword = (EditText) view.findViewById(R.id.editText_password);
        btnRegisterNow = (TextView) view.findViewById(R.id.register_now);
        signInGoogle = (SignInButton) view.findViewById(R.id.btn_signIn_google);
        imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

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
                                    Toast.makeText(getActivity(),"Sign In Successfully",Toast.LENGTH_SHORT).show();
                                    //Redirect to profile
                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment()).addToBackStack(null).commit();
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

        // Configure Google Sign In
         gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        return view;
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 189);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 189) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }
}
