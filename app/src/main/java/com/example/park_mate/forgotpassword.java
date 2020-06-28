package com.example.park_mate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {
    private EditText text1;
    private Button button1;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        text1 = findViewById(R.id.text1);
        button1 = findViewById(R.id.button1);
        mAuth = FirebaseAuth.getInstance();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = text1.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(forgotpassword.this, "Please enter your correct email here", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(forgotpassword.this, "Please check your email to reset your password ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(forgotpassword.this,Login.class));
                            }
                            else{
                                String message = task.getException().getMessage();
                                Toast.makeText(forgotpassword.this, "Error:"+message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
