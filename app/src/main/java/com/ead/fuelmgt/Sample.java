package com.ead.fuelmgt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Sample extends AppCompatActivity {

    EditText etName, etPhone, etRowID;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);

    }

    public void btnSubmitData(View v){
        String name = etName.getEditableText().toString().trim();
        String password = etPhone.getEditableText().toString().trim();
        Log.i("VALUES",name+password);


            dbHelper = new DBHelper(this);
//            ContactDB db = new ContactDB(this);
//            db.open();  //open db
//            db.createEntry(name, phone);
//            db.close(); //close db
            long count = dbHelper.createEntry(name,password);
            if (count > 0){
                Toast.makeText(Sample.this, "Successfully saved", Toast.LENGTH_SHORT).show();
            }

//            etName.setText(null);
//            etPhone.setText(null);


    }

    public void btnShowData(View v){
        startActivity(new Intent(Sample.this, UserSelect.class));
    }


}