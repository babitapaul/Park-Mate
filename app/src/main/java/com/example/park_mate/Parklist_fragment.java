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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Set;


public class Parklist_fragment extends Fragment {

    Park_List_adpater park_list_adpater;
    ArrayList<Park>parks;
    RecyclerView recyclerView;
    Session st;
    ProgressBar pb;
    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_parklist_fragment, container, false);
        parks=new ArrayList<Park>();
        recyclerView=(RecyclerView)view.findViewById(R.id.parkmatchlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pb=(ProgressBar)view.findViewById(R.id.pbloading);
        st=new Session(getContext());
        getActivity().setTitle("Park List");
        getmatchingparklist();
        return view;
    }
    void getmatchingparklist() {
        pb.setVisibility(View.VISIBLE);
        Set<String> h = st.getparksurvey();
        for (String g : h) {

            parks = new ArrayList<Park>();
            reference = FirebaseDatabase.getInstance().getReference("ParkRecord");
            Query query = reference.orderByChild("type").equalTo(g);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        Park us = dataSnapshot1.getValue(Park.class);
                        parks.add(us);
                        park_list_adpater = new Park_List_adpater(getContext(), parks);
                        recyclerView.setAdapter(park_list_adpater);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        if(parks.size()==0)
        {
            Toast toast = Toast.makeText(getContext(), "No Park Found According To Your Interest", Toast.LENGTH_SHORT);
            toast.setMargin(50, 50);
            toast.show();
        }
        pb.setVisibility(View.INVISIBLE);
    }
}
