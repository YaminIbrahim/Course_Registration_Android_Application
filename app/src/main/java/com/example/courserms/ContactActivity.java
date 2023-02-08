package com.example.courserms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ContactActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mMessageEditText;
    private Button mSendButton;

    //Drawer
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_contact);
        navigationView = (NavigationView) findViewById(R.id.navigationContact);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.menu_Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Log.i("MENU_DRAWER_TAG", "Search item is clicked");
                        startActivity(new Intent(ContactActivity.this, HomeActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_map:
                        Log.i("MENU_DRAWER_TAG", "Find Us item is clicked");
                        startActivity(new Intent(ContactActivity.this, GoogleMapActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_about:
                        Log.i("MENU_DRAWER_TAG", "About Us item is clicked");
                        startActivity(new Intent(ContactActivity.this, AboutActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_contact:
                        Log.i("MENU_DRAWER_TAG", "Contact Us item is clicked");
                        //startActivity(new Intent(ContactActivity.this, ContactActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_logout:
                        Log.i("MENU_DRAWER_TAG", "Logout item is clicked");
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(ContactActivity.this, MainActivity.class));
                        Toast.makeText(ContactActivity.this, "Successfully Logout!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return false;
            }
        });

        mNameEditText = findViewById(R.id.etName);
        mEmailEditText = findViewById(R.id.etEmail);
        mMessageEditText = findViewById(R.id.etMessage);
        mSendButton = findViewById(R.id.btnSend);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEditText.getText().toString();
                String email = mEmailEditText.getText().toString();
                String message = mMessageEditText.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(message)) {
                    sendMessage(name, email, message);
                } else {
                    Toast.makeText(ContactActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void sendMessage(String name, String email, String message) {
        //logic to send the email
        // Use the Android's built-in Intent to send the email
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"2020459282@student.uitm.edu.my"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Us Message from " + name);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Name: " + name + "\nEmail: " + email + "\n\nMessage:\n" + message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
            Toast.makeText(this, "Opening email application", Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactActivity.this, "No email client installed.", Toast.LENGTH_SHORT).show();
        }

        // Clear the input fields after sending the email
        mNameEditText.setText("");
        mEmailEditText.setText("");
        mMessageEditText.setText("");
    }
}