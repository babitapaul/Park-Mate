package com.example.park_mate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class Userwelcome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,manageuserfragment.onFragmentBtnSelected {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userwelcome);
        toolbar = findViewById(R.id.usertoolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.usernavigationView);
        navigationView.setNavigationItemSelectedListener(this);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();


        //load default fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.user_container_fragment,new UserTimline_fragment());
        fragmentTransaction.commit();



    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);

        if(menuItem.getItemId()== R.id.Dashboard){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.user_container_fragment, new UserTimline_fragment());
            fragmentTransaction.commit();

        }

        if (menuItem.getItemId() == R.id.parklist) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.user_container_fragment, new Parklist_fragment());
            fragmentTransaction.commit();
        }
        if (menuItem.getItemId() == R.id.message) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.user_container_fragment, new Message());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId()== R.id.match){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.user_container_fragment, new MatchList());
            fragmentTransaction.commit();

        }
        if(menuItem.getItemId()== R.id.profile){

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.user_container_fragment, new Editprofile());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId()== R.id.block){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.user_container_fragment, new Blocklist());
            fragmentTransaction.commit();

        }
        if(menuItem.getItemId()== R.id.managereview){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.user_container_fragment, new MyReview_Read());
            fragmentTransaction.commit();

        }
        if(menuItem.getItemId()== R.id. Privacy){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.user_container_fragment, new Privacy());
            fragmentTransaction.commit();

        }

        if(menuItem.getItemId()== R.id.logout){

            startActivity(new Intent(getApplicationContext(),Login.class));

        }

        return true;

    }


    @Override
    public void onButtonSelected() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.user_container_fragment, new UserTimline_fragment());
        fragmentTransaction.commit();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
