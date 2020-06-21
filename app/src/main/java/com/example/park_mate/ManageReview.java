package com.example.park_mate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ManageReview extends AppCompatActivity {

    Manage_Profile_Review_Read_adpter manage_profile_review_read_adpter;

    ImageView currentuserimage;

    RecyclerView recyclerView;
    ProgressBar pb;
    String userrevireemailid;
    private DatabaseReference databaserefrence;

    ArrayList<ProfileReview> sjs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_review);

        Intent intent = getIntent();

        userrevireemailid=intent.getStringExtra("profilereviewemailid");

        currentuserimage=(ImageView)findViewById(R.id.mypic);


        pb=(ProgressBar)findViewById(R.id.pbloading);
        pb.setVisibility(View.GONE);

        recyclerView=(RecyclerView)findViewById(R.id.mngr);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ReviewFetch();


    }

    void  ReviewFetch()
    {
        pb.setVisibility(View.VISIBLE);

        sjs=new ArrayList<ProfileReview>();
        databaserefrence= FirebaseDatabase.getInstance().getReference("Profile review");
        Query query=databaserefrence.orderByChild("useremailidaboutreview").equalTo(userrevireemailid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                    System.out.println(dataSnapshot);
                    ProfileReview us=dataSnapshot1.getValue(ProfileReview.class);
                    sjs.add(us);

                }

                manage_profile_review_read_adpter=new Manage_Profile_Review_Read_adpter(getApplicationContext(),sjs);

                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(manage_profile_review_read_adpter);
                manage_profile_review_read_adpter.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
