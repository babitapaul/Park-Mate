package com.example.park_mate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class dashboardfragment extends Fragment {

     ImageView btn,btn2,btn3;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard,container,false);
          btn=(ImageView)view.findViewById(R.id.mngprk);
          btn2=(ImageView)view.findViewById(R.id.mnguser);
        btn3=(ImageView)view.findViewById(R.id.mngsurv);

          btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  fragmentManager = getFragmentManager();
                  fragmentTransaction = fragmentManager.beginTransaction();
                  fragmentTransaction.replace(R.id.container_fragment, new fragmentmanageparks());
                  fragmentTransaction.commit();
              }
          });
          btn2.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  fragmentManager = getFragmentManager();
                  fragmentTransaction = fragmentManager.beginTransaction();
                  fragmentTransaction.replace(R.id.container_fragment, new manageuserfragment());
                  fragmentTransaction.commit();
              }
          });
          btn3.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  fragmentManager = getFragmentManager();
                  fragmentTransaction = fragmentManager.beginTransaction();
                  fragmentTransaction.replace(R.id.container_fragment, new fragmentManagesurvey());
                  fragmentTransaction.commit();
              }
          });
        return view;
    }
}
