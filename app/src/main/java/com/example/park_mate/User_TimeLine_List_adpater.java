package com.example.park_mate;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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

public class User_TimeLine_List_adpater extends RecyclerView.Adapter<User_TimeLine_List_adpater.MyviewHolders> {

    Context context;
    ArrayList<Timeline> timelines;
    Session stss;
    public User_TimeLine_List_adpater(Context c, ArrayList<Timeline> u) {
        context = c;
        timelines = u;
    }

    @NonNull
    @Override
    public User_TimeLine_List_adpater.MyviewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new User_TimeLine_List_adpater.MyviewHolders(LayoutInflater.from(context).inflate(R.layout.timeline_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull User_TimeLine_List_adpater.MyviewHolders holder, final int position) {

        stss=new Session(context);
        holder.whatpostmsg.setText(timelines.get(position).getPostmessage());
        holder.timepost.setText(timelines.get(position).getTime());
        holder.parkname.setText("Park:"+timelines.get(position).getParkname());
        holder.username.setText("User:"+timelines.get(position).getUsername());

        if(timelines.get(position).getUserimageurl().trim().isEmpty()) {
            Picasso.get().load(R.drawable.userimage).into(holder.userpic);
        }
        else
        {
            Picasso.get().load(timelines.get(position).getUserimageurl()).into(holder.userpic);
        }

        Picasso.get().load(timelines.get(position).getParkimageurl()).into(holder.whatpostimage);
        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,MycommentRead.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("currentimelinekey",timelines.get(position).getTimelineid());
                context.startActivity(intent);

            }
        });
        if(timelines.get(position).getEmailid().equals(stss.getusename())) {
            holder.onClick(position, timelines.get(position).getTime());
            holder.btn.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.btn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return timelines.size();
    }

    public class MyviewHolders extends RecyclerView.ViewHolder {

        TextView whatpostmsg, timepost,parkname,username;
        ImageView userpic,whatpostimage;
        ImageView btn,reply;

        public MyviewHolders(@NonNull View itemView) {
            super(itemView);
            whatpostmsg= (TextView) itemView.findViewById(R.id.whatpost);
            timepost= (TextView) itemView.findViewById(R.id.timepost);
            parkname= (TextView) itemView.findViewById(R.id.parkname);
            username= (TextView) itemView.findViewById(R.id.username);

            userpic = (ImageView) itemView.findViewById(R.id.posttimelineimage);
            whatpostimage = (ImageView) itemView.findViewById(R.id.whatpostimage);
            btn =(ImageView) itemView.findViewById(R.id.deletepost);
            reply=(ImageView)itemView.findViewById(R.id.reply);

        }

        public void onClick(final int position, final String time) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {





                    DatabaseReference fDatabaseRoot;
                    FirebaseDatabase database;
                    database = FirebaseDatabase.getInstance();
                    fDatabaseRoot = database.getReference("UserTimeline");
                    Query query = fDatabaseRoot.orderByChild("time").equalTo(time);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                                Toast.makeText(context, "TimeLine Post deleted", Toast.LENGTH_SHORT).show();
                            }
                            timelines.removeAll(timelines);


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