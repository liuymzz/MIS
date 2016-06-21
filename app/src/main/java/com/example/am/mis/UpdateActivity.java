package com.example.am.mis;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class UpdateActivity extends Activity {
    private TextView text;
    private String mark;
    private Intent intent;
    private EditText name;
    private EditText salary;
    private Button submit;
    private Spinner sex;
    private static final String[] sexItem = {"未选择","男","女"};
    MyDatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new MyDatabaseHelper(this,"information.db",null,1);
        db = dbHelper.getWritableDatabase();
        MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        intent = getIntent();
        mark = intent.getStringExtra("num");
        text = (TextView) findViewById(R.id.update_text);
        name = (EditText) findViewById(R.id.edit_name);
        salary = (EditText) findViewById(R.id.edit_salary);
        submit = (Button) findViewById(R.id.edit_submit);
        sex = (Spinner) findViewById(R.id.edit_sex);

        initial();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitToDB();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_activity_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_del : db.execSQL("delete from person where num=?",new String[]{mark});
                Toast.makeText(UpdateActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                finish(); break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void initial() {
        Cursor cursor = db.rawQuery("select * from person where num=?",new String[]{mark});
        cursor.moveToFirst();
        text.setText("在这里可以修改工号为" + mark + "员工的信息");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sexItem);
        sex.setAdapter(adapter);
        name.setText(cursor.getString(cursor.getColumnIndex("name")));
        salary.setText(cursor.getString(cursor.getColumnIndex("salary")));
        if("男".equals(cursor.getString(cursor.getColumnIndex("sex")))){
            sex.setSelection(1,true);
        }else if("女".equals(cursor.getString(cursor.getColumnIndex("sex")))){
            sex.setSelection(2,true);
        }else{
            sex.setSelection(0,true);
        }
    }

    private void submitToDB() {

        ContentValues values = new ContentValues();
        if(!"".equals(name.getText().toString())) {
            values.put("name",name.getText().toString());
        }
        if(!"".equals(salary.getText().toString())){
            values.put("salary",salary.getText().toString());
        }
        TextView sexPro = (TextView) this.sex.getSelectedView();
        values.put("sex",sexPro.getText().toString());
        db.update("person",values,"num = ?",new String[] {mark});
        Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();

    }
}
