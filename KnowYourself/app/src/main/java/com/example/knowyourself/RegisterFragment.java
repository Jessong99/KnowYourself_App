package com.example.knowyourself;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegisterFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private EditText eTextEmailR, eTextPasswordR, firstName, name;
    private InputMethodManager imm;
    private Button buttonR, btnDate;
    private TextView signInNow;
    private Spinner mSpinner;
    private String item;
    private String bDay = "";
    private String bMonth = "";
    private String bYear = "";

    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register, container, false);

        eTextEmailR = (EditText) view.findViewById(R.id.editText_emailR);
        eTextPasswordR = (EditText) view.findViewById(R.id.editText_passwordR);
        firstName = (EditText) view.findViewById(R.id.editText_fNameR);
        name = (EditText) view.findViewById(R.id.editText_lNameR);
        signInNow = (TextView) view.findViewById(R.id.signIn_now);

        buttonR = (Button) view.findViewById(R.id.btn_register);
        mSpinner = (Spinner) view.findViewById(R.id.spinnerGender);
        btnDate = (Button) view.findViewById(R.id.btn_date);

        imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users");

        //create a list of items for the spinner.
        String[] items = new String[]{"Male", "Female"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });


        signInNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new SignInFragment()).addToBackStack(null).commit();
            }
        });

        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                String emailR = eTextEmailR.getText().toString().trim();
                String passwordR = eTextPasswordR.getText().toString().trim();
                final String firstR = firstName.getText().toString().trim();
                final String nameR = name.getText().toString().trim();
                String dateR = btnDate.getText().toString().trim();

                if(TextUtils.isEmpty(nameR)){
                    //last name is empty
                    Snackbar.make(view, "Please enter the last name.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(firstR)){
                    //first name is empty
                    Snackbar.make(view, "Please enter the first name.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(emailR)){
                    //email is empty
                    Snackbar.make(view, "Please enter the email.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (dateR.equals("Enter your birth date")){
                    //birth date is empty
                    Snackbar.make(view, "Please enter the birth date.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(passwordR)){
                    //password is empty
                    Snackbar.make(view, "Please enter the password.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(emailR).matches())
                {
                    //email is invalid format
                    Snackbar.make(view, "Please enter a valid email address.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                mProgressDialog = new ProgressDialog(getActivity());
                mFirebaseAuth = FirebaseAuth.getInstance();

                //if the fields all filled up
                mProgressDialog.setMessage("Registering user...");
                mProgressDialog.show();

                mFirebaseAuth.createUserWithEmailAndPassword(emailR,passwordR)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProgressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    //if registration is complete
                                    Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();

                                    //get current userID
                                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                    String uid = user.getUid();

                                    mDatabaseReference.child(uid).child("firstName").setValue(firstR);
                                    mDatabaseReference.child(uid).child("lastName").setValue(nameR);
                                    mDatabaseReference.child(uid).child("birthDate").setValue(bDay);
                                    mDatabaseReference.child(uid).child("birthMonth").setValue(bMonth);
                                    mDatabaseReference.child(uid).child("birthYear").setValue(bYear);
                                    mDatabaseReference.child(uid).child("gender").setValue(item);

                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, new SignInFragment()).addToBackStack(null).commit();
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

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String date = "Birth date : " + dayOfMonth + " - " + month + " - " + year;
        bDay = String.valueOf(dayOfMonth);
        bMonth = String.valueOf(month);
        bYear = String.valueOf(year);
        btnDate.setText(date);
    }
}
