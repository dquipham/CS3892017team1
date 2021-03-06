package com.example.jacksonhoang.citygoer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class factsActivity extends AppCompatActivity {

    ArrayList<String> myFacts = new ArrayList<String>();
    Map<String, Object> factsRead = new HashMap<String, Object>();
    ArrayList<String>quizMe= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);

        //initializing firebase database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Info").child("Facts");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                   myFacts.add((String)postSnapshot.getValue());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
       final TextView fact1 = (TextView) findViewById(R.id.facts_text);
        fact1.setText("");

         Button button = (Button) findViewById(R.id.refreshbutton);

         button.setOnClickListener(new View.OnClickListener() {

             public void onClick(View v) {
                 int size = myFacts.size()-1;
                 if(size==-1){
                 fact1.setText("Ooops. Out of facts! Check back later");
                 }
                 else {
                     int numb = (int) (Math.random() * size);
                     fact1.setText(myFacts.get(numb).toString());
                     quizMe.add(myFacts.get(numb));
                     myFacts.remove(myFacts.get(numb));
                 }
             }
         });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
