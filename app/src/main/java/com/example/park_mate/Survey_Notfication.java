package com.example.park_mate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Survey_Notfication extends AppCompatActivity {

    Button b1,b2;
    String tokens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey__notfication);
        b1=(Button)findViewById(R.id.skip);
        b2=(Button)findViewById(R.id.again);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent go=new Intent(Survey_Notfication.this,Userwelcome.class);

                startActivity(go);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Survey_Notfication.this,SurveyFirst.class).putExtra("token","1"));

            }
        });
    }
}
