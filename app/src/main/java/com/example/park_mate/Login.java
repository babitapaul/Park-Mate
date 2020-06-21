package com.example.park_mate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Login extends AppCompatActivity {

    Button button,button1;
    TextView back;
    ProgressBar pgsBar;
    TextView forgot;
    EditText emailid,password;
    FirebaseAuth firebaseAuth;
    Session st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        st=new Session(getApplicationContext());
        emailid=(EditText)findViewById(R.id.login_emailid);
        password=(EditText)findViewById(R.id.login_password);
        pgsBar = (ProgressBar) findViewById(R.id.pbloading);
        back=(TextView)findViewById(R.id.back);
        button1=(Button) findViewById(R.id.button1);
        forgot =(TextView)findViewById(R.id.forgot);
        button =(Button)findViewById(R.id.button2);
        pgsBar.setVisibility(View.GONE);
        firebaseAuth= FirebaseAuth.getInstance();
        //forgot password
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(Login.this, forgotpassword.class);
                startActivity(pass);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login.this,NewUser.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Login.this,Second.class);
                startActivity(i);
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent =new Intent(Login.this,SurveyFirst.class);
                startActivity(intent);*/
                pgsBar.setVisibility(View.VISIBLE);
                String email, pass;
                email = emailid.getText().toString();
                pass= password.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Emailid!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    pgsBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast toast = Toast.makeText(getApplicationContext(),  "Please Enter Password!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    pgsBar.setVisibility(View.GONE);
                    return;
                }
                authcheck(email,pass);


            }
        });
    }

    public void authcheck (final String emailid, String password)
    {
        firebaseAuth.signInWithEmailAndPassword(emailid, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            st.setusename(emailid);

                            pgsBar.setVisibility(View.GONE);
                            startActivity(new Intent(Login.this,SurveyFirst.class).putExtra("token","0"));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast toast = Toast.makeText(getApplicationContext(),  "Wrong Emaild and Password!", Toast.LENGTH_SHORT);
                            toast.setMargin(0,0);
                            toast.show();
                            pgsBar.setVisibility(View.GONE);



                        }

                        // ...
                    }
                });
    }
}
