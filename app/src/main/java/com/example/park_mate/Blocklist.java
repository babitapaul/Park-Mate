package com.example.park_mate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Set;


public class Blocklist extends Fragment {
    Blocking_List_adpater blocking_list_adpater;
    RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayAdapter<String> adapter;
    ArrayList<Block_List> block_lists;
    Session st;
    ProgressBar pb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_blocklist, container, false);
        block_lists=new ArrayList<Block_List>();
        recyclerView=(RecyclerView)view.findViewById(R.id.myblocklist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pb=(ProgressBar)view.findViewById(R.id.pbloading);
        pb.setVisibility(View.INVISIBLE);
        st=new Session(getContext());
        getActivity().setTitle("BlockList User");
        getblocklist();
        return view;

    }


    void getblocklist()
    {
        pb.setVisibility(View.VISIBLE);


        block_lists = new ArrayList<Block_List>();
        reference = FirebaseDatabase.getInstance().getReference("Block List");
        Query query = reference.orderByChild("blockbyemailid").equalTo(st.getusename());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Block_List us = dataSnapshot1.getValue(Block_List.class);
                    block_lists.add(us);



                    blocking_list_adpater=new Blocking_List_adpater(getActivity(),block_lists);

                    recyclerView.setAdapter(blocking_list_adpater);
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
