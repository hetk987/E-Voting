package com.example.evoting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VerifyPhone extends AppCompatActivity {
    Button submit;
    EditText code;
    submitClick sb;
    int secretCode;

    public void onCreate(Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        setContentView(R.layout.verify_phone_activity);
        Intent receive = this.getIntent();
        if(receive!=null){
            secretCode = receive.getIntExtra("code", 0);
        }
        sb = new submitClick();
        submit = findViewById(R.id.Submit_button);
        code = findViewById(R.id.code);
        submit.setOnClickListener(sb);
    }

    public class submitClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(Integer.parseInt(code.getText().toString()) == secretCode){
                Toast.makeText(VerifyPhone.this, "Correct Code", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                Toast.makeText(VerifyPhone.this, "Wrong Code", Toast.LENGTH_SHORT).show();
            }

        }
    }
}


