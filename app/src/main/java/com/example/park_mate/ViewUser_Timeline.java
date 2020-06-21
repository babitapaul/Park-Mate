package com.example.park_mate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import java.util.Calendar;

public class ViewUser_Timeline extends AppCompatActivity {

    ImageView userpic;
    TextView name,city,gender;
    Button writereview,block,message,frdreq;
    String Useremailid,userimageurl,mpname;
    ArrayList<Timeline> sts;
    RecyclerView recyclerView;
    Session st;
    ProgressBar pb;
    String chatid;
    private DatabaseReference reference;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user__timeline);
        pb=(ProgressBar)findViewById(R.id.pbloading);
        userpic=(ImageView)findViewById(R.id.pic);
        writereview=(Button)findViewById(R.id.writereview);
        frdreq=(Button)findViewById(R.id.Sendfriendrequest);
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
        chatid=intent.getStringExtra("freqid");
        recyclerView=(RecyclerView)findViewById(R.id.viewtimelinerecyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        block=(Button)findViewById(R.id.block);
        message=(Button)findViewById(R.id.message);
        message.setClickable(false);
        writereview.setClickable(false);
        block.setClickable(false);
        recyclerView.setVisibility(View.GONE);
        check_already_setrequest();
        check_past_anyrequest();
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Send_Message.class).putExtra("toemailid",Useremailid).putExtra("toname",mpname).putExtra("toimage",userimageurl));
            }
        });
        frdreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sendfriendrequest();
            }
        });
        if(userimageurl.trim().isEmpty()) {
            Picasso.get().load(R.drawable.defultuser).into(userpic);
        }
        else
        {
            Picasso.get().load(userimageurl).into(userpic);
        }
        fetchtimelineofuser();
        writereview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),WriteReview.class).putExtra("profilereviewemailid",Useremailid).putExtra("username",intent.getStringExtra("username")));
            }
        });
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   blockuser();
                final AlertDialog.Builder builder = new AlertDialog.Builder(ViewUser_Timeline.this,R.style.AlertDialogStyle);

                builder.setMessage("this is message");
                builder.setTitle("Block");

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to Block This User ?");
                //This will not allow to close dialogbox until user selects an option
                builder.setCancelable(false);

                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "Please Write a Reason ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Block_Reason.class).putExtra("profilereviewemailid",Useremailid).putExtra("username",intent.getStringExtra("username")).putExtra("userimageurl",userimageurl));

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                //alert.setTitle("AlertDialogExample");
                alert.show();
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
                View_User_TimeLine_List_adpater view_user_timeLine_list_adpater=new View_User_TimeLine_List_adpater(getApplicationContext(),sts);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(view_user_timeLine_list_adpater);
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    void  Sendfriendrequest()
    {
        reference = FirebaseDatabase.getInstance().getReference("Friend Request");

        String requestid=reference.push().getKey();
        Friend_Request friend_request=new Friend_Request(st.getusename(),Useremailid,st.getname(),mpname,st.getimageurl(),userimageurl,"Pending",requestid,city.getText().toString(),gender.getText().toString());

        reference.child(requestid).setValue(friend_request);

        Toast toast = Toast.makeText(getApplicationContext(), "Friend Request Send SuccessFully", Toast.LENGTH_SHORT);
        toast.setMargin(50,50);
        toast.show();
        frdreq.setText("Friend Request Sent");
    }

    void check_already_setrequest()
    {
        pb.setVisibility(View.VISIBLE);

        sts=new ArrayList<Timeline>();
        reference= FirebaseDatabase.getInstance().getReference("Friend Request");
        Query query=reference.orderByChild("senderemailid").equalTo(st.getusename());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {


                    Friend_Request us=dataSnapshot1.getValue(Friend_Request.class);
                    if(us.getFromemailid().equals(Useremailid) && us.getStatus().equals("Pending"))
                    {
                        frdreq.setText("Friend Request Already sent");
                        message.setClickable(false);
                        writereview.setClickable(false);
                        block.setClickable(false);
                        recyclerView.setVisibility(View.GONE);
                        frdreq.setVisibility(View.GONE);
                        message.setVisibility(View.GONE);
                        writereview.setVisibility(View.GONE);
                        block.setVisibility(View.GONE);
                    }
                    else if(us.getFromemailid().equals(Useremailid) && us.getStatus().equals("Approved"))
                    {
                        message.setClickable(true);
                        writereview.setClickable(true);
                        block.setClickable(true);
                        recyclerView.setVisibility(View.VISIBLE);
                        frdreq.setVisibility(View.GONE);
                        message.setVisibility(View.VISIBLE);
                        writereview.setVisibility(View.VISIBLE);
                        block.setVisibility(View.VISIBLE);
                    }

                }

                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    void check_past_anyrequest()
    {
        pb.setVisibility(View.VISIBLE);

        reference= FirebaseDatabase.getInstance().getReference("Friend Request");
        Query query=reference.orderByChild("requestid").equalTo(chatid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {


                    Friend_Request us=dataSnapshot1.getValue(Friend_Request.class);
                    if(us.getStatus().equals("Pending"))
                    {
                        frdreq.setText("Friend Request Already sent");
                        message.setClickable(false);
                        writereview.setClickable(false);
                        block.setClickable(false);
                        recyclerView.setVisibility(View.GONE);
                        frdreq.setVisibility(View.GONE);
                        message.setVisibility(View.GONE);
                        writereview.setVisibility(View.GONE);
                    }
                    else
                    {
                        message.setClickable(true);
                        writereview.setClickable(true);
                        block.setClickable(true);
                        message.setVisibility(View.VISIBLE);
                        writereview.setVisibility(View.VISIBLE);
                        block.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        frdreq.setVisibility(View.GONE);
                    }

                }

                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
