package com.example.am.mis;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity{

    private static final String[] sexItem = {"未选择","男","女"};
    private EditText name;
    private EditText num;
    private EditText salary;
    private Spinner sex;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        name = (EditText) findViewById(R.id.add_name);
        num = (EditText) findViewById(R.id.add_num);
        salary = (EditText) findViewById(R.id.add_salary);
        submit = (Button) findViewById(R.id.add_submit);
        sex = (Spinner) findViewById(R.id.add_sex);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sexItem);
        sex.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitToDB();
            }
        });
    }

    private void submitToDB() {
        MyDatabaseHelper dbHelper;

        dbHelper = new MyDatabaseHelper(this,"information.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("insert into person(name,num,sex,salary)values(?,?,?,?)",
                new String[]{
                        this.name.getText().toString(),
                        this.num.getText().toString(),
                        ((TextView) this.sex.getSelectedView()).getText().toString(),
                        this.salary.getText().toString()
                });

        Toast.makeText(this,"添加数据成功",Toast.LENGTH_SHORT).show();

        this.name.setText("");
        this.num.setText("");
        this.salary.setText("");
    }
}

