package com.example.park_mate;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        pgsBar = (ProgressBar) findViewById(R.id.pbloading);
        loading = (TextView) findViewById(R.id.loading);
        ParkDelete = (Button) findViewById(R.id.parkdelete);
        Parklist = (Spinner) findViewById(R.id.parklist);
        database = FirebaseDatabase.getInstance();

    }
}