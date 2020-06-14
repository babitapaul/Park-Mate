package com.example.park_mate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.cert.CertPathValidatorException;
import java.util.Calendar;

public class Block_Reason extends AppCompatActivity {

    private DatabaseReference reference;
    Session session;
    String name,Useremailid,userimageurl;
    EditText reasonwrite;
    Button block,home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block__reason);
        final Intent intent = getIntent();
        session=new Session(getApplicationContext());
        name=intent.getStringExtra("username");
        Useremailid=intent.getStringExtra("profilereviewemailid");
        userimageurl=intent.getStringExtra("userimageurl");
        reasonwrite=(EditText)findViewById(R.id.wrt);
        block=(Button)findViewById(R.id.blocks);
        home=(Button)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Userwelcome.class));
            }
        });
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(reasonwrite.getText().toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Reason!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();

                    return;
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(Block_Reason.this,R.style.AlertDialogStyle);

                builder.setMessage("this is message");
                builder.setTitle("Confirm");

                //Setting message manually and performing action on button click
                builder.setMessage("Are Sure Your Reason is Valid ?");
                //This will not allow to close dialogbox until user selects an option
                builder.setCancelable(false);

                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String mydatetime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                        reference= FirebaseDatabase.getInstance().getReference("Block List");
                        Block_List block_list=new Block_List(mydatetime,name,Useremailid,session.getname(),session.getusename(),userimageurl,reasonwrite.getText().toString());
                        String blkid=reference.push().getKey();
                        reference.child(blkid).setValue(block_list);

                        Toast toast=Toast.makeText(getApplicationContext(),"Block SuccessFully ",Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                        reasonwrite.setText(null);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                //alert.setTitle("AlertDialogExample");
                alert.show();

            }
        });
    }

}
