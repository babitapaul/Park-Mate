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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Set;


public class MatchList extends Fragment {

    Matching_List_adpater matching_list_adpater;
    RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayAdapter<String> adapter;
    ArrayList<registration> registrations;
    Session st;
    ProgressBar pb;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_match_list, container, false);
        registrations=new ArrayList<registration>();
        recyclerView=(RecyclerView)view.findViewById(R.id.mymatchlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pb=(ProgressBar)view.findViewById(R.id.pbloading);
        st=new Session(getContext());
        getActivity().setTitle("Matching User List");
        getmatchinglist();





        return  view;
    }

    void getmatchinglist()
    {
        pb.setVisibility(View.VISIBLE);

        Set<String>h=st.getsecondsurvey();
        for(String g:h){
            System.out.println("value is "+g);
            registrations = new ArrayList<registration>();
            reference = FirebaseDatabase.getInstance().getReference("users");
            Query query = reference.orderByChild("gender").equalTo(g);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        //  System.out.println("value is "+dataSnapshot1.getChildren().toString());

                        System.out.println("hghghgh"+dataSnapshot1);
                        registration us = dataSnapshot1.getValue(registration.class);
                        System.out.println("test-------------------------------"+us.getusersurvey());
                       /*  for(String Myfilter:filter) {
                             if(Myfilter.equals(us.get))


                         } */
                        registrations.add(us);
                        matching_list_adpater=new Matching_List_adpater(getContext(),registrations);

                        recyclerView.setAdapter(matching_list_adpater);
                        pb.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }



    }

}
