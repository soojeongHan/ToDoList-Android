package com.example.vicky.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;
import static com.example.vicky.todolist.Const.*;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView mCalendarView;
    long todoId;
    String date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        mCalendarView = findViewById(R.id.calendarView);
        todoId = getIntent().getLongExtra(INTENT_TODO_ID, -1);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                date = year + "/" + (month+1) + "/" + dayOfMonth;
                Log.i("date", date);
                Intent backIntent = new Intent();
                backIntent.putExtra("date", date);
                setResult(RESULT_OK, backIntent);
                finish();
            }
        });
    }
}
