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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SurveyThree extends AppCompatActivity {

    TextView back,Question;
    Button b1;
    CheckBox op1,op2,op3,op4,op5,op6,op7,op8;
    ProgressBar pb;
    DatabaseReference reference;
    Session s;
    ArrayList<Survey> surveys;
    ArrayList<String>Surveyist,Surveytwolist,Surveythreelist;
    private DatabaseReference fDatabaseRoot;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_three);
        s=new Session(getApplication());
        b1 =(Button)findViewById(R.id.gotodashboard);
        Question=(TextView)findViewById(R.id.Question);
        surveys=new ArrayList<Survey>();
        Surveyist=new ArrayList<>();
        Surveytwolist=new ArrayList<>();
        Surveythreelist=new ArrayList<>();
        Intent intent = getIntent();
        Surveyist= intent.getStringArrayListExtra("SurveyFirstList");
        Surveytwolist= intent.getStringArrayListExtra("SurveySecondList");
        database = FirebaseDatabase.getInstance();
        fDatabaseRoot = database.getReference("users");
        reference= FirebaseDatabase.getInstance().getReference("Survey");
        fetchquestion();
        pb=(ProgressBar)findViewById(R.id.pbloading);
        b1=(Button)findViewById(R.id.gotodashboard);
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




        back=(TextView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SurveyThree.this,SurveyTwo.class);
                startActivity(intent);

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pb.setVisibility(View.VISIBLE);
                if(op1.isChecked())
                {
                    Surveythreelist.add(op1.getText().toString());
                }
                if(op2.isChecked())
                {
                    Surveythreelist.add(op2.getText().toString());
                }
                if(op3.isChecked())
                {
                    Surveythreelist.add(op3.getText().toString());
                }
                if(op4.isChecked())
                {
                    Surveythreelist.add(op4.getText().toString());
                }
                if(op5.isChecked())
                {
                    Surveythreelist.add(op5.getText().toString());
                }
                if(op6.isChecked())
                {
                    Surveythreelist.add(op6.getText().toString());
                }
                if(op7.isChecked())
                {
                    Surveythreelist.add(op7.getText().toString());
                }
                if(op8.isChecked())
                {
                    Surveythreelist.add(op8.getText().toString());
                }
                if(Surveythreelist.size()>0) {

                    Set<String> iinds = new HashSet<String>(Surveytwolist);
                    s.setsecondsurvey(iinds);

                    Surveyist.addAll(Surveythreelist);
                    Set<String> iiird = new HashSet<String>(Surveyist);
                    s.setparksurvey(iiird);

                    Toast toast = Toast.makeText(getApplicationContext(), "Welcome To ParkMate", Toast.LENGTH_SHORT);
                    toast.setMargin(50, 50);
                    toast.show();
                    pb.setVisibility(View.GONE);
                    Intent i=new Intent(SurveyThree.this,Userwelcome.class);

                    startActivity(i);

                  /*  Intent i=new Intent(SurveyTwo.this,SurveyThree.class);
                    i.putExtra("SurveyFirstList",k);
                    i.putExtra("SurveySecondList",Surveytwolist);
                    startActivity(i);

                   */


            /*        final Query query = fDatabaseRoot.orderByChild("emailid").equalTo(s.getusename());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                String key = appleSnapshot.getKey();
                                //    update(key);

                                fDatabaseRoot.child(key).child("firstsurvey").setValue(Surveyist);
                                fDatabaseRoot.child(key).child("secondsurvey").setValue(Surveytwolist);
                                fDatabaseRoot.child(key).child("thirdsurvey").setValue(Surveythreelist);


                            }



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Log.e(TAG, "onCancelled", databaseError.toException());
                            pb.setVisibility(View.GONE);
                        }
                    });
                  */


                }

                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),  "Please Check At Least one!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    pb.setVisibility(View.GONE);

                }









            }
        });
    }
    void fetchquestion()
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {

                    Survey us=dataSnapshot1.getValue(Survey.class);
                    surveys.add(us);




                }

                System.out.println(surveys.size());
                Question.setText(surveys.get(1).getQuestion());
                int k=1;
                if(surveys.get(1).getOptions().size()==2)
                {
                    op1.setVisibility(View.VISIBLE);
                    op2.setVisibility(View.VISIBLE);
                    op3.setVisibility(View.GONE);
                    op4.setVisibility(View.GONE);
                    op5.setVisibility(View.GONE);
                    op6.setVisibility(View.GONE);
                    op7.setVisibility(View.GONE);
                    op1.setText(surveys.get(1).getOptions().get(0));
                    op2.setText(surveys.get(1).getOptions().get(1));
                    pb.setVisibility(View.GONE);
                    //  op3.setText(surveys.get(0).getOptions().get(2));
                }
                else if(surveys.get(1).getOptions().size()==3)
                {
                    op1.setVisibility(View.VISIBLE);
                    op2.setVisibility(View.VISIBLE);
                    op3.setVisibility(View.VISIBLE);
                    op1.setText(surveys.get(1).getOptions().get(0));
                    op2.setText(surveys.get(1).getOptions().get(1));
                    op3.setText(surveys.get(1).getOptions().get(2));
                    pb.setVisibility(View.GONE);
                }
                else if(surveys.get(1).getOptions().size()==4)
                {
                    op1.setVisibility(View.VISIBLE);
                    op2.setVisibility(View.VISIBLE);
                    op3.setVisibility(View.VISIBLE);
                    op4.setVisibility(View.VISIBLE);
                    op1.setText(surveys.get(1).getOptions().get(0));
                    op2.setText(surveys.get(1).getOptions().get(1));
                    op3.setText(surveys.get(1).getOptions().get(2));
                    op4.setText(surveys.get(1).getOptions().get(3));
                    pb.setVisibility(View.GONE);
                }
                else if(surveys.get(0).getOptions().size()==5)
                {
                    op1.setVisibility(View.VISIBLE);
                    op2.setVisibility(View.VISIBLE);
                    op3.setVisibility(View.VISIBLE);
                    op4.setVisibility(View.VISIBLE);
                    op5.setVisibility(View.VISIBLE);
                    op1.setText(surveys.get(1).getOptions().get(0));
                    op2.setText(surveys.get(1).getOptions().get(1));
                    op3.setText(surveys.get(1).getOptions().get(2));
                    op4.setText(surveys.get(1).getOptions().get(3));
                    op5.setText(surveys.get(1).getOptions().get(4));
                    pb.setVisibility(View.GONE);
                }
                else if(surveys.get(1).getOptions().size()==6)
                {
                    op1.setVisibility(View.VISIBLE);
                    op2.setVisibility(View.VISIBLE);
                    op3.setVisibility(View.VISIBLE);
                    op4.setVisibility(View.VISIBLE);
                    op5.setVisibility(View.VISIBLE);
                    op6.setVisibility(View.VISIBLE);
                    op1.setText(surveys.get(1).getOptions().get(0));
                    op2.setText(surveys.get(1).getOptions().get(1));
                    op3.setText(surveys.get(1).getOptions().get(2));
                    op4.setText(surveys.get(1).getOptions().get(3));
                    op5.setText(surveys.get(1).getOptions().get(4));
                    op6.setText(surveys.get(1).getOptions().get(5));
                    pb.setVisibility(View.GONE);
                }
                else if(surveys.get(1).getOptions().size()==7)
                {
                    op1.setVisibility(View.VISIBLE);
                    op2.setVisibility(View.VISIBLE);
                    op3.setVisibility(View.VISIBLE);
                    op4.setVisibility(View.VISIBLE);
                    op5.setVisibility(View.VISIBLE);
                    op6.setVisibility(View.VISIBLE);
                    op7.setVisibility(View.VISIBLE);
                    op1.setText(surveys.get(1).getOptions().get(0));
                    op2.setText(surveys.get(1).getOptions().get(1));
                    op3.setText(surveys.get(1).getOptions().get(2));
                    op4.setText(surveys.get(1).getOptions().get(3));
                    op5.setText(surveys.get(1).getOptions().get(4));
                    op6.setText(surveys.get(1).getOptions().get(5));
                    op7.setText(surveys.get(1).getOptions().get(6));
                    pb.setVisibility(View.GONE);
                }
                else if(surveys.get(1).getOptions().size()==8)
                {
                    op1.setVisibility(View.VISIBLE);
                    op2.setVisibility(View.VISIBLE);
                    op3.setVisibility(View.VISIBLE);
                    op4.setVisibility(View.VISIBLE);
                    op5.setVisibility(View.VISIBLE);
                    op6.setVisibility(View.VISIBLE);
                    op7.setVisibility(View.VISIBLE);
                    op8.setVisibility(View.VISIBLE);
                    op1.setText(surveys.get(1).getOptions().get(0));
                    op2.setText(surveys.get(1).getOptions().get(1));
                    op3.setText(surveys.get(1).getOptions().get(2));
                    op4.setText(surveys.get(1).getOptions().get(3));
                    op5.setText(surveys.get(1).getOptions().get(4));
                    op6.setText(surveys.get(1).getOptions().get(5));
                    op7.setText(surveys.get(1).getOptions().get(6));
                    op8.setText(surveys.get(1).getOptions().get(7));
                    pb.setVisibility(View.GONE);
                }
                pb.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}