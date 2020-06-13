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

public class DeleteSurvey extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    Spinner Questionlist;
    TextView loading;
    Button QuestionDelete;
    final List<String> queslist = new ArrayList<String>();
    private FirebaseDatabase database;
    private ProgressBar pgsBar;

    private DatabaseReference fDatabaseRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_survey);
        pgsBar=(ProgressBar)findViewById(R.id.pbloading);
        loading=(TextView)findViewById(R.id.loading);
        QuestionDelete=(Button)findViewById(R.id.myquestiondelete);
        Questionlist=(Spinner)findViewById(R.id.questionlist);
        database = FirebaseDatabase.getInstance();
        fDatabaseRoot = database.getReference("Survey");
        Retrieve_Questions();
        QuestionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                String qusname=Questionlist.getSelectedItem().toString();
                DeleteQuestion(qusname);
            }
        });

    }
    void DeleteQuestion(String que)
    {
        Query query = fDatabaseRoot.orderByChild("question").equalTo(que);
        query .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
                Toast toast = Toast.makeText(getApplicationContext(), "Question Delete Successfully...", Toast.LENGTH_SHORT);
                toast.setMargin(50, 50);
                toast.show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }
    void Retrieve_Questions()
    {
        Query query = fDatabaseRoot.orderByKey();
        queslist.add(0,"Select Question");
        fDatabaseRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot questionrecord : dataSnapshot.getChildren()){

                    queslist.add(questionrecord.child("question").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
        adapter = new ArrayAdapter<String>(this,R.layout.color_spinner_layout,queslist);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        Questionlist.setAdapter(adapter);
        pgsBar.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        Questionlist.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                if(parent.getItemAtPosition(pos).equals("Select Question"))
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