package com.example.timetableio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button cancelButton, loginButton;
    private EditText inputLoginEmail, inputLoginPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        inputLoginEmail = (EditText) findViewById(R.id.loginEmail);
        inputLoginPassword = (EditText) findViewById(R.id.loginPassword);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String email = inputLoginEmail.getText().toString();
                String password = inputLoginPassword.getText().toString();
                if(email.equalsIgnoreCase("admin")){
                    Intent intent = new Intent(getBaseContext(), AdminHomeActivity.class);
                    startActivity(intent);
                }
                else if(password.equalsIgnoreCase("lecturer")){
                    Intent intent = new Intent(getBaseContext(), LecturerHomeActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
