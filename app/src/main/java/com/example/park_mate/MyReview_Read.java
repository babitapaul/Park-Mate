package com.example.park_mate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MyReview_Read extends Fragment {

    My_Profile_Review_Read_adpter profile_review_read_adpter;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_my_review__read, container, false);
        st=new Session(getContext());
        st.getname();
        currentuserimage=(ImageView)view.findViewById(R.id.mypic);
        writepost=(Button)view.findViewById(R.id.writepost);
        pb=(ProgressBar)view.findViewById(R.id.pbloading);
        pb.setVisibility(View.GONE);
        getActivity().setTitle("My Profile Review");
        write=(EditText)view.findViewById(R.id.wrt);
        recyclerView=(RecyclerView)view.findViewById(R.id.myreviewlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ReviewFetch();
        writepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setwritereview();
            }
        });

        return  view;
    }
    void setwritereview()
    {
        sjs.removeAll(sjs);
        pb.setVisibility(View.VISIBLE);
        String mydatetime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        databaserefrence= FirebaseDatabase.getInstance().getReference("Profile review");
        ProfileReview reviewPost=new ProfileReview(mydatetime,st.getusename(),st.getusename(),write.getText().toString(),st.getimageurl(),st.getname());
        String commentd=databaserefrence.push().getKey();
        databaserefrence.child(commentd).setValue(reviewPost);
        Toast toast=Toast.makeText(getContext(),"Review Post SuccessFully ",Toast.LENGTH_SHORT);
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
        Query query=databaserefrence.orderByChild("useremailidaboutreview").equalTo(st.getusename());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                    System.out.println(dataSnapshot);
                    ProfileReview us=dataSnapshot1.getValue(ProfileReview.class);
                    sjs.add(us);

                }

                profile_review_read_adpter=new My_Profile_Review_Read_adpter(getContext(),sjs);

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
