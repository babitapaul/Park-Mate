package com.example.park_mate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


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
                    Addpark();
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


    private void Addpark() {

        if (imageuri != null) {
            final StorageReference filerefrence = mstoragereReference.child(System.currentTimeMillis() + "." + getFileExtension(imageuri));
            mstoragetask = filerefrence.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mprogressbar.setProgress(0);
                        }
                    }, 5000);

                    filerefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri s = uri;
                            Toast toast = Toast.makeText(getApplicationContext(), "Park Added SuccessFully", Toast.LENGTH_SHORT);
                            toast.setMargin(50, 50);
                            toast.show();

                            Park Add_park = new Park(pknme.getText().toString().trim(),Parkaddress.getText().toString().trim(),ParkState.getText().toString().trim(), ParkCity.getText().toString().trim(),ParkCounty.getText().toString().trim(),Parktype.getText().toString().trim(),s.toString());
                            String uploadid = mdatabaserefrence.push().getKey();
                            mdatabaserefrence.child(uploadid).setValue(Add_park);
                            pgsBar.setVisibility(View.GONE);

                            Intent intent =new Intent(ParkAdd.this,welcomepage.class);
                            startActivity(intent);
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar snackbar1 = Snackbar.make(linr, e.getMessage(), Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progressbar = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mprogressbar.setProgress((int) progressbar);
                }
            });
        } else {

            Toast toast = Toast.makeText(getApplicationContext(), "No File Selected!", Toast.LENGTH_SHORT);
            toast.setMargin(50, 50);
            toast.show();
            pgsBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pick_image_request && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();
            Picasso.get().load(imageuri).into(mImageview);
        }
    }

}
