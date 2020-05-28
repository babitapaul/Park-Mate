package com.example.park_mate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class NewUser extends AppCompatActivity {

     TextView back;
      EditText emailid,username,mobilenb,password;
      Button reg;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    LinearLayout linr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        emailid=(EditText)findViewById(R.id.text1);
        username=(EditText)findViewById(R.id.username);
        mobilenb=(EditText)findViewById(R.id.mobilenb);
        password=(EditText)findViewById(R.id.text2);
        reg=(Button)findViewById(R.id.button);
        back =(TextView)findViewById(R.id.back);
        databaseReference= FirebaseDatabase.getInstance().getReference("");
        firebaseAuth= FirebaseAuth.getInstance();
        linr=(LinearLayout)findViewById(R.id.singupform);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(NewUser.this, Login.class);
                startActivity(i);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Adduser(emailid.getText().toString(),username.getText().toString(),mobilenb.getText().toString(),password.getText().toString());
            }
        });

    }
    public void Adduser(final String emailid, final String username, final String mobilenb, final String password)
    {
        firebaseAuth.createUserWithEmailAndPassword(emailid, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {



                        }
                    }
                });
    }
}
