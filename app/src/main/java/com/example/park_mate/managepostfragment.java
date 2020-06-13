package com.example.park_mate;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class managepostfragment extends Fragment {

    private manageuserfragment.onFragmentBtnSelected listener;
    Manage_Profile_adpater user_list_adpater;
    ArrayList<registration> registrations;
    RecyclerView recyclerView;
    ProgressBar pb;
    DatabaseReference reference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_managepost,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.manageprofiles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        registrations=new ArrayList<registration>();
        pb=(ProgressBar)view.findViewById(R.id.loadingProgress);
        getActivity().setTitle("Pofile Control");
        reference= FirebaseDatabase.getInstance().getReference("users");
        pb.setVisibility(View.VISIBLE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {

                    registration us=dataSnapshot1.getValue(registration.class);
                    registrations.add(us);
                }
                user_list_adpater=new Manage_Profile_adpater(getActivity(),registrations);

                recyclerView.setAdapter(user_list_adpater);
                pb.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof manageuserfragment.onFragmentBtnSelected) {
            listener = (manageuserfragment.onFragmentBtnSelected) context;
        }
        else
        {
            throw new ClassCastException(context.toString() + "must implement listener");
        }
        listener = (manageuserfragment.onFragmentBtnSelected) context;
    }

    public interface onFragmentBtnSelected{
        public void onButtonSelected();
    }
}

