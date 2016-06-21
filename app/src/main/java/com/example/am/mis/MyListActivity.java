package com.example.am.mis;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class MyListActivity extends Activity {

    private ListView listView;
    private MyDatabaseHelper dbHelper;
    private long clickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        dbHelper = new MyDatabaseHelper(this,"information.db",null,1);
        listView = new ListView(this);
        refreshView();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - clickTime > 2000) {
                Toast.makeText(this,"再次点击退出", LENGTH_SHORT).show();
                clickTime = System.currentTimeMillis();
            } else {
                MyApplication.getInstance().exit();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_information :
                Intent intent = new Intent();
                intent.setClass(this,AddActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshView();
    }

    private void refreshView() {

        final Cursor cursor = dbHelper.getWritableDatabase().query("person",null,null,null,null,null,null);
        ListAdapter adapter = new SimpleCursorAdapter(
                this,R.layout.list_item_layout,cursor,new String[]{"name","num"},
                new int[]{R.id.list_item_name,R.id.list_item_num},0);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();

                //获取选中列表在数据库中的num
                Cursor cursor1 = (Cursor) parent.getItemAtPosition(position);
                String num = cursor1.getString(cursor.getColumnIndex("num"));

                intent.putExtra("num",num);
                intent.setClass(MyListActivity.this,UpdateActivity.class);
                startActivity(intent);

            }
        });
        setContentView(listView);
    }
}
