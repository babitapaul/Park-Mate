package com.example.park_mate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Manage_User_TimeLine_List_adpater extends RecyclerView.Adapter<Manage_User_TimeLine_List_adpater.MyviewHolders> {

    Context context;
    ArrayList<Timeline> timelines;

    public Manage_User_TimeLine_List_adpater(Context c, ArrayList<Timeline> u) {
        context = c;
        timelines = u;

    }

    @NonNull
    @Override
    public Manage_User_TimeLine_List_adpater.MyviewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Manage_User_TimeLine_List_adpater.MyviewHolders(LayoutInflater.from(context).inflate(R.layout.manage_view_user_timeline_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final Manage_User_TimeLine_List_adpater.MyviewHolders holder, final int position) {

        holder.whatpostmsg.setText(timelines.get(position).getPostmessage());
        holder.timepost.setText(timelines.get(position).getTime());
        holder.parkname.setText("Park:"+timelines.get(position).getParkname());


       if(timelines.get(position).getUserimageurl().trim().isEmpty()) {
           Picasso.get().load(R.drawable.userimage).into(holder.userpic);
       }
       else
       {
           Picasso.get().load(timelines.get(position).getUserimageurl()).into(holder.userpic);
       }

            Picasso.get().load(timelines.get(position).getParkimageurl()).into(holder.whatpostimage);

          holder.btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent= new Intent(context,ManageComment.class);
                  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  intent.putExtra("currentimelinekey",timelines.get(position).getTimelineid());
                  context.startActivity(intent);

              }
          });
          holder.deletetimline.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  DatabaseReference fDatabaseRoot;
                  FirebaseDatabase database;
                  database = FirebaseDatabase.getInstance();
                  fDatabaseRoot = database.getReference("UserTimeline");
                  Query query = fDatabaseRoot.orderByChild("timelineid").equalTo(timelines.get(position).getTimelineid());
                  query.addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                          for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                              appleSnapshot.getRef().removeValue();
                              Toast.makeText(context, "Selected TimeLine  deleted", Toast.LENGTH_SHORT).show();
                              timelines.removeAll(timelines);
                          }



                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
                  });
              }
          });


    }

    @Override
    public int getItemCount() {
        return timelines.size();
    }

    public class MyviewHolders extends RecyclerView.ViewHolder {

        TextView whatpostmsg, timepost,parkname;
        ImageView userpic,whatpostimage;
        ImageView btn,deletetimline;

        public MyviewHolders(@NonNull View itemView) {
            super(itemView);
            whatpostmsg= (TextView) itemView.findViewById(R.id.whatpost);
            timepost= (TextView) itemView.findViewById(R.id.timepost);
            parkname= (TextView) itemView.findViewById(R.id.parkname);

            userpic = (ImageView) itemView.findViewById(R.id.posttimelineimage);
            whatpostimage = (ImageView) itemView.findViewById(R.id.whatpostimage);
            btn =(ImageView) itemView.findViewById(R.id.reply);
            deletetimline =(ImageView) itemView.findViewById(R.id.deletetimline);


        }

        public void onClick(final int position, final String time) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
              /*      FragmentManager manager = ((AppCompatActivity)View.getContext()).getFragmentManager();

                    comment_Dialog t=new comment_Dialog();
                    t.show(manager,"Comment"); */


/*
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
                                Toast.makeText(context, "TimeLine Posted deleted", Toast.LENGTH_SHORT).show();
                            }
                            timelines.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, timelines.size());


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    }); */
                }
            });

        }
    }
}
