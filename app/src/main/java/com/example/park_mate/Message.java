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
import java.util.Set;


public class Message extends Fragment {


    Message_uer__List_adpater message_uer__list_adpater;
    ArrayList<ChatMessage> chatMessages;
    RecyclerView recyclerView;
    private Session session;
    ProgressBar pb;
    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        session=new Session(getContext());
        View view= inflater.inflate(R.layout.fragment_message, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.messageuserlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pb=(ProgressBar)view.findViewById(R.id.pbloading);
        pb.setVisibility(View.VISIBLE);
        getActivity().setTitle("Message");

        chatMessages = new ArrayList<ChatMessage>();
        reference = FirebaseDatabase.getInstance().getReference("Message");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //  System.out.println("value is "+dataSnapshot1.getChildren().toString());

                    ChatMessage us = dataSnapshot1.getValue(ChatMessage.class);
                    if(us.getToemailid().equals(session.getusename()) || us.getFromemailid().equals(session.getusename())) {
                        chatMessages.add(us);



                    }
                    message_uer__list_adpater = new Message_uer__List_adpater(getContext(), chatMessages);

                    recyclerView.setAdapter(message_uer__list_adpater);
                    pb.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        return  view;
    }
}
