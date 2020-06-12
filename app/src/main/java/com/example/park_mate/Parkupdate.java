package com.example.park_mate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Parkupdate extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    Spinner Parklist;
    TextView loading;
    Button Parkupdate;
    final List<String> parknamelist = new ArrayList<String>();
    private FirebaseDatabase database;
    private ProgressBar pgsBar;

    private DatabaseReference fDatabaseRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkupdate);
        pgsBar=(ProgressBar)findViewById(R.id.pbloading);
        loading=(TextView)findViewById(R.id.loading);
        Parkupdate=(Button)findViewById(R.id.parkupdate);
        Parklist=(Spinner)findViewById(R.id.parklist);
        database = FirebaseDatabase.getInstance();
        fDatabaseRoot = database.getReference("ParkRecord");
        Retrieve_Park_Names();

        Parkupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                String parkname=Parklist.getSelectedItem().toString();
                startActivity(new Intent(Parkupdate.this,Parkupdation_final.class).putExtra("Parkname",parkname));

            }
        });


    }

    void Retrieve_Park_Names()
    {
        Query query = fDatabaseRoot.orderByKey();
        parknamelist.add(0,"Select Park Name");
        fDatabaseRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot parkrecord : dataSnapshot.getChildren()){

                    parknamelist.add(parkrecord.child("parkname").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
        adapter = new ArrayAdapter<String>(this,R.layout.color_spinner_layout,parknamelist);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        Parklist.setAdapter(adapter);
        pgsBar.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        Parklist.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                if(parent.getItemAtPosition(pos).equals("Select Park Name"))
                {

                }
                else
                {
                    String name=parent.getItemAtPosition(pos).toString();
                    Toast toast = Toast.makeText(getApplicationContext(), "Your Select "+name, Toast.LENGTH_SHORT);
                    toast.setMargin(50, 50);
                    toast.show();
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }



}
