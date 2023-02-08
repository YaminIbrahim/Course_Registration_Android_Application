package com.example.courserms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class FindUsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    Button btnFindUs;

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
        setContentView(R.layout.activity_find_us);

        //Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_findUs);
        navigationView = (NavigationView) findViewById(R.id.navigationFindUs);
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
                        startActivity(new Intent(FindUsActivity.this, HomeActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_map:
                        Log.i("MENU_DRAWER_TAG", "Find Us item is clicked");
                        //startActivity(new Intent(FindUsActivity.this, FindUsActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_about:
                        Log.i("MENU_DRAWER_TAG", "About Us item is clicked");
                        startActivity(new Intent(FindUsActivity.this, AboutActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_contact:
                        Log.i("MENU_DRAWER_TAG", "Contact Us item is clicked");
                        startActivity(new Intent(FindUsActivity.this, ContactActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_logout:
                        Log.i("MENU_DRAWER_TAG", "Logout item is clicked");
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(FindUsActivity.this, MainActivity.class));
                        Toast.makeText(FindUsActivity.this, "Successfully Logout!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return false;
            }
        });

        btnFindUs = (Button) findViewById(R.id.btnFind);

        btnFindUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindUsActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}