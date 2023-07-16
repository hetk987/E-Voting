package com.example.evoting;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView createAccount;
    TextView forgotPassword;
    TextView adminAccount;
    EditText username;
    EditText password;
    Button loginBtn;
    UserDataBaseHelper userDB = new UserDataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainClick mc = new MainClick(this);

        createAccount = findViewById(R.id.Create_Text);
        forgotPassword = findViewById(R.id.Forgot_txt);
        adminAccount = findViewById(R.id.Admin_txt);
        username = findViewById(R.id.Name_Input);
        password = findViewById(R.id.Password_Input);
        loginBtn = findViewById(R.id.LoginBtn);

        createAccount.setOnClickListener(mc);
        forgotPassword.setOnClickListener(mc);
        loginBtn.setOnClickListener(mc);
        adminAccount.setOnClickListener(mc);


    }

    public class MainClick implements View.OnClickListener{
        private final MainActivity mc;
        private boolean changeActivity = false;

        public MainClick(MainActivity mc) {
            this.mc = mc;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            if(view.getId() == R.id.Create_Text){
                intent = new Intent(mc, CreateActivity.class);
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                changeActivity = true;
            }
            else if(view.getId() == R.id.Forgot_txt){
                intent = new Intent(mc, ForgotActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                changeActivity = true;
            }
            else if(view.getId() == R.id.Admin_txt){
                intent = new Intent(mc, AddCandidate.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                changeActivity = true;
            }
            else if(view.getId() == R.id.LoginBtn){
                System.out.println("LOGIN");
                if(username.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(mc, "Missing Field", Toast.LENGTH_SHORT).show();
                }
                else if(checkIfUsernameExists(username.getText().toString())){
                    String dataPassword = userDB.getPassword(username.getText().toString());
                    if(userDB.hasVoted(username.getText().toString())){
                        Toast.makeText(mc, "You have already Voted", Toast.LENGTH_SHORT).show();
                    }
                    else if(dataPassword.equals(password.getText().toString())){
                        Toast.makeText(mc, "LOGIN", Toast.LENGTH_SHORT).show();
                        intent = new Intent(mc, VoteActivity.class);
                        intent.putExtra("username", username.getText().toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        changeActivity = true;
                    }
                    else {
                        Toast.makeText(mc, "NOOOOOOOO", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(mc, "Username doesn't exist", Toast.LENGTH_SHORT).show();

                }
            }
            if(changeActivity){
                startActivity(intent);
            }

        }
    }
    private boolean checkIfUsernameExists(String username) {
        ArrayList<String> list = (ArrayList<String>) userDB.getUsernames();
        return list.contains(username);
    }
}

