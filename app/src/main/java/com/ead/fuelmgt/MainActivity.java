package com.ead.fuelmgt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button loginBtn;
    EditText username_edt, password_edt, confirmPassword_etd;
    TextView nameTxt;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username_edt = (EditText)findViewById(R.id.username_edt);
        password_edt = (EditText)findViewById(R.id.password_edt);
        confirmPassword_etd = (EditText) findViewById(R.id.confirm_password_edt);
        loginBtn = ( Button) findViewById(R.id.login_btn);
        nameTxt = (TextView) findViewById(R.id.show_name);



        dbHelper = new DBHelper(this);
        dbHelper.openDatabase();

//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    public void btnSignUp(View view) {
        String username = username_edt.getEditableText().toString();
        String password =  password_edt.getEditableText().toString();
        String cPassword = confirmPassword_etd.getEditableText().toString();
        Log.i("VALUES", username+ password+ cPassword);
        nameTxt.setText(username);
        //
        if (username.isEmpty() || password.isEmpty() || cPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill the all fields", Toast.LENGTH_SHORT).show();
        } else {
            if(dbHelper.checkUsername(username)) {
                Toast.makeText(getApplicationContext(), "Username already exist", Toast.LENGTH_SHORT).show();
            } else {
                if(!password.equals(cPassword)) {
                    Toast.makeText(getApplicationContext(), "Password and confirm password does not match", Toast.LENGTH_SHORT).show();
                } else {
                    long count = dbHelper.createEntry(username,password);
                    if(count > 0) {
                        Toast.makeText(getApplicationContext(), "Signup successfully!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Signup failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

}