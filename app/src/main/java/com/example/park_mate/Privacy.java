package com.example.park_mate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Privacy extends Fragment {


    TextView user_name;
    ImageView cpic;
    Switch btnupdate;
    private Session j;
    private DatabaseReference fDatabaseRoot;
    private FirebaseDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =inflater.inflate(R.layout.fragment_privacy, container, false);
        getActivity().setTitle("Privacy");
        user_name = (TextView)root.findViewById(R.id.userid);
        cpic = (ImageView)root.findViewById(R.id.pic);
        btnupdate = (Switch)root.findViewById(R.id.switch1);
        j=new Session(getActivity());
        user_name.setText(j.getname());
        database = FirebaseDatabase.getInstance();
        fDatabaseRoot = database.getReference("users");
        if (j.getimageurl().trim().isEmpty()) {
            Picasso.get().load(R.drawable.userimage).into(cpic);

        } else {
            Picasso.get().load(j.getimageurl()).into(cpic);

        }
        String sts=j.getvisiblity();
        if(sts.equalsIgnoreCase("1"))
        {
            btnupdate.setChecked(true);
        }
        else
        {
            btnupdate.setChecked(false);
        }
        btnupdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    final Query query = fDatabaseRoot.orderByChild("emailid").equalTo(j.getusename());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                String key = appleSnapshot.getKey();
                                //    update(key);
                                fDatabaseRoot.child(key).child("status").setValue("1");


                            }
                            Toast toast=Toast.makeText(getActivity(),"Profile Visible Public",Toast.LENGTH_SHORT);
                            toast.setMargin(50,50);
                            toast.show();
                            j.setvisiblity("1");


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Log.e(TAG, "onCancelled", databaseError.toException());

                        }
                    });


                }

                else {

                    final Query query = fDatabaseRoot.orderByChild("emailid").equalTo(j.getusename());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                String key = appleSnapshot.getKey();
                                //    update(key);
                                fDatabaseRoot.child(key).child("status").setValue("0");


                            }
                            Toast toast=Toast.makeText(getActivity(),"Profile Visible Private",Toast.LENGTH_SHORT);
                            toast.setMargin(50,50);
                            toast.show();
                            j.setvisiblity("0");


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Log.e(TAG, "onCancelled", databaseError.toException());

                        }
                    });
                }
            }
        });

        return root;
    }
}