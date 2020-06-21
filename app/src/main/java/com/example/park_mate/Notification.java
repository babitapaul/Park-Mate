package com.example.park_mate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Notification extends Fragment {


    RecyclerView recyclerView;
    Friend_List_Notification_adpater friend_list_notification_adpater;

    DatabaseReference reference;
    ArrayList<Friend_Request> friend_requests;
    Session st;
    ProgressBar pb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notification, container, false);

        friend_requests=new ArrayList<Friend_Request>();
        recyclerView=(RecyclerView)view.findViewById(R.id.friendlistnotification);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pb=(ProgressBar)view.findViewById(R.id.pbloading);
        pb.setVisibility(View.INVISIBLE);
        st=new Session(getContext());
        getActivity().setTitle("FriendList Notification");
        getfriendlistNotfiation();


        return view;
    }
    void getfriendlistNotfiation()
    {
        pb.setVisibility(View.VISIBLE);



        reference = FirebaseDatabase.getInstance().getReference("Friend Request");
        Query query = reference.orderByChild("fromemailid").equalTo(st.getusename());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Friend_Request us = dataSnapshot1.getValue(Friend_Request.class);
                    friend_requests.add(us);



                    friend_list_notification_adpater=new Friend_List_Notification_adpater(getActivity(),friend_requests);

                    recyclerView.setAdapter(friend_list_notification_adpater);
                    pb.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        pb.setVisibility(View.INVISIBLE);

    }
}

