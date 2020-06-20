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

public class Profile_Review_Read_adpter extends RecyclerView.Adapter<Profile_Review_Read_adpter.MyviewHolders> {

    Context context;
    ArrayList<ProfileReview> profileReviews;
    Session st;
    public Profile_Review_Read_adpter(Context c, ArrayList<ProfileReview> u) {
        context = c;
        profileReviews = u;
        st=new Session(context);
    }

    @NonNull
    @Override
    public Profile_Review_Read_adpter.MyviewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Profile_Review_Read_adpter.MyviewHolders(LayoutInflater.from(context).inflate(R.layout.review_read_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final Profile_Review_Read_adpter.MyviewHolders holder, final int position) {

        holder.timepost.setText(profileReviews.get(position).getTime());
        holder.comment.setText(profileReviews.get(position).getReviewmsg());
        holder.name.setText(profileReviews.get(position).getPostreviewpersonname()+":");


        if(profileReviews.get(position).getImagereviewperson().trim().isEmpty()) {

        }
        else {
            Picasso.get().load(profileReviews.get(position).getImagereviewperson()).into(holder.userimage);
        }
        if(profileReviews.get(position).getPersonwhoreview().equals(st.getusename()))
        {
            holder.deletecomments.setVisibility(View.VISIBLE);
        }

        holder.deletecomments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference fDatabaseRoot;
                FirebaseDatabase database;
                database = FirebaseDatabase.getInstance();
                fDatabaseRoot = database.getReference("Profile review");
                Query query = fDatabaseRoot.orderByChild("time").equalTo(profileReviews.get(position).getTime());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                            Toast.makeText(context, "Review  deleted", Toast.LENGTH_SHORT).show();
                            profileReviews.removeAll(profileReviews);
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

        return profileReviews.size();
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
