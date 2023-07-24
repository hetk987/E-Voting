package com.example.evoting;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class ForgotActivity extends AppCompatActivity {
    EditText usernameInput;
    Button forgot;
    UserDataBaseHelper userHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_activity);
        usernameInput = findViewById(R.id.username_Input);
        forgot = findViewById(R.id.forgot_Button);
        userHelper = new UserDataBaseHelper(this);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();

            }
        });

    }
    private void sendMessage() {
        String phone = userHelper.getPhone(usernameInput.getText().toString());
        String password = userHelper.getPassword(usernameInput.getText().toString());
        if(phone != null){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, "Password: "+ password, null, null);
            Toast.makeText(this, "Sent Password", Toast.LENGTH_SHORT).show();
        }
    }
}
