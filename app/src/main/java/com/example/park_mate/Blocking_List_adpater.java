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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Blocking_List_adpater extends RecyclerView.Adapter<Blocking_List_adpater.MyviewHolders> {

    Context context;
    ArrayList<Block_List> block_lists;

    public Blocking_List_adpater(Context c, ArrayList<Block_List> u) {
        context = c;
        block_lists = u;

    }

    @NonNull
    @Override
    public Blocking_List_adpater.MyviewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Blocking_List_adpater.MyviewHolders(LayoutInflater.from(context).inflate(R.layout.blocklist_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Blocking_List_adpater.MyviewHolders holder, final int position) {


              holder.name.setText("Name: " + block_lists.get(position).getBlockto());
              holder.time.setText("Time: " + block_lists.get(position).getTime());
              holder.Reason.setText("Reason: " + block_lists.get(position).getReason());



              if (block_lists.get(position).getBlocktoimage().trim().isEmpty()) {
                  Picasso.get().load(R.drawable.defultuser).into(holder.userpic);
              } else {
                  Picasso.get().load(block_lists.get(position).getBlocktoimage().trim()).into(holder.userpic);
              }

       holder.btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               DatabaseReference fDatabaseRoot;
               FirebaseDatabase database;
               database = FirebaseDatabase.getInstance();
               fDatabaseRoot = database.getReference("Block List");
               Query query = fDatabaseRoot.orderByChild("blockpersonemailid").equalTo(block_lists.get(position).getBlockpersonemailid());
               query.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                           appleSnapshot.getRef().removeValue();
                           Toast.makeText(context, "Unblock  Sucessfully", Toast.LENGTH_SHORT).show();

                       }


                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
                     block_lists.remove(position);
                     notifyDataSetChanged();
           }
       });



    }

    @Override
    public int getItemCount() {
        return block_lists.size();
    }

    public class MyviewHolders extends RecyclerView.ViewHolder {

        TextView name,time,Reason;
        ImageView userpic;
        Button btn;

        public MyviewHolders(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.username);
            time= (TextView) itemView.findViewById(R.id.time);
            Reason= (TextView) itemView.findViewById(R.id.Reason);
            userpic = (ImageView) itemView.findViewById(R.id.pic);
            btn = (Button) itemView.findViewById(R.id.unblock);



        }


    }
}
