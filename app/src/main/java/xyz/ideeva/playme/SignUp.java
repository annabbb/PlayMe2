package xyz.ideeva.playme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mylibrary.BaseAuthentication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends BaseAuthentication implements View.OnClickListener{

    private Button button;
    private EditText email;
    private EditText password;
    private EditText password2;
    private EditText name;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        button = (Button) findViewById(R.id.button);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        password2 = (EditText) findViewById(R.id.password2);

        progressDialog = new ProgressDialog(this);

        button.setOnClickListener(this);

    }

    private void signUp(String email, String password, String password2){
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        progressDialog.show();

        if (password == password2) {
            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                finish();
                                startActivity(new Intent(getApplicationContext(), HomePage.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUp.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            progressDialog.hide();
                        }
                    });
        }

    }

    private boolean validateForm() {
        boolean valid = true;

        String mName = name.getText().toString();
        if (TextUtils.isEmpty(mName)) {
            name.setError("Required.");
            valid = false;
        }
        else {
            name.setError(null);
        }

        String mEmail = email.getText().toString();
        if (TextUtils.isEmpty(mEmail)) {
            email.setError("Required.");
            valid = false;
        }
        else {
            email.setError(null);
        }

        String mPassword = password.getText().toString();
        if (TextUtils.isEmpty(mPassword)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }

        String mPassword2 = password2.getText().toString();
        if (TextUtils.isEmpty(mPassword2)) {
            password2.setError("Required.");
            valid = false;
        }
        else {
            password2.setError(null);
        }
        return valid;
    }


    @Override
    public void onClick(View view) {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        String userPassword2 = password2.getText().toString().trim();
        signUp(userEmail, userPassword, userPassword2);
    }
}
