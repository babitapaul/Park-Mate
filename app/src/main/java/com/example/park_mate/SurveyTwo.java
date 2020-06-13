package com.example.park_mate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SurveyTwo extends AppCompatActivity {

    Button b1;
    TextView back;
    ArrayList<String> k;
    CheckBox gen1,gen2,gen3;
    ArrayList<String>Surveytwolist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_two);
        Surveytwolist=new ArrayList<>();
        gen1=(CheckBox)findViewById(R.id.gen1);
        gen2=(CheckBox)findViewById(R.id.gen2);
        gen3=(CheckBox)findViewById(R.id.gen3);
        k=new ArrayList<>();
        Intent intent = getIntent();
        k= intent.getStringArrayListExtra("SurveyFirstList");
        System.out.println(k);
        b1=(Button)findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gen1.isChecked())
                {
                    Surveytwolist.add(gen1.getText().toString());
                }
                if(gen2.isChecked())
                {
                    Surveytwolist.add(gen2.getText().toString());
                }
                if(gen3.isChecked())
                {
                    Surveytwolist.add(gen3.getText().toString());
                }
                if(Surveytwolist.size()>0) {

                    Intent i=new Intent(SurveyTwo.this,SurveyThree.class);
                    i.putExtra("SurveyFirstList",k);
                    i.putExtra("SurveySecondList",Surveytwolist);
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
                Intent intent= new Intent(SurveyTwo.this,SurveyFirst.class);
                startActivity(intent);
            }
        });
    }
}