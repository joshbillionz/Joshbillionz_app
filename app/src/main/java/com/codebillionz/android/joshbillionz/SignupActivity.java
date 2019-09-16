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

import com.codebillionz.android.joshbillionz.models.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    EditText firstNameET,lastNameET,emailET,passwordET;
    Button signUpButton;
    TextView loginTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        initializeWidgets();


    }


    public void signUp(final String firstName ,final String lastName,final String email,final String password ){





        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SIGNUP", "createUserWithEmail:success");
                            String uId = mAuth.getCurrentUser().getUid();
                            Account account = new Account(firstName,lastName,email, password,uId);
                            addNewAccount(account);
                            launchDashboard();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SIGNUP", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void initializeWidgets(){
        firstNameET= findViewById(R.id.firstname_edittext);
        lastNameET = findViewById(R.id.lastname_edittext);
        emailET = findViewById(R.id.emailEditTextS);
        passwordET = findViewById(R.id.password_edittextS);
        signUpButton = findViewById(R.id.signup_btn);
        loginTv=findViewById(R.id.login_tvS);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName=firstNameET.getText().toString();
                String lastName = lastNameET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                EditText fields[] = {firstNameET,lastNameET,emailET,passwordET};

                if (fieldsFilled(fields)){
                    signUp(firstName, lastName,email, password);
                }

            }
        });

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchLoginPage();
            }
        });
    }
    private void launchDashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void launchLoginPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void addNewAccount(Account account){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child(account.getuId()).setValue(account);
    }

    private boolean fieldsFilled(EditText[] fields){
        for (EditText field : fields){

            if (field.getText().toString().length()<=0){
                field.setError("field must be filled");
                return false;
            }
            if((field.getId()==R.id.password_edittextS)&&passwordET.getText().length()<6){
                field.setError("password should contain at least 6 characters");
                return false;
            }
        }

        if(!isValidEmail(emailET.getText().toString())){
            emailET.setError("Invalid Email");
            return false;
        }
        return true;
    }

    private boolean isValidEmail(CharSequence target){
        if (target== null) return false;
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
