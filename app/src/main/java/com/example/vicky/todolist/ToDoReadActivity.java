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

public class ToDoReadActivity extends AppCompatActivity {

    Toolbar item_toolbar;
    RecyclerView rv_item;
    FloatingActionButton fab_item;
    TextView contentsView, headerView, dateView;

    public static ToDoReadActivity finish_toDoReadActivity;

    long todoId = -1;
    ToDoReadActivity activity;
    DBHandler dbHandler;
    ItemTouchHelper touchHelper;
    ItemAdapter adapter;
    ArrayList<ToDoItem> list;
    ArrayList<ToDo> todoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todoread);
        item_toolbar = findViewById(R.id.item_toolbar);
        rv_item = findViewById(R.id.rv_item);
        fab_item = findViewById(R.id.fab_item);
        contentsView = findViewById(R.id.ContentsView);
        headerView = findViewById(R.id.headerView);
        dateView = findViewById(R.id.dateView);
        setSupportActionBar(item_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");
        finish_toDoReadActivity = ToDoReadActivity.this;
        todoId = getIntent().getLongExtra(INTENT_TODO_ID, -1);
        Log.i("todoId", String.valueOf(todoId));
        activity = this;
        dbHandler = new DBHandler(activity);

        todoList = dbHandler.getToDoRead(todoId);
        Log.i("todoList", String.valueOf(todoList));
        Log.i("todoList.SIZE", String.valueOf(todoList.size()));

        if(todoList.get(0).getName()==null) {
            headerView.setText("");
        } else {
            headerView.setText(todoList.get(0).getName());
        }
        if(todoList.get(0).getContents()==null) {
            contentsView.setText("");
        } else {
            contentsView.setText(todoList.get(0).getContents());
        }
        if(todoList.get(0).getDate()==null) {
            dateView.setText("");
        } else {
            dateView.setText(todoList.get(0).getDate());
        }

        rv_item.setLayoutManager(new LinearLayoutManager(activity));
        fab_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(ToDoReadActivity.this, SubToDoAddActivity.class);
                editIntent.putExtra(INTENT_TODO_ID, todoId);
                startActivity(editIntent);
                finish();
                }
        });
        touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder p1, @NonNull RecyclerView.ViewHolder p2) {
                int sourcePosition = p1.getAdapterPosition();
                int targetPosition = p2.getAdapterPosition();
                Collections.swap(list, sourcePosition, targetPosition);
                adapter.notifyItemMoved(sourcePosition, targetPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            }
        });

        touchHelper.attachToRecyclerView(rv_item);
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
                onBackPressed();
                return true;
            case R.id.menu_edit:
                Intent editIntent = new Intent(ToDoReadActivity.this, ToDoModifyActivity.class);
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
        adapter = new ItemAdapter(activity);
        rv_item.setAdapter(adapter);
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
        ToDoReadActivity activity;

        ItemAdapter(ToDoReadActivity activity) {
            this.activity = activity;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.todoread_sublist, viewGroup, false));
        }



        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
            Log.i("subToDoId : ", String.valueOf(list.get(i).getToDoId()));

            todoId = list.get(i).getToDoId();
            holder.itemName.setText(list.get(i).getItemName());

            // Test DATE 추후 연동 시 사용 예정
            if(todoList.get(0).getDate()==null) {
                holder.dateTextView.setText("DATEVALUE : null");
            } else {
                holder.dateTextView.setText("~"+todoList.get(0).getDate());
            }
            if(list.get(0).getItemName()==null) {
                holder.headerTextView.setText(" ");
            } else {
                holder.headerTextView.setText(list.get(0).getItemName());
            }

            holder.itemName.setChecked(list.get(i).isCompleted());
            if(list.get(i).isCompleted()==false) {
                holder.itemName.setPaintFlags(0);
            } else if(list.get(i).isCompleted()==true) {
                holder.itemName.setPaintFlags(holder.headerTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }

            // check = false, uncheck = true
            holder.itemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.get(i).setCompleted(!list.get(i).isCompleted());
                    Log.d("listGetIsCompleted ", String.valueOf(!list.get(i).isCompleted()));
                    activity.dbHandler.updateToDoItem(list.get(i));
                    if(list.get(i).isCompleted()==false) {
                        holder.itemName.setPaintFlags(0);
                    } else if(list.get(i).isCompleted()==true) {
                        holder.itemName.setPaintFlags(holder.headerTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
            });

            holder.itemName.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        activity.touchHelper.startDrag(holder);
                    }
                    return false;
                }
            });

            holder.headerTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ToDoReadActivity.this, SubToDoReadActivity.class);
                    intent.putExtra(COL_TODO_ID, todoId);
                    startActivity(intent);
                    finish();
                }
            });

            holder.dateTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ToDoReadActivity.this, SubToDoReadActivity.class);
                    intent.putExtra(COL_TODO_ID, todoId);
                    startActivity(intent);
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                    dialog.setTitle("Are you sure");
                    dialog.setMessage("Do you want to delete this item ?");
                    dialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int pos) {
                            activity.dbHandler.deleteToDoItem(list.get(i).getId());
                            activity.refreshList();
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog.show();
                }
            });

            holder.move.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        activity.touchHelper.startDrag(holder);
                    }
                    return false;
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CheckBox itemName;
            TextView headerTextView;
            TextView dateTextView;
            ImageView delete;
            ImageView move;


            ViewHolder(View v) {
                super(v);
                itemName = v.findViewById(R.id.cb_item);
                headerTextView = v.findViewById(R.id.tv_header);
                dateTextView = v.findViewById(R.id.tv_date);
                delete = v.findViewById(R.id.iv_delete);
                move = v.findViewById(R.id.iv_move);
            }
        }
    }
}
