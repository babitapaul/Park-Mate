package com.example.park_mate;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


public class fragmentManagesurvey extends Fragment {

    Button add,dt,up;
    EditText question;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_managesurveys,container,false);
        add=(Button)view.findViewById(R.id.add);
        dt=(Button)view.findViewById(R.id.delete);
        getActivity().setTitle("Manage Survey");
        question=(EditText)view.findViewById(R.id.surveryquestion);
        up=(Button)view.findViewById(R.id.modify);
        dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),DeleteSurvey.class);

                startActivity(i);
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),UpdateSurvey.class);

                startActivity(i);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(question.getText().toString().trim()))
                {
                    Toast toast = Toast.makeText(getContext(), "Please Enter Survey Question!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();

                    return;
                }
                Intent i = new Intent(getContext(),Add_Survery.class);
                i.putExtra("Question",question.getText().toString());
                startActivity(i);
            }
        });
        return view;
    }
}
