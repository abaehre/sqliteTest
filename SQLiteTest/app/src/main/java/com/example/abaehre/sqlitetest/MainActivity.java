package com.example.abaehre.sqlitetest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private Button add;
    private Button delete;
    private TextView showTable;
    private int count;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        count = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (Button)findViewById(R.id.add);
        delete = (Button)findViewById(R.id.delete);
        showTable = (TextView)findViewById(R.id.showTable);

        db = new DBHelper(this);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyTask task = new MyTask();
                task.execute(new String[]{"add", Integer.toString(count), "Andrew", "password"});
                count++;
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyTask task = new MyTask();
                task.execute(new String[]{"delete", "", "", ""});

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            if(params[0].equals("add")){
                db.add(params[1],params[2],params[3]);
                return "";
            }
            else if(params[0].equals("delete")){
                String temp = db.deleteFirst();
                if(!temp.equals("")){
                    return temp;
                }
                return "";
            }
            else{
                return "";
            }
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            if(!str.equals("")){
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
            showTable.setText(db.update());
        }
    }
}
