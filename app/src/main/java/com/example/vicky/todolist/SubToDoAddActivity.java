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
    TextView contentsView, headerView, dateView;


    long todoId = -1;
    SubToDoAddActivity activity;
    DBHandler dbHandler;
    ItemTouchHelper touchHelper;
    ArrayList<ToDoItem> list;
    ArrayList<ToDo> todoList;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.todomodify);
        item_toolbar = findViewById(R.id.item_toolbar);
        contentsView = findViewById(R.id.ContentsView);
        headerView = findViewById(R.id.headerView);
        dateView = findViewById(R.id.dateView);
        setSupportActionBar(item_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_item, menu);
        return true;
    }

}
