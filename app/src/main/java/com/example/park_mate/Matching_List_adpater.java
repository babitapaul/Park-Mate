package com.example.park_mate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import java.util.Set;

public class Matching_List_adpater extends RecyclerView.Adapter<Matching_List_adpater.MyviewHolders> {

    Context context;
    ArrayList<registration> registrations;
    ArrayList<String>blockemailid=new ArrayList<>();
    Session st;
    int r=0;
    DatabaseReference reference;
    public Matching_List_adpater(Context c, ArrayList<registration> u) {
        context = c;
        registrations = u;




    }

    @NonNull
    @Override
    public Matching_List_adpater.MyviewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Matching_List_adpater.MyviewHolders(LayoutInflater.from(context).inflate(R.layout.matchlist_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final Matching_List_adpater.MyviewHolders holder, final int position) {
        st=new Session(context);
        final Set<String> filter = st.getparksurvey();
        if(st.getusename().equals(registrations.get(position).getEmailid()))
        {
            holder.name.setVisibility(View.GONE);
            holder.city.setVisibility(View.GONE);

            holder.gender.setVisibility(View.GONE);
            holder.btn.setVisibility(View.GONE);
            holder.userpic.setVisibility(View.GONE);
            holder.crd.setVisibility(View.GONE);
        }
        else
        {
            if(registrations.get(position).getusersurvey()!=null)
            {
                for(String y:registrations.get(position).getusersurvey()) {
                    if (filter.contains(y)) {

                        reference = FirebaseDatabase.getInstance().getReference("Block List");
                        Query query1 = reference.orderByChild("blockpersonemailid").equalTo(st.getusename().trim());
                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                    r++;
                                    Block_List ust = dataSnapshot1.getValue(Block_List.class);
                                    if(ust.getBlockbyemailid().equals(registrations.get(position).getEmailid()))
                                    {
                                        holder.name.setVisibility(View.GONE);
                                        holder.city.setVisibility(View.GONE);

                                        holder.gender.setVisibility(View.GONE);
                                        holder.btn.setVisibility(View.GONE);
                                        holder.userpic.setVisibility(View.GONE);
                                        holder.crd.setVisibility(View.GONE);
                                    }
                                    else {
                                        if(registrations.get(position).getStatus().equals("1"))
                                        {
                                            holder.name.setText("Name: " + registrations.get(position).getUsername());
                                            holder.city.setText("City: " + registrations.get(position).getCity());

                                            holder.gender.setText("Gender :- " + registrations.get(position).getGender());

                                            if (registrations.get(position).getImageurl().trim().isEmpty()) {
                                                Picasso.get().load(R.drawable.defultuser).into(holder.userpic);
                                            } else {
                                                Picasso.get().load(registrations.get(position).getImageurl()).into(holder.userpic);
                                            } }
                                        else
                                        {
                                            holder.name.setVisibility(View.GONE);
                                            holder.city.setVisibility(View.GONE);

                                            holder.gender.setVisibility(View.GONE);
                                            holder.btn.setVisibility(View.GONE);
                                            holder.userpic.setVisibility(View.GONE);
                                            holder.crd.setVisibility(View.GONE);
                                        }
                                    }

                                }
                                if(r==0)
                                {
                                    if(registrations.get(position).getStatus().equals("1")) {
                                        holder.name.setText("Name: " + registrations.get(position).getUsername());
                                        holder.city.setText("City: " + registrations.get(position).getCity());

                                        holder.gender.setText("Gender :- " + registrations.get(position).getGender());

                                        if (registrations.get(position).getImageurl().trim().isEmpty()) {
                                            Picasso.get().load(R.drawable.defultuser).into(holder.userpic);
                                        } else {
                                            Picasso.get().load(registrations.get(position).getImageurl()).into(holder.userpic);
                                        }
                                    }
                                    else
                                    {
                                        holder.name.setVisibility(View.GONE);
                                        holder.city.setVisibility(View.GONE);

                                        holder.gender.setVisibility(View.GONE);
                                        holder.btn.setVisibility(View.GONE);
                                        holder.userpic.setVisibility(View.GONE);
                                        holder.crd.setVisibility(View.GONE);
                                    }
                                }
                                System.out.println("value of r is "+r);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

//visibility gone krni

                    else
                    {
                        holder.name.setVisibility(View.GONE);
                        holder.city.setVisibility(View.GONE);

                        holder.gender.setVisibility(View.GONE);
                        holder.btn.setVisibility(View.GONE);
                        holder.userpic.setVisibility(View.GONE);
                        holder.crd.setVisibility(View.GONE);
                    }

                }

            }
            else
            {
                holder.name.setVisibility(View.GONE);
                holder.city.setVisibility(View.GONE);

                holder.gender.setVisibility(View.GONE);
                holder.btn.setVisibility(View.GONE);
                holder.userpic.setVisibility(View.GONE);
                holder.crd.setVisibility(View.GONE);
            }


        }


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, ViewUser_Timeline.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("username",registrations.get(position).getUsername());
                intent.putExtra("freqid","");
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

        TextView name,city,gender;
        ImageView userpic;
        CardView crd;
        Button btn;

        public MyviewHolders(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.username);
            city= (TextView) itemView.findViewById(R.id.usercity);
            gender = (TextView) itemView.findViewById(R.id.Usergender);
            userpic = (ImageView) itemView.findViewById(R.id.pic);
            btn = (Button) itemView.findViewById(R.id.viewuser);
            crd=(CardView)itemView.findViewById(R.id.crd);


        }


    }
}
