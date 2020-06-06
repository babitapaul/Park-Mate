package com.example.park_mate;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class ParkAdd extends AppCompatActivity {

    private static final int pick_image_request = 1;
    private Button ParkAdd, ParkImageBtn;
    TextView pknme;
    private String Parkname;
    private ImageView mImageview;
    private ProgressBar pgsBar,mprogressbar;
    private EditText  Parktype, ParkCity, ParkCounty, ParkState,Parkaddress;
    private Uri imageuri;
    private StorageReference mstoragereReference;
    private DatabaseReference mdatabaserefrence;
    private StorageTask mstoragetask;
    private LinearLayout linr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_park_add);


        mprogressbar=(ProgressBar)findViewById(R.id.ourprogressbar);

        mImageview=(ImageView)findViewById(R.id.imageView2);
        Parkaddress=(EditText)findViewById(R.id.prkads);
        ParkCity = (EditText) findViewById(R.id.parkcity);
        ParkCounty = (EditText) findViewById(R.id.parkcounty);
        ParkState = (EditText) findViewById(R.id.parkstate);
        Parktype = (EditText) findViewById(R.id.parktype);
        pknme = (TextView) findViewById(R.id.park_name);
        ParkAdd = (Button) findViewById(R.id.add);
        pgsBar = (ProgressBar) findViewById(R.id.pbloading);
        pgsBar.setVisibility(View.GONE);
        ParkImageBtn = (Button) findViewById(R.id.selectparkimage);
        Intent intent = getIntent();
        Parkname = intent.getStringExtra("Parkname");
        pknme.setText(Parkname);
        mstoragereReference= FirebaseStorage.getInstance().getReference("ParkImage");
        mdatabaserefrence= FirebaseDatabase.getInstance().getReference("ParkRecord");

        ParkAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mstoragetask != null && mstoragetask.isInProgress()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Wait Request In Progress", Toast.LENGTH_SHORT);
                    toast.setMargin(50, 50);
                    toast.show();

                } else {

                    if (TextUtils.isEmpty(Parktype.getText().toString().trim())) {
                        Toast toast = Toast.makeText(getApplicationContext(),  "Please Enter Park Type!", Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                        pgsBar.setVisibility(View.GONE);
                        return;
                    }
                    if (TextUtils.isEmpty(ParkCity.getText().toString().trim())) {
                        Toast toast = Toast.makeText(getApplicationContext(),  "Please Enter Park City!", Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                        pgsBar.setVisibility(View.GONE);
                        return;
                    }
                    if (TextUtils.isEmpty(ParkState.getText().toString().trim())) {
                        Toast toast = Toast.makeText(getApplicationContext(),  "Please Enter Park State!", Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                        pgsBar.setVisibility(View.GONE);
                        return;
                    }
                    if (TextUtils.isEmpty(ParkCounty.getText().toString().trim())) {
                        Toast toast = Toast.makeText(getApplicationContext(),  "Please Enter Park Country!", Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                        pgsBar.setVisibility(View.GONE);
                        return;
                    }
                    if (TextUtils.isEmpty(Parkaddress.getText().toString().trim())) {
                        Toast toast = Toast.makeText(getApplicationContext(),  "Please Enter Park Address!", Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                        pgsBar.setVisibility(View.GONE);
                        return;
                    }
                    pgsBar.setVisibility(View.VISIBLE);
                }
            }
        });
        ParkImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, pick_image_request);
    }

    private String getFileExtension(Uri uri) {

        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }




}
