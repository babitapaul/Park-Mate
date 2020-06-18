package com.example.park_mate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Chat extends AppCompatActivity {

    RecyclerView recyclerView;

    private DatabaseReference reference;
    String chatid;
    ArrayList<ChatHistory> chatMessages;
    //  ProgressBar pb;
    Chat_Adapter chat_adapter;
    EditText writechat;
    Button sendchat;
    Session mysession;
    List<String> mymeesage;
    private DatabaseReference databaserefrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mysession=new Session(getApplicationContext());
        mymeesage=new ArrayList<>();
        Intent intent = getIntent();
        //pb=(ProgressBar)findViewById(R.id.pbloading);
        recyclerView=(RecyclerView)findViewById(R.id.chatlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        chatid=intent.getStringExtra("chatid");
        writechat=(EditText)findViewById(R.id.writechatmsg);
        sendchat=(Button)findViewById(R.id.sendchat);

        getchat();
        sendchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagesend();
            }
        });
    }
    void getchat()
    {

        chatMessages=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("ChatHistory");
        Query query=reference.orderByChild("chatid").equalTo(chatid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    ChatHistory us=dataSnapshot1.getValue(ChatHistory.class);
                    chatMessages.add(us);

                }
                chat_adapter=new Chat_Adapter(getApplicationContext(),chatMessages);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(chat_adapter);
                // pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    void messagesend()
    {
        chatMessages.removeAll(chatMessages);
        //    pb.setVisibility(View.VISIBLE);
        String mydatetime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        databaserefrence= FirebaseDatabase.getInstance().getReference("ChatHistory");
        ChatHistory chatHistory=new ChatHistory(mysession.getimageurl(),chatid,mysession.getname(),mysession.getusename(),writechat.getText().toString(),mydatetime);
        String commentd=databaserefrence.push().getKey();
        databaserefrence.child(commentd).setValue(chatHistory);
        writechat.setText(null);
        //  pb.setVisibility(View.GONE);
    }
}
