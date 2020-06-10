package com.example.park_mate;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragmentmanageparks extends Fragment {

    EditText Parkname;
    Button prkbtn,parkdelete,parkupdate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manageparks,container,false);
        Parkname=(EditText)view.findViewById(R.id.prkn);
        prkbtn=(Button)view.findViewById(R.id.prkadd);
        parkdelete=(Button)view.findViewById(R.id.delete);
        parkupdate=(Button)view.findViewById(R.id.modify);
        prkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String h=Parkname.getText().toString();

                if (TextUtils.isEmpty(h.trim()))
                {
                    Toast toast = Toast.makeText(getContext(), "Please Enter Park Name!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();

                    return;
                }
                Intent i = new Intent(getContext(),ParkAdd.class);
                i.putExtra("Parkname",h);
                startActivity(i);
            }
        });
        parkdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(),DeletePark.class);

                startActivity(i);
            }
        });
        parkupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),Parkupdate.class);

                startActivity(i);
            }
        });
        return view;
    }
}
