package com.example.park_mate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NewUser extends AppCompatActivity {
    private RadioGroup genderGroup;
    private RadioButton Selectgender;
    TextView back;
    EditText emailid,username,mobilenb,password,city;
    Button reg;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    LinearLayout linr;
    ProgressBar pgsBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        genderGroup=(RadioGroup)findViewById(R.id.radioGroup);
        pgsBar = (ProgressBar) findViewById(R.id.pbloading);
        pgsBar.setVisibility(View.GONE);
        emailid=(EditText)findViewById(R.id.text1);
        username=(EditText)findViewById(R.id.username);
        mobilenb=(EditText)findViewById(R.id.mobilenb);
        password=(EditText)findViewById(R.id.text2);
        reg=(Button)findViewById(R.id.button);
        city=(EditText)findViewById(R.id.city);
        back =(TextView)findViewById(R.id.back);
        databaseReference= FirebaseDatabase.getInstance().getReference("registration");
        firebaseAuth= FirebaseAuth.getInstance();
        linr=(LinearLayout)findViewById(R.id.singupform);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(NewUser.this,Login.class);
                startActivity(i);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgsBar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(emailid.getText().toString().trim())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Emailid!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    pgsBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(username.getText().toString().trim())) {
                    Toast toast = Toast.makeText(getApplicationContext(),  "Please Enter Username!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    pgsBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(city.getText().toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(),  "Please Enter City!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    pgsBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(mobilenb.getText().toString().trim())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Mobile Number!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    pgsBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(),  "Please Enter Password!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    pgsBar.setVisibility(View.GONE);
                    return;
                }

                int selectedId=genderGroup.getCheckedRadioButtonId();
                Selectgender=(RadioButton)findViewById(selectedId);

                Adduser(city.getText().toString(),Selectgender.getText().toString(),emailid.getText().toString(),username.getText().toString(),mobilenb.getText().toString(),password.getText().toString());
            }
        });



    }
    public void Adduser(final String city,final String gender,final String emailid, final String username, final String mobilenb, final String password)
    {
        firebaseAuth.createUserWithEmailAndPassword(emailid, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        List<String> ists=new ArrayList<>();
                        List<String> iinds=new ArrayList<>();
                        List<String> iiirds=new ArrayList<>();
                        if (task.isSuccessful()) {
                            registration us = new registration(city,emailid,username, password,gender,mobilenb,"",ists,iinds,iiirds,"","1","","0");


                            FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(us).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Registration SucessFully!", Toast.LENGTH_SHORT);
                                    toast.setMargin(50,50);
                                    toast.show();

                                    pgsBar.setVisibility(View.GONE);
                                    startActivity(new Intent(getApplicationContext(),Login.class));
                                }
                            });
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "This Emaillid Already Registered!", Toast.LENGTH_SHORT);
                            toast.setMargin(50,50);
                            toast.show();

                            pgsBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}