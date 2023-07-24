package com.example.evoting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VoteActivity extends AppCompatActivity {
    ListView rv;
    int position;
    String username;
    CandidateDataBaseHelper canHelper;
    ArrayList<CandidateModel> canList;
    ArrayList<String> canListString;
    Button submitVote;
    ArrayAdapter customArrayAdapter;
    CandidateDataBaseHelper db;
    UserDataBaseHelper userDB;


    @Override
    protected void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);

        setContentView(R.layout.vote_activity);
        rv = findViewById(R.id.candidateList);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        submitVote = findViewById(R.id.submitVote);
        submitVote.setOnClickListener(new VoteSubmit());
        canHelper = new CandidateDataBaseHelper(this);
        db = new CandidateDataBaseHelper(this);
        userDB = new UserDataBaseHelper(this);
        canList = (ArrayList<CandidateModel>) canHelper.getCandidates();
        canListString = new ArrayList<String>();
        canListString = ListToString();
        customArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, canListString);
        rv.setAdapter(customArrayAdapter);
        Toast.makeText(this, canListString.toString(), Toast.LENGTH_SHORT).show();
        rv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                int length = adapterView.getCount();
                for(int j = 0; j<length;j++){
                    canList.get(j).setSelected(false);
                }
                canList.get(i).setSelected(true);
                canListString = ListToString();
                customArrayAdapter = new ArrayAdapter(VoteActivity.this, android.R.layout.simple_list_item_1, canListString);
                rv.setAdapter(customArrayAdapter);
            }
        });
    }

    private ArrayList<String> ListToString() {
        ArrayList<String> returnList = new ArrayList<String>();
        for (int i = 0; i<canList.size();i++) {
            returnList.add(canList.get(i).toString());
        }
        return returnList;
    }

    private class VoteSubmit implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            db.incrementVote(canList.get(position).getName());
            userDB.setVotedTrue(username);
            finish();
        }
    }
}
