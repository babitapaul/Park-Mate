package com.example.park_mate;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Parkupdation_final extends AppCompatActivity {
    private static final int pick_image_request = 1;
    private Button ParkUpdtae, ParkImageBtn;
    TextView pknme;
    private String Parkname;
    private ImageView mImageview;
    private ProgressBar pgsBar,mprogressbar;
    private EditText Parktype, ParkCity, ParkCounty, ParkState,Parkaddress,Park_Name;
    private Uri imageuri;
    private StorageReference mstoragereReference;
    private DatabaseReference mdatabaserefrence;
    private StorageTask mstoragetask;
    private LinearLayout linr;
    private FirebaseDatabase database89;
    ArrayList<Park> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkupdation_final);
        mprogressbar=(ProgressBar)findViewById(R.id.ourprogressbar);
        mImageview=(ImageView)findViewById(R.id.imageView2);
        Parkaddress=(EditText)findViewById(R.id.prkads);
        ParkCity = (EditText) findViewById(R.id.parkcity);
        ParkCounty = (EditText) findViewById(R.id.parkcounty);
        ParkState = (EditText) findViewById(R.id.parkstate);
        Parktype = (EditText) findViewById(R.id.parktype);
        Park_Name=(EditText)findViewById(R.id.parkname);
        pknme = (TextView) findViewById(R.id.park_name);
        ParkUpdtae = (Button) findViewById(R.id.updatepark);
        pgsBar = (ProgressBar) findViewById(R.id.pbloading);

        ParkImageBtn = (Button) findViewById(R.id.selectparkimage);
        Intent intent = getIntent();
        Parkname = intent.getStringExtra("Parkname");
        pknme.setText(Parkname);
        mstoragereReference= FirebaseStorage.getInstance().getReference("ParkImage");
        mdatabaserefrence= FirebaseDatabase.getInstance().getReference("ParkRecord");
        FetchParkdetails(Parkname);
        ParkImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });
        ParkUpdtae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mstoragetask!=null && mstoragetask.isInProgress())
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Wait Request In Progress ", Toast.LENGTH_SHORT);
                    toast.setMargin(50, 50);
                    toast.show();

                }
                else {
                    Updatenow(Parkname);
                }
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pick_image_request && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();
            Picasso.get().load(imageuri).into(mImageview);
        }
    }
    public void FetchParkdetails(String Parkname) {
        pgsBar.setVisibility(View.VISIBLE);
        list=new ArrayList<Park>();
        DatabaseReference reference11= FirebaseDatabase.getInstance().getReference("ParkRecord");
        System.out.println("Parkn Name is "+Parkname);
        Query query1=reference11.orderByChild("parkname").equalTo(Parkname);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    System.out.println(dataSnapshot1);
                    Park us=dataSnapshot1.getValue(Park.class);
                    list.add(us);

                    Picasso.get().load(list.get(0).getImageurl()).into(mImageview);
                    Parktype.setText(list.get(0).getType());
                    Parkaddress.setText(list.get(0).getAddress());
                    ParkCity.setText(list.get(0).getCity());
                    ParkCounty .setText(list.get(0).getCountry());
                    ParkState.setText(list.get(0).getState());
                    Park_Name.setText(list.get(0).getParkname());
                    pgsBar.setVisibility(View.GONE);
                    break;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }
    void Updatenow(String Parkname)
    {
        if(imageuri!=null)
        {



            DatabaseReference fDatabaseRoot89;
            database89 = FirebaseDatabase.getInstance();
            fDatabaseRoot89 = database89.getReference("ParkRecord");
            Query query23 = fDatabaseRoot89.orderByChild("parkname").equalTo(Parkname);
            query23 .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().removeValue();
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Log.e(TAG, "onCancelled", databaseError.toException());
                }
            });

            final StorageReference filerefrence=mstoragereReference.child(System.currentTimeMillis()+"."+getFileExtension(imageuri));
            mstoragetask=filerefrence.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mprogressbar.setProgress(0);
                        }
                    },5000);

                    filerefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Park update SuccessFully", Toast.LENGTH_SHORT);
                            toast.setMargin(50, 50);
                            toast.show();
                            Uri s= uri;

                            Park Add_park=new Park(Park_Name.getText().toString().trim(),Parkaddress.getText().toString().trim(),ParkState.getText().toString().trim(),ParkCity.getText().toString().trim(),ParkCounty.getText().toString().trim(),Parktype.getText().toString().trim(),s.toString());
                            String uploadid=mdatabaserefrence.push().getKey();
                            mdatabaserefrence.child(uploadid).setValue(Add_park);
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
                    double progressbar=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mprogressbar.setProgress((int)progressbar);
                }
            });
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "No File Selected! ", Toast.LENGTH_SHORT);
            toast.setMargin(50, 50);
            toast.show();

        }

    }

}
