package com.example.park_mate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class SurveyFirst extends AppCompatActivity {

    Button b1;
    TextView back,Question;
    CheckBox op1,op2,op3,op4,op5,op6,op7,op8;
    ProgressBar pb;
    private DatabaseReference ref;
    private DatabaseReference refs;
    Session j;
    ArrayList<Survey> surveys;
    ArrayList<String>Surveyist;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_first);
        Surveyist=new ArrayList<String>();
        surveys=new ArrayList<Survey>();
        ref= FirebaseDatabase.getInstance().getReference("Survey");
        refs= FirebaseDatabase.getInstance().getReference("users");
        //  fetchquestion();

        j=new Session(getApplicationContext());
        Intent intent = getIntent();
        token= intent.getStringExtra("token");
        if(token.equals("0"))
        {
            surveyHistorycheck();
        }
        pb=(ProgressBar)findViewById(R.id.pbloading);
        b1=(Button)findViewById(R.id.b1);
        Question=(TextView)findViewById(R.id.Question);
        op1=(CheckBox)findViewById(R.id.op1);
        op2=(CheckBox)findViewById(R.id.op2);
        op3=(CheckBox)findViewById(R.id.op3);
        op4=(CheckBox)findViewById(R.id.op4);;
        op5=(CheckBox)findViewById(R.id.op5);
        op6=(CheckBox)findViewById(R.id.op6);
        op7=(CheckBox)findViewById(R.id.op7);
        op8=(CheckBox)findViewById(R.id.op8);
        fetchquestion();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Surveyist.removeAll(Surveyist);

                if(op1.isChecked())
                {
                    Surveyist.add(op1.getText().toString());
                }
                if(op2.isChecked())
                {
                    Surveyist.add(op2.getText().toString());
                }
                if(op3.isChecked())
                {
                    Surveyist.add(op3.getText().toString());
                }
                if(op4.isChecked())
                {
                    Surveyist.add(op4.getText().toString());
                }
                if(op5.isChecked())
                {
                    Surveyist.add(op5.getText().toString());
                }
                if(op6.isChecked())
                {
                    Surveyist.add(op6.getText().toString());
                }
                if(op7.isChecked())
                {
                    Surveyist.add(op7.getText().toString());
                }
                if(op8.isChecked())
                {
                    Surveyist.add(op8.getText().toString());
                }
                if(Surveyist.size()>0) {

                    Intent i = new Intent(SurveyFirst.this, SurveyTwo.class);

                    i.putExtra("SurveyFirstList", Surveyist);
                    startActivity(i);
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),  "Please Check At Least one!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
        });

        back=(TextView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SurveyFirst.this,Login.class);
                startActivity(intent);
            }
        });
    }
    void fetchquestion()
    {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {

                    Survey us=dataSnapshot1.getValue(Survey.class);
                    surveys.add(us);
                    Question.setText(surveys.get(0).getQuestion());
                    Question.setVisibility(View.VISIBLE);
                    int k=1;
                    if(surveys.get(0).getOptions().size()==2)
                    {
                        op1.setVisibility(View.VISIBLE);
                        op2.setVisibility(View.VISIBLE);
                        op3.setVisibility(View.GONE);
                        op4.setVisibility(View.GONE);
                        op5.setVisibility(View.GONE);
                        op6.setVisibility(View.GONE);
                        op7.setVisibility(View.GONE);
                        op1.setText(surveys.get(0).getOptions().get(0));
                        op2.setText(surveys.get(0).getOptions().get(1));
                        pb.setVisibility(View.GONE);
                        //  op3.setText(surveys.get(0).getOptions().get(2));
                    }
                    else if(surveys.get(0).getOptions().size()==3)
                    {
                        op1.setVisibility(View.VISIBLE);
                        op2.setVisibility(View.VISIBLE);
                        op3.setVisibility(View.VISIBLE);
                        op1.setText(surveys.get(0).getOptions().get(0));
                        op2.setText(surveys.get(0).getOptions().get(1));
                        op3.setText(surveys.get(0).getOptions().get(2));
                        pb.setVisibility(View.GONE);
                    }
                    else if(surveys.get(0).getOptions().size()==4)
                    {
                        op1.setVisibility(View.VISIBLE);
                        op2.setVisibility(View.VISIBLE);
                        op3.setVisibility(View.VISIBLE);
                        op4.setVisibility(View.VISIBLE);
                        op1.setText(surveys.get(0).getOptions().get(0));
                        op2.setText(surveys.get(0).getOptions().get(1));
                        op3.setText(surveys.get(0).getOptions().get(2));
                        op4.setText(surveys.get(0).getOptions().get(3));
                        pb.setVisibility(View.GONE);
                    }
                    else if(surveys.get(0).getOptions().size()==5)
                    {
                        op1.setVisibility(View.VISIBLE);
                        op2.setVisibility(View.VISIBLE);
                        op3.setVisibility(View.VISIBLE);
                        op4.setVisibility(View.VISIBLE);
                        op5.setVisibility(View.VISIBLE);
                        op1.setText(surveys.get(0).getOptions().get(0));
                        op2.setText(surveys.get(0).getOptions().get(1));
                        op3.setText(surveys.get(0).getOptions().get(2));
                        op4.setText(surveys.get(0).getOptions().get(3));
                        op5.setText(surveys.get(0).getOptions().get(4));
                        pb.setVisibility(View.GONE);
                    }
                    else if(surveys.get(0).getOptions().size()==6)
                    {
                        op1.setVisibility(View.VISIBLE);
                        op2.setVisibility(View.VISIBLE);
                        op3.setVisibility(View.VISIBLE);
                        op4.setVisibility(View.VISIBLE);
                        op5.setVisibility(View.VISIBLE);
                        op6.setVisibility(View.VISIBLE);
                        op1.setText(surveys.get(0).getOptions().get(0));
                        op2.setText(surveys.get(0).getOptions().get(1));
                        op3.setText(surveys.get(0).getOptions().get(2));
                        op4.setText(surveys.get(0).getOptions().get(3));
                        op5.setText(surveys.get(0).getOptions().get(4));
                        op6.setText(surveys.get(0).getOptions().get(5));
                        pb.setVisibility(View.GONE);
                    }
                    else if(surveys.get(0).getOptions().size()==7)
                    {
                        op1.setVisibility(View.VISIBLE);
                        op2.setVisibility(View.VISIBLE);
                        op3.setVisibility(View.VISIBLE);
                        op4.setVisibility(View.VISIBLE);
                        op5.setVisibility(View.VISIBLE);
                        op6.setVisibility(View.VISIBLE);
                        op7.setVisibility(View.VISIBLE);
                        op1.setText(surveys.get(0).getOptions().get(0));
                        op2.setText(surveys.get(0).getOptions().get(1));
                        op3.setText(surveys.get(0).getOptions().get(2));
                        op4.setText(surveys.get(0).getOptions().get(3));
                        op5.setText(surveys.get(0).getOptions().get(4));
                        op6.setText(surveys.get(0).getOptions().get(5));
                        op7.setText(surveys.get(0).getOptions().get(6));
                        pb.setVisibility(View.GONE);
                    }
                    else if(surveys.get(0).getOptions().size()==8)
                    {
                        op1.setVisibility(View.VISIBLE);
                        op2.setVisibility(View.VISIBLE);
                        op3.setVisibility(View.VISIBLE);
                        op4.setVisibility(View.VISIBLE);
                        op5.setVisibility(View.VISIBLE);
                        op6.setVisibility(View.VISIBLE);
                        op7.setVisibility(View.VISIBLE);
                        op8.setVisibility(View.VISIBLE);
                        op1.setText(surveys.get(0).getOptions().get(0));
                        op2.setText(surveys.get(0).getOptions().get(1));
                        op3.setText(surveys.get(0).getOptions().get(2));
                        op4.setText(surveys.get(0).getOptions().get(3));
                        op5.setText(surveys.get(0).getOptions().get(4));
                        op6.setText(surveys.get(0).getOptions().get(5));
                        op7.setText(surveys.get(0).getOptions().get(6));
                        op8.setText(surveys.get(0).getOptions().get(7));
                        pb.setVisibility(View.GONE);
                    }



                }

                pb.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    void surveyHistorycheck()
    {



        Query query=refs.orderByChild("emailid").equalTo(j.getusename());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    //  System.out.println("value is "+dataSnapshot1.getChildren().toString());
                    registration uts = dataSnapshot1.getValue(registration .class);

                    if(uts.getSurverysts().equals("1"))
                    {


                        startActivity(new Intent(SurveyFirst.this,Survey_Notfication.class));

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
