package com.example.mylibrary;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public abstract class BaseAuthentication extends FullscreenActivity {

    public final static String TAG = BaseAuthentication.class.getSimpleName();

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference root = database.getReference();

    DatabaseReference userTypeRoot;

    // [START declare_auth]
    public FirebaseAuth mAuth;
    // [END declare_auth]


    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ValueEventListener userValueEventListener;
    // [END declare_auth_listener]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeAuth();
        setupAuthListener();
    }

    public void initializeAuth() {
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }

    public void setupAuthListener() {
        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in: " + user.getUid());
                    userSignedIn(user);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    userSignedOut();
                }
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        removeAuthListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        if (userTypeRoot != null) {
            if (userValueEventListener != null) {
                userTypeRoot.addValueEventListener(userValueEventListener);
            }
        }
    }

    private void removeAuthListener() {
        if (mAuth != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

        if (userTypeRoot != null) {
            userTypeRoot.removeEventListener(userValueEventListener);
        }
    }

    @SuppressWarnings("unused")
    public void signOut() {
        mAuth.signOut();
    }

    @SuppressWarnings("unused")
    public void userSignedIn(FirebaseUser user) {
//        getUserObject(user);
    }

    public void userSignedOut() {
    }


}
