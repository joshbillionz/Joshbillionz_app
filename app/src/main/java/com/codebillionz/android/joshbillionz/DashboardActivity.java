package com.codebillionz.android.joshbillionz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.codebillionz.android.joshbillionz.models.Account;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    FirebaseUser user ;

    TextView firstNameTextView, lastNameTextView , emailTextView, welcomeTextView;


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        initializeWidgets();
        displayDashboard();


        mAuth = FirebaseAuth.getInstance();
    }



    private void displayDashboard(){

    }
    private void initializeWidgets(){

    }
    private void launchLoginPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //overrides

    @Override
    protected void onStart() {
        super.onStart();
        user= mAuth.getCurrentUser();
        if( user==null){
            Intent intent = new Intent(this , LoginActivity.class);
            startActivity(intent);
            finish();

        }
        else{
            firstNameTextView = findViewById(R.id.firstNameTextView);
            lastNameTextView = findViewById(R.id.lastNameTextView);
            emailTextView = findViewById(R.id.emailTextView);
            welcomeTextView = findViewById(R.id.welcomeTextView);

            updateWidgetsFromObject();


        }
    }


    private void updateWidgetsFromObject(){

        FirebaseDatabase db = FirebaseDatabase.getInstance();

        // Attach a listener to read the data at our posts reference

        db.getReference(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Account account =(dataSnapshot.getValue(Account.class));
                lastNameTextView.setText(account.getName().getLastName());
                firstNameTextView.setText(account.getName().getFirstName());
                emailTextView.setText(account.getEmail());
                welcomeTextView.setText("Welcome " + account.getName().getFullNameString() + ". This is dashboard you get from task 2 of my HNG internship.. with time you will get an upgraded one :)");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        super.onCreateOptionsMenu(menu);
        inflater.inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_item:
                mAuth.signOut();
                launchLoginPage();
                return super.onOptionsItemSelected(item);
            case R.id.restart_item:
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                return super.onOptionsItemSelected(item);
            default: return super.onOptionsItemSelected(item);

        }
    }


}
