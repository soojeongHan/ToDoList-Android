package com.example.vicky.todolist;


import android.content.DialogInterface;
import android.content.Intent;
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

    public static SubToDoReadActivity finish_SubtoDoReadActivity;
    Toolbar item_toolbar;
    RecyclerView rv_item;
    FloatingActionButton fab_item;
    TextView contentsView, headerView, dateView;

    public static ToDoReadActivity finish_toDoReadActivity;

}
