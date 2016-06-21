package com.example.am.mis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private MyDatabaseHelper dbHelper;
    EditText accountEditText;
    EditText passwordEditText;
    Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        accountEditText = (EditText) findViewById(R.id.login_account);
        passwordEditText = (EditText) findViewById(R.id.login_password);
        goButton = (Button) findViewById(R.id.login_go);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("admin".equals(accountEditText.getText().toString()) ||
                    "123456".equals(passwordEditText.getText().toString())) {
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,MyListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this,"你不要耍我好咩~~",Toast.LENGTH_SHORT).show();
                }
            }
        });

        dbHelper = new MyDatabaseHelper(this,"information.db",null,1);
        dbHelper.getWritableDatabase();
    }
}
