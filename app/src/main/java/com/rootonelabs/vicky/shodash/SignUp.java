package com.rootonelabs.vicky.shodash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;

    MaterialEditText email, password;
    Button signUpButton;

    private static final String TAG = "SignUp";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        email = (MaterialEditText)findViewById(R.id.email);
        password = (MaterialEditText)findViewById(R.id.password);
        signUpButton = (Button)findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!email.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()) {

                    final ProgressDialog progressDialog = ProgressDialog.show(SignUp.this, "", "Creating Your Account");
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressDialog.dismiss();

                            /*firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Snackbar.make(findViewById(android.R.id.content), "Email Verfiacation Sent", Snackbar.LENGTH_LONG);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Snackbar.make(findViewById(android.R.id.content), "Email Verfiacation Not Sent", Snackbar.LENGTH_LONG);
                                }
                            });*/

                            Snackbar.make(findViewById(android.R.id.content), "Account Creation Successful!", Snackbar.LENGTH_LONG).show();
                            startActivity(new Intent(SignUp.this, Home.class));
                            //Start Next Activity;
                            //if(firebaseUser.isEmailVerified()){

                            //}
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Snackbar.make(findViewById(android.R.id.content), "Account Creation Failed.", Snackbar.LENGTH_LONG).show();
                        }
                    });
                } else if (email.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()) {
                    email.setError("Email is Required");
                } else if (!email.getText().toString().trim().isEmpty() && password.getText().toString().trim().isEmpty()) {
                    password.setError("Password is Required");
                } else {
                    email.setError("Email is Required");
                    password.setError("Password is Required");
                }
            }
            });
    }
}
