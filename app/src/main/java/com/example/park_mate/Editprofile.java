package com.example.park_mate;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import static android.app.Activity.RESULT_OK;


public class Editprofile extends Fragment {

    ImageView myimage;
    private static final int pick_image_request = 1;
    //  private  EditText Address,about;
    private  Button chooseimage,Updateprofile;
    private  TextView name;
    private  ProgressBar pb,mprogressbar;
    private Uri imageuri;
    private StorageReference mstoragereReference;
    private StorageTask mstoragetask;
    private DatabaseReference fDatabaseRoot;
    private FirebaseDatabase database;
    private  Session session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_editprofile, container, false);
        mprogressbar=(ProgressBar)view.findViewById(R.id.ourprogressbar);
        session=new Session(getContext());
        name=(TextView)view.findViewById(R.id.username);
        myimage=(ImageView)view.findViewById(R.id.pic);
        //   Address=(EditText)view.findViewById(R.id.Address);
        //   about=(EditText)view.findViewById(R.id.Aboutme);
        chooseimage=(Button)view.findViewById(R.id.choosepic);
        Updateprofile=(Button)view.findViewById(R.id.UpdateNow);
        pb=(ProgressBar)view.findViewById(R.id.pbloading);
        pb.setVisibility(View.GONE);
        getActivity().setTitle("Edit Profile");
        name.setText(session.getname());
        //if(session.getAboutme().isEmpty()){}else{about.setText(session.getAboutme());}
        //if(session.getAddress().isEmpty()){}else{Address.setText(session.getAddress());}
        if(session.getimageurl().trim().isEmpty()) {

        }
        else {
            Picasso.get().load(session.getimageurl()).into(myimage);
        }

        database = FirebaseDatabase.getInstance();
        fDatabaseRoot = database.getReference("users");
        mstoragereReference= FirebaseStorage.getInstance().getReference("UserImage");
        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        Updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mstoragetask != null && mstoragetask.isInProgress()) {
                    Toast toast = Toast.makeText(getContext(), "Please Wait Request In Progress", Toast.LENGTH_SHORT);
                    toast.setMargin(50, 50);
                    toast.show();
                    pb.setVisibility(View.GONE);

                } else {

                      /*if (TextUtils.isEmpty(about.getText().toString())) {
                          Toast toast = Toast.makeText(getContext(), "Please Enter About Your Self!", Toast.LENGTH_SHORT);
                          toast.setMargin(50, 50);
                          toast.show();

                          return;
                      }
                      if (TextUtils.isEmpty(Address.getText().toString())) {
                          Toast toast = Toast.makeText(getContext(), "Please Enter Your Address!", Toast.LENGTH_SHORT);
                          toast.setMargin(50, 50);
                          toast.show();

                          return;
                      } */
                    updateprofile();
                }
            }
        });
        return  view;
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, pick_image_request);
    }

    private String getFileExtension(Uri uri) {

        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pick_image_request && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();
            Picasso.get().load(imageuri).into(myimage);
        }
    }

    private void updateprofile()
    {
        pb.setVisibility(View.VISIBLE);
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
                            final Uri s = uri;

                            final Query query = fDatabaseRoot.orderByChild("emailid").equalTo(session.getusename());
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                        String key = appleSnapshot.getKey();
                                        //    update(key);
                                        //  fDatabaseRoot.child(key).child("aboutme").setValue(about.getText().toString());
                                        fDatabaseRoot.child(key).child("imageurl").setValue(s.toString());
                                        session.setimageurl(s.toString());
                                        //fDatabaseRoot.child(key).child("address").setValue(Address.getText().toString());


                                    }

                                    pb.setVisibility(View.GONE);

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Log.e(TAG, "onCancelled", databaseError.toException());
                                    pb.setVisibility(View.GONE);
                                }
                            });


                            Toast toast = Toast.makeText(getContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT);
                            toast.setMargin(50, 50);
                            toast.show();


                            Intent intent =new Intent(getContext(),Userwelcome.class);
                            startActivity(intent);
                            pb.setVisibility(View.GONE);
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast toast = Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
                    toast.setMargin(50, 50);
                    toast.show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progressbar = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mprogressbar.setProgress((int) progressbar);
                }
            });
        } else {

            Toast toast = Toast.makeText(getContext(), "No File Selected!", Toast.LENGTH_SHORT);
            toast.setMargin(50, 50);
            toast.show();
            pb.setVisibility(View.GONE);
        }
    }
}
