package com.example.park_mate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class Send_Message extends AppCompatActivity {
    private Session mysesion;
    ImageView ss;
    EditText wrtmsg;
    Button SendMessage,home;
    DatabaseReference databaseReference;
    String toimageurl,toname,toemailid;
    List<String> mymeesage;
    GifImageView s;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mymeesage=new ArrayList<>();
        setContentView(R.layout.activity_send__message);
        wrtmsg=(EditText)findViewById(R.id.wrtmsg);
        SendMessage=(Button)findViewById(R.id.SendMessage);
        home=(Button)findViewById(R.id.home);
        ss=(ImageView)findViewById(R.id.message);
        mysesion=new Session(getApplicationContext());
        s=(GifImageView)findViewById(R.id.success);
        final Intent intent = getIntent();
        toname=intent.getStringExtra("toname");
        toimageurl=intent.getStringExtra("toimage");
        toemailid=intent.getStringExtra("toemailid");
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Userwelcome.class));
            }
        });

        SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(wrtmsg.getText().toString().trim())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Write Your Message", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    return;

                }
                setSendMessage();
            }
        });


    }
    void setSendMessage()
    {

        mymeesage.add(mysesion.getname()+":"+wrtmsg.getText().toString());

        databaseReference = FirebaseDatabase.getInstance().getReference("Message");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //  System.out.println("value is "+dataSnapshot1.getChildren().toString());
                    ChatMessage st=dataSnapshot1.getValue(ChatMessage.class);
                    if(st.getToemailid().equals(toemailid) && st.getFromemailid().equals(mysesion.getusename())) {
                        String mydatetime1 = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                        String commentd = databaseReference.push().getKey();
                        databaseReference.child(dataSnapshot1.getKey()).child("chat").setValue(mymeesage);
                        // databaseReference.push().child(commentd).setValue(wrtmsg.getText().toString());
                        i++;
                        Toast toast1 = Toast.makeText(getApplicationContext(), "Message Send SuccessFully ", Toast.LENGTH_SHORT);
                        toast1.setMargin(50, 50);
                        toast1.show();
                        ss.setVisibility(View.GONE);
                        s.setVisibility(View.VISIBLE);

                        home.setText("Go Back TO Home & Check Inbox");
                        wrtmsg.setVisibility(View.GONE);
                        SendMessage.setVisibility(View.GONE);
                        wrtmsg.setText(null);
                    }


                }
                if(i==0)
                {
                    String mydatetime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    String messageid=databaseReference.push().getKey();
                    ChatMessage Postmessage=new ChatMessage(messageid,toemailid,mysesion.getusename(),mysesion.getname(),mysesion.getimageurl(),mymeesage,mydatetime,toimageurl,toname);

                    databaseReference.child(messageid).setValue(Postmessage);
                    SendMessage.setVisibility(View.GONE);
                    wrtmsg.setVisibility(View.GONE);
                    wrtmsg.setText(null);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
