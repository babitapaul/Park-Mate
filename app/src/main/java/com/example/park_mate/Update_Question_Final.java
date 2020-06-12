package com.example.park_mate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Update_Question_Final extends AppCompatActivity {

      TextView setquestionname;
      String getquestionname;
      Button addoption,dropoption,updateoption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__question__final);
        setquestionname = (TextView) findViewById(R.id.my_question);
        addoption = (Button) findViewById(R.id.addoption);
        dropoption = (Button) findViewById(R.id.dropoption);
        updateoption = (Button) findViewById(R.id.updateoption);
        Intent intent = getIntent();
        getquestionname = intent.getStringExtra("que");
        setquestionname.setText(getquestionname);
    }
}
