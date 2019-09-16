package com.codebillionz.android.joshbillionz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginButton;
    TextView signupTextView;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        initializeWidgets();


    }

    private void initializeWidgets(){
        emailEditText = findViewById(R.id.email_edittextl);
        passwordEditText = findViewById(R.id.password_edittext);
        loginButton = findViewById(R.id.login_btn);
        signupTextView = findViewById(R.id.signup_textview);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();



                EditText[] fields ={emailEditText,passwordEditText};

                if(fieldsFilled(fields)){
                    login(email, password);
                }

            }
        });

        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSignupPage();

            }
        });
    }

    private void  login(String email , String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LOGIN ", "signInWithEmail:success");
                            launchDashboard();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LOGIN", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void launchDashboard(){
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
        finish();

    }
    private void launchSignupPage(){
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean fieldsFilled(EditText[] fields){
        for (EditText field : fields){
            if (field.getText().toString().length()<=0){
                field.setError("field must be filled");
                return false;
            }

            if((field.getId()==R.id.password_edittext)&&passwordEditText.getText().length()<6){
                field.setError(" password should contain at least 6 characters");
                return false;
            }

        }

        if(!isValidEmail(emailEditText.getText().toString())){
            emailEditText.setError("Invalid Email");
            return false;
        }
        return true;
    }

    private boolean isValidEmail(CharSequence target){
        if (target== null) return false;
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
