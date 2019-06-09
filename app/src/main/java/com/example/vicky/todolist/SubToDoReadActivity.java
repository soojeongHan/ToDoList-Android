package com.example.vicky.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.*;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.vicky.todolist.DTO.ToDo;
import com.example.vicky.todolist.DTO.ToDoItem;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.vicky.todolist.Const.*;

public class SubToDoReadActivity extends AppCompatActivity {

    Toolbar item_toolbar;
    TextView contentsView, headerView, dateView;

    public static SubToDoReadActivity finish_SubToDoReadActivity;

    long todoId = -1;
    SubToDoReadActivity activity;
    DBHandler dbHandler;
    ArrayList<ToDoItem> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subtodoread);
        item_toolbar = findViewById(R.id.item_toolbar);

        contentsView = findViewById(R.id.subContentsView);
        headerView = findViewById(R.id.subHeaderView);
        dateView = findViewById(R.id.subDateView);
        setSupportActionBar(item_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");
        finish_SubToDoReadActivity = SubToDoReadActivity.this;
        todoId = getIntent().getLongExtra(COL_TODO_ID, -1);
        Log.i("todoId", String.valueOf(todoId));
        activity = this;
        dbHandler = new DBHandler(activity);

        list = dbHandler.getToDoItems(todoId);

        if(list.get(0).getItemName()==null) {
            headerView.setText("");
        } else {
            headerView.setText(list.get(0).getItemName());
        }
        if(list.get(0).getSubContents()==null) {
            contentsView.setText("");
        } else {
            contentsView.setText(list.get(0).getSubContents());
        }
        if(list.get(0).getSubDate()==null) {
            dateView.setText("");
        } else {
            dateView.setText(list.get(0).getSubDate());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todoread_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent todogo = new Intent(SubToDoReadActivity.this, ToDoReadActivity.class);
                todogo.putExtra(INTENT_TODO_ID, todoId);
                startActivity(todogo);
                finish();
                return true;
            case R.id.menu_edit:
                Intent editIntent = new Intent(SubToDoReadActivity.this, SubToDoModifyActivity.class);
                editIntent.putExtra(INTENT_TODO_ID, todoId);
                startActivity(editIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        refreshList();
        super.onResume();
    }

    void refreshList() {
        list = dbHandler.getToDoItems(todoId);
    }

}
