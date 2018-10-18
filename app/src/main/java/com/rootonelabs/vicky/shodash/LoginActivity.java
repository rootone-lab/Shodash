package com.rootonelabs.vicky.shodash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {



    // UI references.
    private MaterialEditText mEmailView;
    private MaterialEditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private  Button login;
    private TextView signUpLink;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.


        mEmailView = (MaterialEditText) findViewById(R.id.username);
        mPasswordView = (MaterialEditText) findViewById(R.id.password);
        login = (Button)findViewById(R.id.loginButton);
        signUpLink = (TextView)findViewById(R.id.signUpLink);


        signUpLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUp.class));
            }
        });

        //setting firebase database

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = mFirebaseAuth.getCurrentUser();

                if(firebaseUser!=null){
                    //next activity

                    startActivity(new Intent(LoginActivity.this, Home.class));

                }else{
                    Snackbar.make(findViewById(android.R.id.content), "Please Login", Snackbar.LENGTH_SHORT).show();
                }
            }
        };

        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEmailView.getText().toString().trim().isEmpty()&&!mPasswordView.getText().toString().trim().isEmpty()){
                    final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"", "Logging In...", true);
                    mFirebaseAuth.signInWithEmailAndPassword(mEmailView.getText().toString().trim(),mPasswordView.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressDialog.dismiss();
                            Snackbar.make(findViewById(android.R.id.content), "Logged In Successfully", Snackbar.LENGTH_LONG).show();
                            //Add Intent To Next Activity
                            startActivity(new Intent(LoginActivity.this, Home.class));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Snackbar.make(findViewById(android.R.id.content), "Sign In Failed", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }else if(!mEmailView.getText().toString().trim().isEmpty()&&mPasswordView.getText().toString().trim().isEmpty()){
                    mPasswordView.setError("Password Is Required");
                }else if(mEmailView.getText().toString().trim().isEmpty()&&!mPasswordView.getText().toString().trim().isEmpty()){
                    mEmailView.setError("Email is Required");
                }else{
                    mPasswordView.setError("Password Is Required");
                    mEmailView.setError("Email is Required");
                }
            }
        });
    }

    private void onSignOutCleanUp() {
    }

    private void onSignedInInitialized() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        super.onPause();
    }
}

