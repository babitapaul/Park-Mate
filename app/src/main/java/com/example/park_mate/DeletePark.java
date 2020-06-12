package com.example.park_mate;

import androidx.appcompat.app.AppCompatActivity;

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

public class DeletePark extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    Spinner Parklist;
    TextView loading;
    Button ParkDelete;
    final List<String> parknamelist = new ArrayList<String>();
    private FirebaseDatabase database;
    private ProgressBar pgsBar;

    private DatabaseReference fDatabaseRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_park);
        pgsBar=(ProgressBar)findViewById(R.id.pbloading);
        loading=(TextView)findViewById(R.id.loading);
        ParkDelete=(Button)findViewById(R.id.parkdelete);
    /*    adapter = ArrayAdapter.createFromResource(
                this,
                R.array.parklist_array,
                R.layout.color_spinner_layout
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        Parklist.setAdapter(adapter); */
        Parklist=(Spinner)findViewById(R.id.parklist);
        database = FirebaseDatabase.getInstance();
        fDatabaseRoot = database.getReference("ParkRecord");
        Retrieve_Park_Names();

        ParkDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                String parkname=Parklist.getSelectedItem().toString();
                deletePark(parkname);
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

    void deletePark(String ParkName)
    {
        Query query = fDatabaseRoot.orderByChild("parkname").equalTo(ParkName);
        query .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
                Toast toast = Toast.makeText(getApplicationContext(), "Park Delete Successfully...", Toast.LENGTH_SHORT);
                toast.setMargin(50, 50);
                toast.show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }
}