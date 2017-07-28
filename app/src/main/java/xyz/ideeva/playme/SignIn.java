package xyz.ideeva.playme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.BaseAuthentication;
import com.example.mylibrary.EditTextWithIcon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignIn extends BaseAuthentication implements View.OnClickListener {

    private Button button;
    private EditTextWithIcon email;
    private EditTextWithIcon password;
    private TextView bottomText;
    private TextView middleText;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        firebaseAuth = FirebaseAuth.getInstance();

        //if(firebaseAuth.getCurrentUser() != null){      //if the user is signed in
           // startActivity(new Intent(getApplicationContext(), HomePage.class));
           // finish();
        //}

        if(!isNetworkAvailable()){
            Toast.makeText(SignIn.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        button = (Button) findViewById(R.id.button);
        email = (EditTextWithIcon) findViewById(R.id.email);
        password = (EditTextWithIcon) findViewById(R.id.password);
        bottomText = (TextView) findViewById(R.id.bottomText);
        middleText  = (TextView) findViewById(R.id.middleText);

        progressDialog = new ProgressDialog(this);

        button.setOnClickListener(this);
        bottomText.setOnClickListener(this);
        middleText.setOnClickListener(this);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        progressDialog.show();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomePage.class));
                        }
                        else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignIn.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String mEmail = email.getText().toString();
        if (TextUtils.isEmpty(mEmail)) {
            email.showError("Required");
            valid = false;
        }
        else {
            email.showError(null);
        }

        String mPassword = password.getText().toString();
        if (TextUtils.isEmpty(mPassword)) {
            password.showError("Required");
            valid = false;
        } else {
            password.showError(null);
        }

        if(!isNetworkAvailable()){
            Toast.makeText(SignIn.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        return valid;
    }


    private void sendPassword(String email) {
        if (!validateEmail()) {
            return;
        }


        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(SignIn.this, "Email sent.",
                                    Toast.LENGTH_SHORT).show();
                        } else{
                            Log.w(TAG, "Failed to sent email.", task.getException());
                            Toast.makeText(SignIn.this, "Failed to sent email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private boolean validateEmail(){
        boolean valid = true;

        String mEmail = email.getText().toString();
        if (TextUtils.isEmpty(mEmail)) {
            email.showError("Required");
            valid = false;
        }
        else {
            email.showError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {

        if (v == button){
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();
            signIn(userEmail, userPassword);
        }

        if(v == bottomText){
            progressDialog.show();
            finish();
            startActivity(new Intent(this, SignUp.class));
            progressDialog.dismiss();
        }

        if(v == middleText){
            String userEmail = email.getText().toString();
            sendPassword(userEmail);
        }
    }

}
