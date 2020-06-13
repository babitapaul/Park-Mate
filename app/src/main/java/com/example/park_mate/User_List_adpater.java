package com.example.park_mate;

import android.content.Context;
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

public class User_List_adpater extends RecyclerView.Adapter<User_List_adpater.MyviewHolders> {

    Context context;
    ArrayList<registration> registrations;

    public User_List_adpater(Context c, ArrayList<registration> u) {
        context = c;
        registrations = u;
    }

    @NonNull
    @Override
    public User_List_adpater.MyviewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new User_List_adpater.MyviewHolders(LayoutInflater.from(context).inflate(R.layout.user_list_cardview, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull User_List_adpater.MyviewHolders holder, int position) {

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
        holder.onClick(position, registrations.get(position).getEmailid(),registrations.get(position).getPassword());

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
            btn = (Button) itemView.findViewById(R.id.deleteuser);


        }

        public void onClick(final int position, final String emailidid,final String password) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(emailidid, password);

                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, "User  Authentication deleted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });


                    DatabaseReference fDatabaseRoot;
                FirebaseDatabase database;
                database = FirebaseDatabase.getInstance();
                fDatabaseRoot = database.getReference("users");
                Query query = fDatabaseRoot.orderByChild("emailid").equalTo(emailidid);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                            //  Toast.makeText(context,"User Record Also Deleted Sucessfully",Toast.LENGTH_SHORT).show();
                        }
                        registrations.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, registrations.size());
                        
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                    });
                }
            });

        }
    }
}
