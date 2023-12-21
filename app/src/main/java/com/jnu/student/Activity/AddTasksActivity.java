package com.jnu.student.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jnu.student.R;

public class AddTasksActivity extends AppCompatActivity {
    private BottomNavigationView btmNavView;
    private String task_Type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tasks);

        RadioGroup radioGroupFrequency = findViewById(R.id.add_tasks_details_Group_Frequency);

        radioGroupFrequency.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.add_tasks_details_Button_meiri) {
                    task_Type = "daily";
                }
                if(checkedId == R.id.add_tasks_details_Button_meizhou) {
                    task_Type = "weekly";
                }
                if(checkedId == R.id.add_tasks_details_Button_putong) {
                    task_Type = "regular";
                }
                if(checkedId == R.id.add_tasks_details_Button_fubeng) {
                    task_Type = "fubeng";
                }
            }
        });


        Button buttonOk = findViewById(R.id.add_tasks_details_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                EditText editTasksTitle = findViewById(R.id.add_tasks_details_edit_Title);

                intent.putExtra("title", editTasksTitle.getText().toString());

                EditText editScoreTitle = findViewById(R.id.add_tasks_details_edit_Score);
                intent.putExtra("score", editScoreTitle.getText().toString());


                intent.putExtra("task_type", task_Type);

                setResult(Activity.RESULT_OK, intent);
                AddTasksActivity.this.finish();
            }
        });

        Button buttonCancel = findViewById(R.id.add_tasks_details_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                EditText editTasksTitle = findViewById(R.id.add_tasks_details_edit_Title);

                intent.putExtra("title", editTasksTitle.getText().toString());

                EditText editScoreTitle = findViewById(R.id.add_tasks_details_edit_Score);
                intent.putExtra("score", editScoreTitle.getText().toString());



                intent.putExtra("task_type", task_Type);

                setResult(Activity.RESULT_OK, intent);
                AddTasksActivity.this.finish();
            }
        });
    }
}