package com.example.park_mate;

import android.content.Context;
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

public class Manage_Comment_Adpater extends RecyclerView.Adapter<Manage_Comment_Adpater.MyviewHolders> {

    Context context;
    ArrayList<CommentPost> timelines;

    public Manage_Comment_Adpater(Context c, ArrayList<CommentPost> u) {
        context = c;
        timelines = u;

    }

    @NonNull
    @Override
    public Manage_Comment_Adpater.MyviewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Manage_Comment_Adpater.MyviewHolders(LayoutInflater.from(context).inflate(R.layout.manage_comment_read_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final Manage_Comment_Adpater.MyviewHolders holder, final int position) {

        holder.timepost.setText(timelines.get(position).getCommenttime());
        holder.comment.setText(timelines.get(position).getCommentmessage());
        holder.name.setText(timelines.get(position).getComemtpersonname()+":");


       if(timelines.get(position).getCommentpersonimage().trim().isEmpty()) {

       }
       else {
           Picasso.get().load(timelines.get(position).getCommentpersonimage()).into(holder.userimage);
       }




           holder.deletecomments.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   DatabaseReference fDatabaseRoot;
                   FirebaseDatabase database;
                   database = FirebaseDatabase.getInstance();
                   fDatabaseRoot = database.getReference("Comment Post");
                   Query query = fDatabaseRoot.orderByChild("commenttime").equalTo(timelines.get(position).getCommenttime());
                   query.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                               appleSnapshot.getRef().removeValue();
                               Toast.makeText(context, "Comment  deleted", Toast.LENGTH_SHORT).show();
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

        TextView timepost,comment,name;
        ImageView userimage,deletecomments;
        ImageView btn;

        public MyviewHolders(@NonNull View itemView) {
            super(itemView);

            timepost= (TextView) itemView.findViewById(R.id.timepost);
            comment= (TextView) itemView.findViewById(R.id.comment);
            name= (TextView) itemView.findViewById(R.id.name);
            userimage=(ImageView)itemView.findViewById(R.id.posttimelineimage);
            deletecomments=(ImageView)itemView.findViewById(R.id.deletecomment);

        }


    }
}
