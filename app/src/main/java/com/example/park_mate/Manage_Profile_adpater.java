package com.example.park_mate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Manage_Profile_adpater extends RecyclerView.Adapter<Manage_Profile_adpater.MyviewHolders> {

    Context context;
    ArrayList<registration> registrations;

    public Manage_Profile_adpater(Context c, ArrayList<registration> u) {
        context = c;
        registrations = u;
    }

    @NonNull
    @Override
    public Manage_Profile_adpater.MyviewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Manage_Profile_adpater.MyviewHolders(LayoutInflater.from(context).inflate(R.layout.manage_profile_cardview, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Manage_Profile_adpater.MyviewHolders holder, final int position) {

        holder.name.setText("UserName: "+registrations.get(position).getUsername());
        holder.Emailid.setText("Emailid:- " + registrations.get(position).getEmailid());

        holder.Password.setText("Password :- " + registrations.get(position).getPassword());

        if(registrations.get(position).getImageurl().trim().isEmpty()) {
            Picasso.get().load(R.drawable.defultuser).into(holder.userpic);
        }
        else
        {
            Picasso.get().load(registrations.get(position).getImageurl()).into(holder.userpic);
        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Control_Profile.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("username",registrations.get(position).getUsername());
                intent.putExtra("emailid",registrations.get(position).getEmailid());
                intent.putExtra("usercity",registrations.get(position).getCity());
                intent.putExtra("usergender",registrations.get(position).getGender());
                intent.putExtra("userimageurl",registrations.get(position).getImageurl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return registrations.size();
    }

    public class MyviewHolders extends RecyclerView.ViewHolder {

        TextView name, Emailid,Password;
        ImageView userpic;
        Button btn;

        public MyviewHolders(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Uname);
            Emailid= (TextView) itemView.findViewById(R.id.Uemail);
            Password = (TextView) itemView.findViewById(R.id.Upassword);
            userpic = (ImageView) itemView.findViewById(R.id.userpic);
            btn = (Button) itemView.findViewById(R.id.sd);


        }



    }
}

