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

public class Comment_Read_Wallpost_TimeLine_List_adpater extends RecyclerView.Adapter<Comment_Read_Wallpost_TimeLine_List_adpater.MyviewHolders> {

    Context context;
    ArrayList<CommentPost> timelines;
    Session st;
    public Comment_Read_Wallpost_TimeLine_List_adpater(Context c, ArrayList<CommentPost> u) {
        context = c;
        timelines = u;
        st=new Session(context);
    }

    @NonNull
    @Override
    public Comment_Read_Wallpost_TimeLine_List_adpater.MyviewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Comment_Read_Wallpost_TimeLine_List_adpater.MyviewHolders(LayoutInflater.from(context).inflate(R.layout.comment_read_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final Comment_Read_Wallpost_TimeLine_List_adpater.MyviewHolders holder, final int position) {

        holder.timepost.setText(timelines.get(position).getCommenttime());
        holder.comment.setText(timelines.get(position).getCommentmessage());
        holder.name.setText(timelines.get(position).getComemtpersonname()+":");


       if(timelines.get(position).getCommentpersonimage().trim().isEmpty()) {

       }
       else {
           Picasso.get().load(timelines.get(position).getCommentpersonimage()).into(holder.userimage);
       }
         if(timelines.get(position).getComentpersonemailid().equals(st.getusename()))
         {
             holder.deletecomments.setVisibility(View.VISIBLE);
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
