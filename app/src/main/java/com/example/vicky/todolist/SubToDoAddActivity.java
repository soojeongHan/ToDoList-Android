package com.example.vicky.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import com.example.vicky.todolist.DTO.ToDo;

import java.util.ArrayList;

import static com.example.vicky.todolist.Const.*;

public class SubToDoAddActivity extends AppCompatActivity {

    Toolbar item_toolbar;
    static TextView dateView_edit_add;
    static EditText headerView_edit_add,contentView_edit_add;
    long todoId = -1;
    SubToDoAddActivity activity;
    DBHandler dbHandler;
    private int REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todomodify);
        item_toolbar = findViewById(R.id.item_toolbar);
        headerView_edit_add = findViewById(R.id.headerView_edit);
        dateView_edit_add = findViewById(R.id.dateView_edit);
        contentView_edit_add = findViewById(R.id.contentView_edit);
        setSupportActionBar(item_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        todoId = getIntent().getLongExtra(INTENT_TODO_ID, -1);
        Log.i("TODOMODIFY_ID", String.valueOf(todoId));
        activity = this;
        dbHandler = new DBHandler(activity);

        headerView_edit_add.setText("");

        contentView_edit_add.setText("");
        dateView_edit_add.setText("");

        dateView_edit_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubToDoAddActivity.this, CalendarActivity.class);
                intent.putExtra(INTENT_TODO_ID, todoId);
                startActivityForResult(intent, REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            dateView_edit_add.setText(data.getStringExtra("date"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ToDoReadActivity ta = ToDoReadActivity.finish_toDoReadActivity;
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_check:
                Log.i("subId", String.valueOf(todoId));
                Log.i("subHeader", String.valueOf(headerView_edit_add.getText()));
                Log.i("subDate", String.valueOf(dateView_edit_add.getText()));
                Log.i("subContent", String.valueOf(contentView_edit_add.getText()));
                dbHandler.SubToDoAdd(todoId, headerView_edit_add.getText().toString(), dateView_edit_add.getText().toString(), contentView_edit_add.getText().toString());
                finish();
                ta.finish();
                Intent intent = new Intent(SubToDoAddActivity.this, ToDoReadActivity.class);
                intent.putExtra(INTENT_TODO_ID, todoId);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

