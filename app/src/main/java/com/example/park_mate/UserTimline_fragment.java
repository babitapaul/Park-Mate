package com.example.park_mate;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.app.Activity.RESULT_OK;


public class UserTimline_fragment extends Fragment {

    User_TimeLine_List_adpater user_timeLine_list_adpater;
    RecyclerView recyclerView;
    ArrayAdapter<String> adapter;
    ImageView myimage,camerabutton;
    EditText posttimeline; private DatabaseReference reference;
    Button postbutton;
    Spinner Parklist;
    ProgressBar pb;
    Session mysession;
    final List<String> parknamelists = new ArrayList<String>();
    private FirebaseDatabase database;
    private DatabaseReference fDatabaseRoot;

    private ArrayList<registration> Information;
    private Uri imageuri;
    private static final int pick_image_request = 1;
    private StorageReference mstoragereReference;
    private DatabaseReference mdatabaserefrence;
    private StorageTask mstoragetask;
    ArrayList<Timeline> sts;
    final ArrayList<String>myfrd=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_timline_fragment, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.mytimeline);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        pb=(ProgressBar)view.findViewById(R.id.pbloading);
        mysession=new Session(getContext());
        getActivity().setTitle("Welcome "+mysession.getname().toUpperCase());
        database = FirebaseDatabase.getInstance();
        fDatabaseRoot = database.getReference("ParkRecord");
        Parklist=(Spinner)view.findViewById(R.id.parklist);

        camerabutton=(ImageView)view.findViewById(R.id.camerabuttons);
        myimage=(ImageView)view.findViewById(R.id.mypic);

        posttimeline=(EditText)view.findViewById(R.id.postit);
        postbutton=(Button)view.findViewById(R.id.post);
        getprofileinfo();
        Retrieve_Park_Names();
        loaduserTimeline();
        mstoragereReference= FirebaseStorage.getInstance().getReference("TimelinePostImage");
        mdatabaserefrence= FirebaseDatabase.getInstance().getReference("UserTimeline");
        camerabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(posttimeline.getText().toString().trim())) {
                    Toast toast = Toast.makeText(getContext(), "Please Enter in Post Somthing!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    return;
                }
                Postintimeline(posttimeline.getText().toString().trim());
                posttimeline.setText(null);
            }
        });
        return  view;
    }
    void getprofileinfo()
    {
        pb.setVisibility(View.VISIBLE);
        Information=new ArrayList<registration>();
        reference= FirebaseDatabase.getInstance().getReference("users");
        Query query=reference.orderByChild("emailid").equalTo(mysession.getusename());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    //  System.out.println("value is "+dataSnapshot1.getChildren().toString());
                    registration us = dataSnapshot1.getValue(registration .class);
                    Information.add(us);

                    if(Information.size()>0)
                    {
                        mysession.setname(Information.get(0).getUsername());
                        mysession.setgender(Information.get(0).getGender());
                        mysession.setaboutme(Information.get(0).getAboutme());
                        mysession.setStatus(Information.get(0).getStatus());
                        mysession.setimageurl(Information.get(0).getImageurl());
                        mysession.setmobilenb(Information.get(0).getMobileno());
                        mysession.setAddress(Information.get(0).getAddress());
                        mysession.setvisiblity(Information.get(0).getStatus());
                        //  List<String> g=Information.get(0).secondsurvey;
                        // Set<String> foo = new HashSet<String>(g);
                        // mysession.setsecondsurvey(foo);
                        if(Information.get(0).getImageurl().isEmpty())
                        {

                        }
                        else {
                            Picasso.get().load(Information.get(0).getImageurl()).into(myimage);

                        }
                    }


                }
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    void Retrieve_Park_Names()
    {
        Query query = fDatabaseRoot.orderByKey();
        parknamelists.add(0,"Select Park Name");
        fDatabaseRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot parkrecord : dataSnapshot.getChildren()){

                    parknamelists.add(parkrecord.child("parkname").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
        adapter = new ArrayAdapter<String>(getContext(),R.layout.color_spinner_layout,parknamelists);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        Parklist.setAdapter(adapter);
        pb.setVisibility(View.GONE);

        Parklist.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                if(parent.getItemAtPosition(pos).equals("Select Park Name"))
                {

                }
                else
                {
                    String name=parent.getItemAtPosition(pos).toString();
                    Toast toast = Toast.makeText(getContext(), "Your Select "+name, Toast.LENGTH_SHORT);
                    toast.setMargin(50, 50);
                    toast.show();
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
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

        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pick_image_request && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imageuri=data.getData();
            Picasso.get().load(imageuri).into(myimage);
        }
    }
    private void Postintimeline(final String posttimelinemsgs) {
        if(Parklist.getSelectedItem().equals("Select Park Name"))
        {
            Toast toast = Toast.makeText(getContext(), "Please Select Park Name", Toast.LENGTH_SHORT);
            toast.setMargin(50, 50);
            toast.show();
        }
        else {
            sts.removeAll(sts);
            myfrd.removeAll(myfrd);
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
                                //   mprogressbar.setProgress(0);
                            }
                        }, 5000);

                        filerefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri s = uri;
                                Toast toast = Toast.makeText(getContext(), "TimeLine Posted SuccessFully", Toast.LENGTH_SHORT);
                                toast.setMargin(50, 50);
                                toast.show();
                                String uploadid = mdatabaserefrence.push().getKey();
                                String mydatetime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                                Timeline timeline = new Timeline(uploadid, Parklist.getSelectedItem().toString(), mysession.getname(), mysession.getusename(), mysession.getcity(), mysession.getAddress(), posttimelinemsgs, mydatetime, s.toString(), mysession.getimageurl(), mysession.getmobile());

                                mdatabaserefrence.child(uploadid).setValue(timeline);
                                myfrd.removeAll(myfrd);
                                sts.removeAll(sts);
                                if (mysession.getimageurl().isEmpty()) {
                                    Picasso.get().load(R.drawable.userimage).into(myimage);
                                } else {
                                    Picasso.get().load(mysession.getimageurl()).into(myimage);

                                }
                                pb.setVisibility(View.GONE);
                                sts.removeAll(sts);

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
                        //  mprogressbar.setProgress((int) progressbar);
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
    void loaduserTimeline()
    {
        sts = new ArrayList<Timeline>();
        pb.setVisibility(View.VISIBLE);
        myfrd.removeAll(myfrd);
        sts.removeAll(sts);
        // myfrd.add(mysession.getusename());
        reference= FirebaseDatabase.getInstance().getReference("Friend Request");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {


                    Friend_Request us=dataSnapshot1.getValue(Friend_Request.class);
                    if(us.getStatus().equals("Approved") && us.getSenderemailid().equals(mysession.getusename()) || us.getFromemailid().equals(mysession.getusename()))
                    {
                        myfrd.add(us.getSenderemailid().toString());
                        myfrd.add(us.getFromemailid().toString());
                    }


                }
                //   int chk=0;
                for(String jk:myfrd) {

                    System.out.println("my emailid is "+jk);
                    sts.removeAll(sts);
                    reference = FirebaseDatabase.getInstance().getReference("UserTimeline");
                    Query query = reference.orderByChild("emailid").equalTo(jk);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                Timeline us = dataSnapshot1.getValue(Timeline.class);
                                sts.add(us);
                            }
                            user_timeLine_list_adpater = new User_TimeLine_List_adpater(getContext(), sts);
                            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                            recyclerView.setAdapter(user_timeLine_list_adpater);
                            pb.setVisibility(View.GONE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                sts.removeAll(sts);
                myfrd.removeAll(myfrd);
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
















    }


}
