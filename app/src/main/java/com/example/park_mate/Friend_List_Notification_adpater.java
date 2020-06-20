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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Friend_List_Notification_adpater extends RecyclerView.Adapter<Friend_List_Notification_adpater.MyviewHolders> {

    Context context;
    ArrayList<Friend_Request> friend_requests;

    public Friend_List_Notification_adpater(Context c, ArrayList<Friend_Request> u) {
        context = c;
        friend_requests = u;

    }

    @NonNull
    @Override
    public Friend_List_Notification_adpater.MyviewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Friend_List_Notification_adpater.MyviewHolders(LayoutInflater.from(context).inflate(R.layout.friendlist_notifcation_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Friend_List_Notification_adpater.MyviewHolders holder, final int position) {




        if(friend_requests.get(position).getStatus().equals("Pending"))
        {

            holder.name.setText("Name: " + friend_requests.get(position).getFromname());
            holder.status.setText("Status: " + friend_requests.get(position).getStatus());
            holder.s.setVisibility(View.VISIBLE);
            holder.btn.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.btn.setVisibility(View.GONE);
            holder.btn2.setVisibility(View.GONE);
            holder.name.setVisibility(View.GONE);
            holder.status.setVisibility(View.GONE);
            holder.s.setVisibility(View.GONE);
        }


        if (friend_requests.get(position).getFromimageurl().trim().isEmpty()) {
            Picasso.get().load(R.drawable.defultuser).into(holder.userpic);
        } else {
            Picasso.get().load(friend_requests.get(position).getFromimageurl().trim()).into(holder.userpic);
        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference fDatabaseRoot;
                FirebaseDatabase database;
                database = FirebaseDatabase.getInstance();
                fDatabaseRoot = database.getReference("Friend Request");
                Query query = fDatabaseRoot.orderByChild("requestid").equalTo(friend_requests.get(position).getRequestid());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                            Toast.makeText(context, "Friend Request Rejected", Toast.LENGTH_SHORT).show();

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                friend_requests.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference fDatabaseRoot;
                FirebaseDatabase database;
                database = FirebaseDatabase.getInstance();
                fDatabaseRoot = database.getReference("Friend Request");
                Query query = fDatabaseRoot.orderByChild("requestid").equalTo(friend_requests.get(position).getRequestid());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            String key = appleSnapshot.getKey();
                            //    update(key);
                            fDatabaseRoot.child(key).child("status").setValue("Approved");
                            Toast.makeText(context, "Friend Request Accepted", Toast.LENGTH_SHORT).show();

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                friend_requests.remove(position);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return friend_requests.size();
    }

    public class MyviewHolders extends RecyclerView.ViewHolder {

        TextView name,status;
        ImageView userpic;
        Button btn,btn2;
        CardView s;

        public MyviewHolders(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.username);
            status= (TextView) itemView.findViewById(R.id.status);

            userpic = (ImageView) itemView.findViewById(R.id.pic);
            btn = (Button) itemView.findViewById(R.id.reject);
            btn2 = (Button) itemView.findViewById(R.id.accept);
            s=(CardView)itemView.findViewById(R.id.crd);



        }


    }
}
