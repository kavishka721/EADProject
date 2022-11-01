package com.ead.fuelmgt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button signupBtn, loginBtn;
    EditText username_edt, password_edt;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupBtn = (Button) findViewById(R.id.lsignup_btn);
        loginBtn = (Button) findViewById(R.id.login_btn);
        username_edt = (EditText) findViewById(R.id.lusername_edt);
        password_edt = (EditText) findViewById(R.id.lpassword_edt);

        dbHelper = new DBHelper(this);
        dbHelper.openDatabase();

        //login btn onclick method ( check username, password when login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_edt.getEditableText().toString().trim();
                String password = password_edt.getEditableText().toString().trim();

                if(dbHelper.checkUsernamePassword(username, password)) {
                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), User_Mainpage.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Login credentials are invalid", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //navigate to signup page
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}