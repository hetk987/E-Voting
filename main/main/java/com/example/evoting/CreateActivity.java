package com.example.evoting;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Random;

public class CreateActivity extends AppCompatActivity {
    static final int verify_phone = 1;
    TextView text;
    Button createBtn;

    Button backBtn;
    EditText username;
    EditText password;
    EditText confirmPassword;
    EditText phone;
    boolean result;
    int secretCode = generateRandomNumber();
    CreateListener listen;
    UserDataBaseHelper userHelper;


    boolean admin = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);
        Intent intent = this.getIntent();
        String type = intent.getStringExtra("type");
        text = findViewById(R.id.account_txt);
        username = findViewById(R.id.username_Input);
        password = findViewById(R.id.password_Input);
        confirmPassword = findViewById(R.id.confirmPass_Input);
        phone = findViewById(R.id.phone_Input);
        createBtn = findViewById(R.id.Create_button);
        backBtn = findViewById(R.id.back_btn);
        listen = new CreateListener();
        createBtn.setOnClickListener(listen);
        backBtn.setOnClickListener(listen);
        userHelper= new UserDataBaseHelper(this);
    }

    public class CreateListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.back_btn){
                finish();
            }
            if(username.getText().toString().equals("") || password.getText().toString().equals("") || confirmPassword.getText().toString().equals("") || phone.getText().toString().equals("")){
                Toast.makeText(CreateActivity.this, "Missing Field", Toast.LENGTH_SHORT).show();
            }
            else if(!(password.getText().toString().equals(confirmPassword.getText().toString()))){
                Toast.makeText(CreateActivity.this, "Passwords Don't match", Toast.LENGTH_SHORT).show();
            }
            else if(username.getText().toString().length()< 5 || password.getText().toString().length() < 5){
                Toast.makeText(CreateActivity.this, "Username or Password too SHORT", Toast.LENGTH_SHORT).show();
            }
            else if (phone.getText().toString().length() != 10) {
                Toast.makeText(CreateActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            }
            else if (checkIfUsernameExists(username.getText().toString())){
                Toast.makeText(CreateActivity.this, "Username Taken", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(CreateActivity.this, VerifyPhone.class);
                intent.putExtra("code",secretCode);
                if(ContextCompat.checkSelfPermission(CreateActivity.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    sendSms(phone.getText().toString());
                }
                else {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                    sendSms(phone.getText().toString());
                }
                userHelper.addOneUser(new UserModel(username.getText().toString(), password.getText().toString(), phone.getText().toString() , false));
                startActivity(intent);

            }
        }
    }


    private boolean checkIfUsernameExists(String username) {
        ArrayList<String> list = (ArrayList<String>) userHelper.getUsernames();
        return list.contains(username);
    }
    private void sendSms(String phone) {
        if(phone != null){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, Integer.toString(secretCode), null, null);
            Toast.makeText(this, "Code Sent", Toast.LENGTH_SHORT).show();
        }
    }
    public static int generateRandomNumber() {
        Random random = new Random();
        int min = 10000; // Minimum 5-digit number
        int max = 99999; // Maximum 5-digit number
        return random.nextInt(max - min + 1) + min;
    }
}


