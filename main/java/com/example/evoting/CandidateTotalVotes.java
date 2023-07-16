package com.example.evoting;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CandidateTotalVotes extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<String> voteTotal;
    CandidateDataBaseHelper db;
    Button back;
    Button deleteAll;
    click click;

    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.candidate_total_votes);
        click = new click();
        back = findViewById(R.id.Back);
        deleteAll = findViewById(R.id.Delete_All);
        back.setOnClickListener(click);
        deleteAll.setOnClickListener(click);
        lv = findViewById(R.id.vote_count);
        db = new CandidateDataBaseHelper(this);
        voteTotal = (ArrayList<String>) db.getTotalVotes();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, voteTotal);
        lv.setAdapter(adapter);

    }
    private class click implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.Back){
                finish();
            }
            else if(view.getId() == R.id.Delete_All){
                db.deleteTable();
                finish();
            }
        }
    }
}
