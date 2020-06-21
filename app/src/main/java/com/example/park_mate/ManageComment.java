package com.example.park_mate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ManageComment extends AppCompatActivity {

    String Timlinekey;

    ImageView currentuserimage;

    RecyclerView recyclerView;

    ArrayList<CommentPost> sjs;
    Manage_Comment_Adpater manage_comment_adpater;
    private DatabaseReference databaserefrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_comment);

        Intent intent = getIntent();
        Timlinekey=intent.getStringExtra("currentimelinekey");
        currentuserimage=(ImageView)findViewById(R.id.mypic);



        recyclerView=(RecyclerView)findViewById(R.id.mngcmnt);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        fetchcomment();


    }

    void fetchcomment()
    {

        sjs=new ArrayList<CommentPost>();
        databaserefrence= FirebaseDatabase.getInstance().getReference("Comment Post");
        Query query=databaserefrence.orderByChild("wallpostid").equalTo(Timlinekey);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                    System.out.println(dataSnapshot);
                    CommentPost us=dataSnapshot1.getValue(CommentPost.class);
                    sjs.add(us);

                }

                manage_comment_adpater=new Manage_Comment_Adpater(getApplicationContext(),sjs);

                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter( manage_comment_adpater);
                manage_comment_adpater.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
