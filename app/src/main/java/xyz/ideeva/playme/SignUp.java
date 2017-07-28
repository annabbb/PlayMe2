package xyz.ideeva.playme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences.Editor;

import com.example.mylibrary.BaseAuthentication;
import com.example.mylibrary.EditTextWithIcon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends BaseAuthentication implements View.OnClickListener{

    private Button button;
    private EditTextWithIcon email;
    private EditTextWithIcon password;
    private EditTextWithIcon password2;
    private EditTextWithIcon name;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        button = (Button) findViewById(R.id.button);
        email = (EditTextWithIcon) findViewById(R.id.email);
        password = (EditTextWithIcon) findViewById(R.id.password);
        name = (EditTextWithIcon) findViewById(R.id.name);
        password2 = (EditTextWithIcon) findViewById(R.id.password2);

        progressDialog = new ProgressDialog(this);

        button.setOnClickListener(this);

        if(!isNetworkAvailable()){
            Toast.makeText(SignUp.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void signUp(String email, String password, String name){
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
        Editor editor = sharedPreferences.edit();
        editor.putString("Name", name);
        editor.putString("Email",email);
        editor.putString("Password",password);
        editor.apply();

        progressDialog.show();

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
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(SignUp.this, "User with this email already exists", Toast.LENGTH_SHORT).show();
                                }
                            }

                            progressDialog.dismiss();
                        }
                    });


    }

    private boolean validateForm() {
        boolean valid = true;

        String mName = name.getText().toString();
        if (TextUtils.isEmpty(mName)) {
            name.showError("Required");
            valid = false;
        }
        else {
            name.showError(null);
        }

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

        if (mPassword.length() < 6 && mPassword.length() > 0) {
            password.showError("Minimum 6 characters");
            valid = false;
        }

        String mPassword2 = password2.getText().toString();
        if (TextUtils.isEmpty(mPassword2)) {
            password2.showError("Required");
            valid = false;
        }
        else {
            password2.showError(null);
        }

        if(mPassword.length() >= 6 && mPassword2.length() > 0 && !mPassword.equals(mPassword2)) {
            password2.showError("Incorrect password");
            valid = false;
        }

        if(!isNetworkAvailable()){
            Toast.makeText(SignUp.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        return valid;
    }


    @Override
    public void onClick(View view) {
        String userEmail = email.getText().toString().trim();
        String userName = name.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        signUp(userEmail, userPassword, userName);
    }
}
