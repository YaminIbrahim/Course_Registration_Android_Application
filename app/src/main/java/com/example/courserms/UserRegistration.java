package com.example.courserms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegistration extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView linkLogin, registerUser;
    private EditText fullName, staffid, email, password;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        linkLogin = (TextView) findViewById(R.id.linkLogin);
        linkLogin.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.register);
        registerUser.setOnClickListener(this);

        fullName = (EditText) findViewById(R.id.fullname);
        staffid = (EditText) findViewById(R.id.staffid);
        email = (EditText) findViewById(R.id.regisEmail);
        password = (EditText) findViewById(R.id.regisPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linkLogin:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.register:
                registerUser();
        }
    }

    private void registerUser() {
        String emailx = email.getText().toString().trim();
        String staffidx = staffid.getText().toString().trim();
        String fullNamex = fullName.getText().toString().trim();
        String passwordx = password.getText().toString().trim();

        if (fullNamex.isEmpty()){
            fullName.setError("Full name is required!");
            fullName.requestFocus();
            return;
        }

        if (staffidx.isEmpty()){
            staffid.setError("Staff ID is required!");
            staffid.requestFocus();
            return;
        }

        if (emailx.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailx).matches()){
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }


        if (passwordx.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }

        if (passwordx.length() < 6){
            password.setError("Mininum character should be 6 characters!");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailx, passwordx)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullNamex, emailx, staffidx);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(UserRegistration.this, "Registered Successully!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);

                                                //redirect to login page
                                                Intent intent = new Intent(UserRegistration.this, MainActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(UserRegistration.this, "Registration Failed!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                            
                        } else {
                            Toast.makeText(UserRegistration.this, "Registration Failed!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}