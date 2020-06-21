package com.example.park_mate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Control_Profile extends AppCompatActivity {

    Button managereview;
    ImageView userpic;
    TextView name,city,gender;
    String Useremailid,userimageurl,mpname;
    ArrayList<Timeline> sts;
    RecyclerView recyclerView;
    Session st;
    ProgressBar pb;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control__profile);
        pb=(ProgressBar)findViewById(R.id.pbloading);
        managereview=(Button)findViewById(R.id.managereview);
        userpic=(ImageView)findViewById(R.id.pic);
        name=(TextView)findViewById(R.id.username);
        city=(TextView)findViewById(R.id.city);
        gender=(TextView)findViewById(R.id.gender);
        st=new Session(getApplicationContext());
        final Intent intent = getIntent();
        mpname=intent.getStringExtra("username");
        name.setText(intent.getStringExtra("username"));
        Useremailid=intent.getStringExtra("emailid");
        city.setText(intent.getStringExtra("usercity"));
        gender.setText(intent.getStringExtra("usergender"));
        userimageurl=intent.getStringExtra("userimageurl");
        recyclerView=(RecyclerView)findViewById(R.id.viewtimelinerecyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if(userimageurl.trim().isEmpty()) {
            Picasso.get().load(R.drawable.defultuser).into(userpic);
        }
        else
        {
            Picasso.get().load(userimageurl).into(userpic);
        }
        fetchtimelineofuser();
        managereview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ManageReview.class).putExtra("profilereviewemailid",Useremailid));
            }
        });
    }
    void fetchtimelineofuser()
    {
        pb.setVisibility(View.VISIBLE);

        sts=new ArrayList<Timeline>();
        reference= FirebaseDatabase.getInstance().getReference("UserTimeline");
        Query query=reference.orderByChild("emailid").equalTo(Useremailid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    System.out.println("key is "+dataSnapshot.getRef().push().getKey());

                    Timeline us=dataSnapshot1.getValue(Timeline.class);
                    sts.add(us);
                }
                Manage_User_TimeLine_List_adpater view_user_timeLine_list_adpater=new Manage_User_TimeLine_List_adpater(getApplicationContext(),sts);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(view_user_timeLine_list_adpater);
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
