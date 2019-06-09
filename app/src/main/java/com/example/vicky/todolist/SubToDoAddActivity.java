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
import android.widget.ImageView;
import com.example.vicky.todolist.DTO.ToDo;
import com.example.vicky.todolist.DTO.ToDoItem;

import java.util.ArrayList;

import static com.example.vicky.todolist.Const.*;



public class SubToDoAddActivity extends AppCompatActivity {


    Toolbar item_toolbar;
    static TextView headerView_edit,dateView_edit,contentView_edit;

    long todoId = -1;
    SubToDoModifyActivity activity;
    DBHandler dbHandler;
    ItemTouchHelper touchHelper;
    ArrayList<ToDoItem> todoList;
    private int REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todomodify);
        item_toolbar = findViewById(R.id.item_toolbar);
        headerView_edit = findViewById(R.id.headerView_edit);
        dateView_edit = findViewById(R.id.dateView_edit);
        contentView_edit = findViewById(R.id.contentView_edit);
        setSupportActionBar(item_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");
        todoId = getIntent().getLongExtra(INTENT_TODO_ID ,-1);
        Log.i("TODOMODIFY_ID", String.valueOf(todoId));

        dbHandler = new DBHandler(activity);
        todoList = dbHandler.getToDoItems(todoId);
        Log.i("todoList", String.valueOf(todoList));
        Log.i("todoList.SIZE", String.valueOf(todoList.size()));
        if(todoList.get(0).getItemName()==null) {
            headerView_edit.setText("");
        } else {
            headerView_edit.setText(todoList.get(0).getItemName());
        }
        if(todoList.get(0).getSubContents()==null) {
            contentView_edit.setText("");
        } else {
            contentView_edit.setText(todoList.get(0).getSubContents());
        }
        if(todoList.get(0).getSubDate()==null) {
            dateView_edit.setText("");
        } else {
            dateView_edit.setText(todoList.get(0).getSubDate());
        }

        dateView_edit.setOnClickListener(new View.OnClickListener() {
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
            dateView_edit.setText(data.getStringExtra("date"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SubToDoReadActivity ta = SubToDoReadActivity.finish_SubToDoReadActivity;
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_check:
                dbHandler.updateSubToDoModify(todoId, headerView_edit.getText().toString(), dateView_edit.getText().toString(), contentView_edit.getText().toString());
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
