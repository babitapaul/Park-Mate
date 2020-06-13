package com.example.park_mate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateOption_Survey extends AppCompatActivity {

    List<String> questionlistoption = new ArrayList<String>();
    List<String> Fquestionlistoption = new ArrayList<String>();
    String getquestionname;
    ArrayAdapter<String> adapter;;
    EditText questioname,optionnewvalue;
    private ProgressBar pgsBar;
    Button updateit;
    private DatabaseReference fDatabaseRoot;
    private FirebaseDatabase database;
    Spinner spinlist;
    Survey kk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_option__survey);
        pgsBar=(ProgressBar)findViewById(R.id.pbloading);
        updateit=(Button)findViewById(R.id.updatesoption);
        Intent intent = getIntent();
        getquestionname = intent.getStringExtra("que");
        questioname=(EditText)findViewById(R.id.questnname);
        questioname.setText(getquestionname);
        optionnewvalue=(EditText)findViewById(R.id.newvalue);
        spinlist=(Spinner)findViewById(R.id.optionsslist);
        database = FirebaseDatabase.getInstance();
        fDatabaseRoot = database.getReference("Survey");
        Retrieve_Park_Question_Options();
        updateit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(optionnewvalue.getText().toString().trim())) {
                    Toast toast = Toast.makeText(getApplicationContext(),  "Please Enter New Value!", Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    pgsBar.setVisibility(View.GONE);
                    return;
                }



                String options=spinlist.getSelectedItem().toString();


                int index = spinlist.getSelectedItemPosition();
                Toast toast = Toast.makeText(getApplicationContext(), "Your Select " + options, Toast.LENGTH_SHORT);
                toast.setMargin(50, 50);
                toast.show();
                System.out.println(questionlistoption);

                questionlistoption.set(index, optionnewvalue.getText().toString());
                if(questionlistoption.get(0).equals("Select Park Name")) {
                    questionlistoption.remove(0);
                }
                adapter.notifyDataSetChanged();
                //  System.out.println(questionlistoption);

                pgsBar.setVisibility(View.VISIBLE);
                final Query query = fDatabaseRoot.orderByChild("question").equalTo(getquestionname);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            String key = appleSnapshot.getKey();
                            //    update(key);
                            fDatabaseRoot.child(key).child("question").setValue(questioname.getText().toString());
                            fDatabaseRoot.child(key).child("options").setValue(questionlistoption);


                        }
                        Toast toast = Toast.makeText(getApplicationContext(), "Question Selected Option Update Successfully", Toast.LENGTH_SHORT);
                        toast.setMargin(50, 50);
                        toast.show();
                        pgsBar.setVisibility(View.GONE);
                        Intent intent =new Intent(UpdateOption_Survey.this,welcomepage.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Log.e(TAG, "onCancelled", databaseError.toException());
                        pgsBar.setVisibility(View.GONE);
                    }
                });
            }

        });

    }
    void Retrieve_Park_Question_Options()
    {

        Query query = fDatabaseRoot.orderByKey();
        questionlistoption.add(0,"Select Park Name");
        query = fDatabaseRoot.orderByChild("question").equalTo(getquestionname);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot surveySnapshot : dataSnapshot.getChildren()) {

                    kk=surveySnapshot.getValue(Survey.class);
                    Fquestionlistoption=kk.getOptions();



                    for(int i=0;i<Fquestionlistoption.size();i++)
                    {
                        System.out.println(Fquestionlistoption.get(i));
                        questionlistoption.add(Fquestionlistoption.get(i));
                    }
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAG, "onCancelled", databaseError.toException());

            }
        });





        adapter = new ArrayAdapter<String>(this,R.layout.color_spinner_layout,questionlistoption);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinlist.setAdapter(adapter);
        pgsBar.setVisibility(View.GONE);

        spinlist.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                if(parent.getItemAtPosition(pos).equals("Select Question Option"))
                {

                }
                else
                {
                    String name=parent.getItemAtPosition(pos).toString();
                    Toast toast = Toast.makeText(getApplicationContext(), "Your Select "+name, Toast.LENGTH_SHORT);
                    toast.setMargin(50, 50);
                    toast.show();






                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }
}