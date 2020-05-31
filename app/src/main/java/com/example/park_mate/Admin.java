package com.example.park_mate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class Admin extends AppCompatActivity {
    private static final int pick_image_request=1;
    Button login;
    private EditText adminemailid,adminpass;
    private String admn,pas;
    private ProgressBar pgsBar;
    private Uri imageuri;
    private StorageReference mstoragereReference;
    private DatabaseReference mdatabaserefrence;
    private StorageTask mstoragetask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        adminemailid=(EditText)findViewById(R.id.adminemailid);
        adminpass=(EditText)findViewById(R.id.adminpass);
        pgsBar = (ProgressBar) findViewById(R.id.pbloading);
        pgsBar.setVisibility(View.GONE);
        mstoragereReference= FirebaseStorage.getInstance().getReference("Parkimage");
        mdatabaserefrence= FirebaseDatabase.getInstance().getReference("ParkRecord");
        login= (Button)findViewById(R.id.adminlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgsBar.setVisibility(View.VISIBLE);
                admn=adminemailid.getText().toString();
                pas=adminpass.getText().toString();
                if (TextUtils.isEmpty(admn.trim())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Emailid!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    pgsBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(pas.trim())) {
                    Toast toast = Toast.makeText(getApplicationContext(),  "Please Enter Password!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    pgsBar.setVisibility(View.GONE);
                    return;
                }
                if(admn.equals("parkmate@admin.com") && pas.equals("admin"))
                {
                    pgsBar.setVisibility(View.GONE);
                    Intent intent = new Intent(Admin.this,welcomepage.class);
                    startActivity(intent);
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),  "Wrong Emaild and Password!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    pgsBar.setVisibility(View.GONE);
                }



            }
        });

    }
}
