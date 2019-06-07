package com.example.vicky.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.vicky.todolist.DTO.ToDo;
import com.example.vicky.todolist.DTO.ToDoItem;

import java.util.ArrayList;

import static com.example.vicky.todolist.Const.*;

public class DBHandler extends SQLiteOpenHelper {


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createToDoTable = "CREATE TABLE " + TABLE_TODO + " (" +
                COL_ID + " integer PRIMARY KEY AUTOINCREMENT," +
                COL_CREATED_AT + " datetime DEFAULT CURRENT_TIMESTAMP," +
                COL_TODO_IS_COMPLETED + " integer," +
                COL_NAME + " varchar," +
                COL_CONTENTS + " varchar," +
                COL_DATE + " varchar)";
        String createToDoItemTable =
                "CREATE TABLE " + TABLE_TODO_ITEM + " (" +
                        COL_ID + " integer PRIMARY KEY AUTOINCREMENT," +
                        COL_CREATED_AT + " datetime DEFAULT CURRENT_TIMESTAMP," +
                        COL_TODO_ID + " integer," +
                        COL_ITEM_NAME + " varchar," +
                        COL_IS_COMPLETED + " integer," +
                        COL_SUBCONTENTS + " varchar," +
                        COL_SUBDATE + " varchar)";

        db.execSQL(createToDoTable);
        db.execSQL(createToDoItemTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    boolean addToDo(ToDo todo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, todo.getName());
        long result = db.insert(TABLE_TODO, null, cv);
        return result != -1;
    }


    void updateToDo(ToDo todo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, todo.getName());
        db.update(TABLE_TODO, cv, COL_ID + "=?", new String[]{String.valueOf(todo.getId())});
    }

    void deleteToDo(Long todoId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_TODO_ITEM, COL_TODO_ID + "=?", new String[]{String.valueOf(todoId)});
        db.delete(TABLE_TODO, COL_ID + "=?", new String[]{String.valueOf(todoId)});
    }

    void updateToDoItemCompletedStatus(Long todoId, Boolean isCompleted) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor queryResult = db.rawQuery("SELECT * FROM " + TABLE_TODO_ITEM + " WHERE " + COL_TODO_ID + "=" + todoId, null);
        if (queryResult.moveToFirst()) {
            do {
                ToDoItem item = new ToDoItem();
                item.setId(queryResult.getLong(queryResult.getColumnIndex(COL_ID)));
                item.setToDoId(queryResult.getLong(queryResult.getColumnIndex(COL_TODO_ID)));
                item.setItemName(queryResult.getString(queryResult.getColumnIndex(COL_ITEM_NAME)));
                item.setCompleted(isCompleted);
                updateToDoItem(item);
            } while (queryResult.moveToNext());
        }
        queryResult.close();
    }

    public void updateToDoItem(ToDoItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ITEM_NAME, item.getItemName());
        cv.put(COL_TODO_ID, item.getToDoId());
        cv.put(COL_IS_COMPLETED, item.isCompleted());
        db.update(TABLE_TODO_ITEM, cv, COL_ID + "=?", new String[]{String.valueOf(item.getId())});
    }

    ArrayList<ToDo> getToDos() {
        ArrayList<ToDo> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor queryResult = db.rawQuery("SELECT * from " + TABLE_TODO, null);
        if (queryResult.moveToFirst()) {
            do {
                ToDo todo = new ToDo();
                todo.setId(queryResult.getLong(queryResult.getColumnIndex(COL_ID)));
                todo.setName(queryResult.getString(queryResult.getColumnIndex(COL_NAME)));
                result.add(todo);
            } while (queryResult.moveToNext());
        }
        queryResult.close();
        return result;
    }

    public boolean addToDoItem(ToDoItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ITEM_NAME, item.getItemName());
        cv.put(COL_TODO_ID, item.getToDoId());
        cv.put(COL_IS_COMPLETED, item.isCompleted());

        long result = db.insert(TABLE_TODO_ITEM, null, cv);
        return result != -1;
    }

    public void deleteToDoItem(long itemId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_TODO_ITEM, COL_ID + "=?", new String[]{String.valueOf(itemId)});

    }

    public ArrayList<ToDoItem> getToDoItems(long todoId) {
        ArrayList<ToDoItem> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor queryResult = db.rawQuery("SELECT * FROM " + TABLE_TODO_ITEM + " WHERE " + COL_TODO_ID + "=" + todoId, null);
        if (queryResult.moveToFirst()) {
            do {
                ToDoItem item = new ToDoItem();
                item.setId(queryResult.getLong(queryResult.getColumnIndex(COL_ID)));
                item.setToDoId(queryResult.getLong(queryResult.getColumnIndex(COL_TODO_ID)));
                item.setItemName(queryResult.getString(queryResult.getColumnIndex(COL_ITEM_NAME)));
                item.setSubContents(queryResult.getString(queryResult.getColumnIndex(COL_SUBCONTENTS)));
                item.setSubDate(queryResult.getString(queryResult.getColumnIndex(COL_SUBDATE)));
                item.setCompleted(queryResult.getInt(queryResult.getColumnIndex(COL_IS_COMPLETED)) == 1);
                result.add(item);
            } while (queryResult.moveToNext());
        }

        queryResult.close();
        return result;
    }

    ArrayList<ToDo> getToDoRead(long id) {
        ArrayList<ToDo> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor queryResult = db.rawQuery("SELECT * from " + TABLE_TODO + " WHERE " + COL_ID + "=" + id, null);
        if (queryResult.moveToFirst()) {
            do {
                ToDo todo = new ToDo();
                todo.setId(queryResult.getLong(queryResult.getColumnIndex(COL_ID)));
                todo.setName(queryResult.getString(queryResult.getColumnIndex(COL_NAME)));
                todo.setDate(queryResult.getString(queryResult.getColumnIndex(COL_DATE)));
                todo.setContents(queryResult.getString(queryResult.getColumnIndex(COL_CONTENTS)));
                result.add(todo);
            } while (queryResult.moveToNext());
        }
        queryResult.close();
        return result;
    }

    void updateToDoModify(long todoId, String name, String date, String content) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME,name);
        cv.put(COL_DATE,date);
        cv.put(COL_CONTENTS,content);
        db.update(TABLE_TODO, cv, COL_ID + "=?", new String[]{String.valueOf(todoId)});
    }
    void updateCalendar(long todoId, String date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_DATE,date);
        db.update(TABLE_TODO, cv, COL_ID + "=?", new String[]{String.valueOf(todoId)});
    }


    void insertSubToDoModify(long todoId, String name, String date, String content){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ITEM_NAME,name);
        cv.put(COL_SUBDATE,date);
        cv.put(COL_SUBCONTENTS,content);
        db.update(TABLE_TODO, cv, COL_TODO_ID + "=?", new String[]{String.valueOf(todoId)});

    }

}
