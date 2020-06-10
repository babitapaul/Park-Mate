package com.example.park_mate;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Add_Survery extends AppCompatActivity {


    TextView ques,mylist;
    EditText optns;
    Button addnow,finshs;
    String myquestion;
    List<String> optn;
    private ProgressBar pgsBar;
    private DatabaseReference databaserefrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__survery);
        optn=new ArrayList<>();
        Intent intent = getIntent();
        myquestion= intent.getStringExtra("Question");
        ques=(TextView)findViewById(R.id.survey_question);
        mylist=(TextView)findViewById(R.id.mylist);
        ques.setText(myquestion);
        optns=(EditText)findViewById(R.id.options);

        finshs=(Button)findViewById(R.id.addQuestion);
        addnow=(Button)findViewById(R.id.addOptions);
        pgsBar = (ProgressBar) findViewById(R.id.pbloading);
        pgsBar.setVisibility(View.GONE);
        addnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgsBar.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(optns.getText().toString().trim()))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Survey  Options!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    pgsBar.setVisibility(View.GONE);
                    return;
                }
                optn.add(optns.getText().toString().trim());
                optns.setText(null);
                mylist.setText(optn.toString());
                pgsBar.setVisibility(View.GONE);
                if(optn.size()>2)
                {
                    finshs.setVisibility(View.VISIBLE);
                }
                //
            }
        });
        finshs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgsBar.setVisibility(View.VISIBLE);
                Addsurverynow(ques.getText().toString().trim(),optn);
                pgsBar.setVisibility(View.GONE);
            }
        });

    }

    void Addsurverynow(String Question,List<String> Option)
    {
        databaserefrence= FirebaseDatabase.getInstance().getReference("Survey");
        Survey add_survey_t=new Survey(Question,Option);
        String movieid=databaserefrence.push().getKey();
        databaserefrence.child(movieid).setValue(add_survey_t);
        Toast toast=Toast.makeText(getApplicationContext(),"Survey Question Added Successfully ",Toast.LENGTH_SHORT);
        toast.setMargin(50,50);
        toast.show();


    }
}
