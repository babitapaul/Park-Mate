package com.example.park_mate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class WriteReview extends AppCompatActivity {

    Profile_Review_Read_adpter profile_review_read_adpter;
    Session st;
    ImageView currentuserimage;
    EditText write;
    Button writepost;
    RecyclerView recyclerView;
    ProgressBar pb;
    String userrevireemailid,nm;
    private DatabaseReference databaserefrence;

    ArrayList<ProfileReview> sjs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        st=new Session(getApplicationContext());
        st.getname();
        Intent intent = getIntent();

        userrevireemailid=intent.getStringExtra("profilereviewemailid");
        nm=intent.getStringExtra("username");
        currentuserimage=(ImageView)findViewById(R.id.mypic);
        writepost=(Button)findViewById(R.id.writepost);

        pb=(ProgressBar)findViewById(R.id.pbloading);
        pb.setVisibility(View.GONE);
        write=(EditText)findViewById(R.id.wrt);
        recyclerView=(RecyclerView)findViewById(R.id.writetrecyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ReviewFetch();
        writepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setwritereview();
            }
        });

    }
    void setwritereview()
    {
        sjs.removeAll(sjs);
        pb.setVisibility(View.VISIBLE);
        String mydatetime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        databaserefrence= FirebaseDatabase.getInstance().getReference("Profile review");
        ProfileReview reviewPost=new ProfileReview(mydatetime,st.getusename(),userrevireemailid,write.getText().toString(),st.getimageurl(),st.getname());
        String commentd=databaserefrence.push().getKey();
        databaserefrence.child(commentd).setValue(reviewPost);
        Toast toast=Toast.makeText(getApplicationContext(),"Review Post SuccessFully ",Toast.LENGTH_SHORT);
        toast.setMargin(50,50);
        toast.show();
        write.setText(null);
        pb.setVisibility(View.GONE);
    }

    void  ReviewFetch()
    {
        pb.setVisibility(View.VISIBLE);

        sjs=new ArrayList<ProfileReview>();
        databaserefrence= FirebaseDatabase.getInstance().getReference("Profile review");
        Query query=databaserefrence.orderByChild("useremailidaboutreview").equalTo(userrevireemailid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                    System.out.println(dataSnapshot);
                    ProfileReview us=dataSnapshot1.getValue(ProfileReview.class);
                    sjs.add(us);

                }

                profile_review_read_adpter=new Profile_Review_Read_adpter(getApplicationContext(),sjs);

                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(profile_review_read_adpter);
                profile_review_read_adpter.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
