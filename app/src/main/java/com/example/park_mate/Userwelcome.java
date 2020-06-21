package com.example.park_mate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Userwelcome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,manageuserfragment.onFragmentBtnSelected {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ImageView userimage;
    TextView username;
    Session session;
    private ArrayList<registration> Information;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userwelcome);
        toolbar = findViewById(R.id.usertoolbar);
        setSupportActionBar(toolbar);

        session=new Session(getApplicationContext());
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.usernavigationView);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        username=(TextView)header.findViewById(R.id.username);

        userimage=(ImageView)header .findViewById(R.id.userlogo);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();


        //load default fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.user_container_fragment,new UserTimline_fragment());
        fragmentTransaction.commit();
        getprofileinfo();


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
        if(menuItem.getItemId()== R.id.friendlist){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.user_container_fragment, new Friendlist());
            fragmentTransaction.commit();

        }
        if(menuItem.getItemId()== R.id.pendingfriendlist){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.user_container_fragment, new fragement_pending_friendrequest());
            fragmentTransaction.commit();

        }
        if(menuItem.getItemId()== R.id.Notification){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.user_container_fragment, new Notification());
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
            finish();
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
    void getprofileinfo()
    {
        Information=new ArrayList<registration>();
        reference= FirebaseDatabase.getInstance().getReference("users");
        Query query=reference.orderByChild("emailid").equalTo(session.getusename());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    //  System.out.println("value is "+dataSnapshot1.getChildren().toString());
                    registration us = dataSnapshot1.getValue(registration .class);
                    Information.add(us);

                    if(Information.size()>0)
                    {
                        session.setname(Information.get(0).getUsername());
                        session.setgender(Information.get(0).getGender());
                        session.setaboutme(Information.get(0).getAboutme());
                        session.setStatus(Information.get(0).getStatus());
                        session.setimageurl(Information.get(0).getImageurl());
                        session.setmobilenb(Information.get(0).getMobileno());
                        session.setAddress(Information.get(0).getAddress());
                        session.setvisiblity(Information.get(0).getStatus());
                        Set<String> iinds = new HashSet<String>(Information.get(0).getSecondsurvey());
                        session.setsecondsurvey(iinds);
                        Set<String> iiird = new HashSet<String>(Information.get(0).getusersurvey());
                        session.setparksurvey(iiird);
                        username.setText("Welcome "+session.getname().toUpperCase());

                        if(session.getimageurl().trim().isEmpty()) {

                        }
                        else {
                            Picasso.get().load(session.getimageurl()).into(userimage);
                        }
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
