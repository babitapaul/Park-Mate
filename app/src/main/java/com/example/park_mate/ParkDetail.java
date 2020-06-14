package com.example.park_mate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

public class ParkDetail extends AppCompatActivity {

    ImageView parkimage,mypic;
    EditText msgpost;
    Button postintime;
    ProgressBar pb;
    int parkobjectpoition;
    TextView name,country,state,city,type,address;
    ArrayList<Park> parks;
    Session sts;
    String imageurl;
    String Parkname;
    private DatabaseReference databaserefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_detail);
        pb=(ProgressBar)findViewById(R.id.pbloading);


        pb.setVisibility(View.GONE);
        sts=new Session(getApplicationContext());
        name=(TextView)findViewById(R.id.parkname);
        country=(TextView)findViewById(R.id.parkcountry);
        state=(TextView)findViewById(R.id.parkstate);
        city=(TextView)findViewById(R.id.parkcity);
        type=(TextView)findViewById(R.id.parktype);
        address=(TextView)findViewById(R.id.parkaddress);
        postintime=(Button)findViewById(R.id.postintimeline);
        msgpost=(EditText)findViewById(R.id.postit);
        parkimage=(ImageView) findViewById(R.id.parkimg);
        mypic=(ImageView) findViewById(R.id.mypic);
        if(sts.getimageurl().isEmpty()){}else{Picasso.get().load(sts.getimageurl()).into(parkimage);}
        Intent intent = getIntent();
        Picasso.get().load(intent.getStringExtra("parkimageurl")).into(parkimage);
        imageurl=intent.getStringExtra("parkimageurl");
        country.setText("Country :- "+intent.getStringExtra("parkcountry"));
        state.setText("State :- "+intent.getStringExtra("parkstate"));
        city.setText("City :- "+intent.getStringExtra("parkcity"));
        type.setText("Type :- "+intent.getStringExtra("parktype"));
        address.setText("Address :- "+intent.getStringExtra("parkaddress"));
        Parkname=intent.getStringExtra("parkname");
        name.setText("Park Name :- "+intent.getStringExtra("parkname"));


        System.out.println(parks);
        postintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(msgpost.getText().toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Write Somthing First!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();

                    return;
                }

                postinyourtimeline();
            }
        });

    }
    void postinyourtimeline()
    {
        pb.setVisibility(View.VISIBLE);
        databaserefrence= FirebaseDatabase.getInstance().getReference("UserTimeline");
        String keyid=databaserefrence.push().getKey();
        String mydatetime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Timeline s=new Timeline(keyid,Parkname.toString(),sts.getname(),sts.getusename(),city.getText().toString(),address.getText().toString(),msgpost.getText().toString(),mydatetime,imageurl,sts.getimageurl(),sts.getmobile());

        databaserefrence.child(keyid).setValue(s);
        Toast toast=Toast.makeText(getApplicationContext(),"This Park Posted in Your Timeline SuccessFully..",Toast.LENGTH_SHORT);
        toast.setMargin(50,50);
        toast.show();
        pb.setVisibility(View.GONE);
        startActivity(new Intent(this,Userwelcome.class));
    }

}
