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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Chat_Adapter extends RecyclerView.Adapter<Chat_Adapter.MyviewHolders> {

    Context context;
    ArrayList<ChatHistory>chatMessages;
    Session st;

    public Chat_Adapter(Context c, ArrayList<ChatHistory> u) {
        context = c;
        chatMessages = u;



    }

    @NonNull
    @Override
    public Chat_Adapter.MyviewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Chat_Adapter.MyviewHolders(LayoutInflater.from(context).inflate(R.layout.chat_read_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final Chat_Adapter.MyviewHolders holder, final int position) {

        st=new Session(context);
        holder.chtmsg.setText(chatMessages.get(position).getMessage());
        holder.time.setText(chatMessages.get(position).getTime());
        holder.name.setText(chatMessages.get(position).getChatpersoname()+" : ");
        if(st.getusename().equals(chatMessages.get(position).getChatemailid()))
        {
            holder.deletechat.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.deletechat.setVisibility(View.GONE);
        }
     /*   if(chatMessages.get(position).getChatpersonimage().isEmpty()) {

        }
        else {
            Picasso.get().load(chatMessages.get(position).getChatpersoname()).into(holder.imageView);
        } */
        holder.deletechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference fDatabaseRoot;
                FirebaseDatabase database;
                database = FirebaseDatabase.getInstance();
                fDatabaseRoot = database.getReference("ChatHistory");
                Query query = fDatabaseRoot.orderByChild("time").equalTo(chatMessages.get(position).getTime());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                            Toast.makeText(context, "Chat  deleted", Toast.LENGTH_SHORT).show();
                            chatMessages.removeAll(chatMessages);
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
        return chatMessages.size();
    }

    public class MyviewHolders extends RecyclerView.ViewHolder {

        TextView chtmsg,time,name;
        ImageView deletechat;

        public MyviewHolders(@NonNull View itemView) {
            super(itemView);
            chtmsg = (TextView) itemView.findViewById(R.id.chatmsg);
            time=(TextView)itemView.findViewById(R.id.time);
            name=(TextView)itemView.findViewById(R.id.name);
            deletechat=(ImageView)itemView.findViewById(R.id.deletechat);


        }


    }
}
