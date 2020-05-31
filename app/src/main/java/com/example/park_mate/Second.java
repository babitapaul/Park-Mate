package com.example.park_mate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Second extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        final RadioGroup G1 = (RadioGroup) findViewById(R.id.G1);

        final RadioButton r1 = (RadioButton) findViewById(R.id.r1);
        final RadioButton r2 = (RadioButton) findViewById(R.id.r2);

        final Button B1 = (Button) findViewById(R.id.B1);

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (r1.isChecked()) {
                    Intent intents = new Intent(Second.this, Login.class);
                    startActivity(intents);
                } else if (r2.isChecked()) {
                    Intent intent1 = new Intent(Second.this,Admin.class);
                    startActivity(intent1);
                }
            }
        });
    }
}
