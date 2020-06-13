package com.example.park_mate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class AddOption_Survey extends AppCompatActivity {
    TextView setquestionname,mylist;
    String getquestionname;
    EditText optns;
    Survey mm;
    Button addnow,finshs;
    private ProgressBar pgsBar;
    List<String> optn;
    private FirebaseDatabase database;
    private DatabaseReference fDatabaseRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_option__survey);
        mylist=(TextView)findViewById(R.id.mylist);
        pgsBar = (ProgressBar) findViewById(R.id.pbloading);
        pgsBar.setVisibility(View.GONE);
        optn=new ArrayList<>();
        Intent intent = getIntent();
        getquestionname = intent.getStringExtra("que");
        optns=(EditText)findViewById(R.id.options);
        addnow=(Button)findViewById(R.id.addOptions);
        finshs=(Button)findViewById(R.id.addQuestion);
        setquestionname=(TextView)findViewById(R.id.survey_question);
        setquestionname.setText(getquestionname);
        database = FirebaseDatabase.getInstance();
        fDatabaseRoot = database.getReference("Survey");
        retrive_Survey_Questions_Options(getquestionname);


        addnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(optns.getText().toString().trim()))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Survey  Options!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();

                    return;
                }
                optn.add(optns.getText().toString().trim());
                optns.setText(null);
                mylist.setText(optn.toString());
                if(optn.size()>2)
                {
                    finshs.setVisibility(View.VISIBLE);
                }

            }
        });
        finshs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgsBar.setVisibility(View.VISIBLE);
                update_option_Survey_Question(setquestionname.getText().toString().trim(),optn);
                pgsBar.setVisibility(View.GONE);
            }
        });
    }

    void update_option_Survey_Question(String Questionnam, final List<String> Option)
    {
        pgsBar.setVisibility(View.VISIBLE);
        final Query query = fDatabaseRoot.orderByChild("question").equalTo(Questionnam);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    String key = appleSnapshot.getKey();
                    //    update(key);
                    fDatabaseRoot.child(key).child("options").setValue(Option);


                }
                Toast toast=Toast.makeText(getApplicationContext(),"Question Option Updated Successfully",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                pgsBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAG, "onCancelled", databaseError.toException());
                pgsBar.setVisibility(View.GONE);
            }
        });




    }
    void retrive_Survey_Questions_Options(String getquestionname)
    {
        Query query = fDatabaseRoot.orderByKey();

        query = fDatabaseRoot.orderByChild("question").equalTo(getquestionname);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot surveySnapshot : dataSnapshot.getChildren()) {

                    mm=surveySnapshot.getValue(Survey.class);
                    optn=mm.getOptions();
                    System.out.println(optn);
                    mylist.setText(optn.toString());
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAG, "onCancelled", databaseError.toException());

            }
        });

    }
}