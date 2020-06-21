package com.example.park_mate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Message_uer__List_adpater extends RecyclerView.Adapter<Message_uer__List_adpater.MyviewHolders> {

    Context context;
    ArrayList<ChatMessage>chatMessages;
    Session st;
    public Message_uer__List_adpater(Context c, ArrayList<ChatMessage> u) {
        context = c;
        chatMessages = u;



    }

    @NonNull
    @Override
    public Message_uer__List_adpater.MyviewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Message_uer__List_adpater.MyviewHolders(LayoutInflater.from(context).inflate(R.layout.messagelist_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final Message_uer__List_adpater.MyviewHolders holder, final int position) {


                             st=new Session(context);

                             if(st.getusename().equals(chatMessages.get(position).getToemailid())) {
                                 holder.username.setText("Name: " + chatMessages.get(position).getSendername());
                             }
                             else
                             {
                                 holder.username.setText("Name: " + chatMessages.get(position).getToname());
                             }
                          if (chatMessages.get(position).getSenderimageurl().trim().isEmpty())
                              {
                                  Picasso.get().load(R.drawable.defultuser).into(holder.userpic);
                              } else {
                                  Picasso.get().load(chatMessages.get(position).getSenderimageurl()).into(holder.userpic);
                              }

       holder.btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent= new Intent(context, Chat.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               intent.putExtra("chatid",chatMessages.get(position).getChatid());
               context.startActivity(intent);
           }
       });



    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public class MyviewHolders extends RecyclerView.ViewHolder {

        TextView username;
        ImageView userpic;

        Button btn;

        public MyviewHolders(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);

            userpic = (ImageView) itemView.findViewById(R.id.pic);
            btn = (Button) itemView.findViewById(R.id.Message);



        }


    }
}
