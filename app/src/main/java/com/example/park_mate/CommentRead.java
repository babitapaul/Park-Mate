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
import android.widget.ImageView;
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

public class CommentRead extends AppCompatActivity {

    String Timlinekey;
    Session st;
    ImageView currentuserimage;
    EditText commentwrite;
    Button postcomment;
    RecyclerView recyclerView;
    ProgressBar pb;
    ArrayList<CommentPost> sjs;
    Comment_Read_Wallpost_TimeLine_List_adpater comment_read_wallpost_timeLine_list_adpater;
    private DatabaseReference databaserefrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_read);
        st=new Session(getApplicationContext());
        st.getname();
        Intent intent = getIntent();
        Timlinekey=intent.getStringExtra("currentimelinekey");
        currentuserimage=(ImageView)findViewById(R.id.mypic);
        postcomment=(Button)findViewById(R.id.commentpost);
        pb=(ProgressBar)findViewById(R.id.pbloading);
        pb.setVisibility(View.GONE);
        commentwrite=(EditText)findViewById(R.id.comment);
        recyclerView=(RecyclerView)findViewById(R.id.viewtimelinerecylers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        fetchcomment();
        postcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setPostcomment();
            }
        });

    }
    void setPostcomment()
    {
        sjs.removeAll(sjs);
        pb.setVisibility(View.VISIBLE);
        String mydatetime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        databaserefrence= FirebaseDatabase.getInstance().getReference("Comment Post");
        CommentPost commentPost=new  CommentPost(st.getimageurl(),Timlinekey,st.getname(),st.getusename(),commentwrite.getText().toString(),st.getimageurl(),mydatetime);
        String commentd=databaserefrence.push().getKey();
        databaserefrence.child(commentd).setValue(commentPost);
        Toast toast=Toast.makeText(getApplicationContext(),"Comment Post SuccessFully ",Toast.LENGTH_SHORT);
        toast.setMargin(50,50);
        toast.show();
        commentwrite.setText(null);
        pb.setVisibility(View.GONE);
    }
    void fetchcomment()
    {
        pb.setVisibility(View.VISIBLE);

        sjs=new ArrayList<CommentPost>();
        databaserefrence= FirebaseDatabase.getInstance().getReference("Comment Post");
        Query query=databaserefrence.orderByChild("wallpostid").equalTo(Timlinekey);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                    System.out.println(dataSnapshot);
                    CommentPost us=dataSnapshot1.getValue(CommentPost.class);
                    sjs.add(us);

                }

                comment_read_wallpost_timeLine_list_adpater=new Comment_Read_Wallpost_TimeLine_List_adpater(getApplicationContext(),sjs);

                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(comment_read_wallpost_timeLine_list_adpater);
                comment_read_wallpost_timeLine_list_adpater.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
