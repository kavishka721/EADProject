package com.ead.fuelmgt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserReg extends AppCompatActivity {

    Button signupBtn;
    EditText username_edt, password_edt, confirmPassword_etd;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);

        username_edt = (EditText)findViewById(R.id.username_edt);
        password_edt = (EditText)findViewById(R.id.password_edt);
        confirmPassword_etd = (EditText) findViewById(R.id.confirm_password_edt);
        signupBtn = ( Button) findViewById(R.id.login_btn);

        String username = username_edt.getText().toString();
        String password =  password_edt.getText().toString();
        String cPassword = confirmPassword_etd.getText().toString();

        dbHelper = new DBHelper(this);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}