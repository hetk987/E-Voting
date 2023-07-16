package com.example.evoting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class AddCandidate extends AppCompatActivity {
    EditText candidate_input;
    Button submit_btn;
    Button total;
    CandidateDataBaseHelper db = new CandidateDataBaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_candidate);

        candidate_input = findViewById(R.id.candidate_name_input);
        submit_btn = findViewById(R.id.submit);
        total = findViewById(R.id.voteTotal);
        total.setOnClickListener(new ViewTotalClick());
        submit_btn.setOnClickListener(new SubmitClick());

    }

    public class SubmitClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(db.addCandidate(candidate_input.getText().toString())){
                Toast.makeText(AddCandidate.this, "Candidate Added", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    public class ViewTotalClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AddCandidate.this, CandidateTotalVotes.class);
            startActivity(intent);

        }
    }
}
