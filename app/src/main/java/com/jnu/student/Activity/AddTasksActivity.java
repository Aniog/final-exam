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
                if(checkedId == R.id.add_tasks_details_Button_Daily) {
                    task_Type = "daily";
                }
                if(checkedId == R.id.add_tasks_details_Button_Weekly) {
                    task_Type = "weekly";
                }
                if(checkedId == R.id.add_tasks_details_Button_Regular) {
                    task_Type = "regular";
                }
            }
        });


        // 在布局中找到ID为button_item_details_ok的Button控件
        Button buttonOk = findViewById(R.id.add_tasks_details_ok);
        // 为Button添加点击事件监听器
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建一个新的Intent对象
                Intent intent = new Intent();

                EditText editTasksTitle = findViewById(R.id.add_tasks_details_edit_Title);

                intent.putExtra("title", editTasksTitle.getText().toString());

                //得分
                EditText editScoreTitle = findViewById(R.id.add_tasks_details_edit_Score);
                intent.putExtra("score", editScoreTitle.getText().toString());

                //标签
                EditText editTagsTitle = findViewById(R.id.add_tasks_details_edit_Tags);
                intent.putExtra("tags", editTagsTitle.getText().toString());

                //任务类型
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

                //得分
                EditText editScoreTitle = findViewById(R.id.add_tasks_details_edit_Score);
                intent.putExtra("score", editScoreTitle.getText().toString());

                //标签
                EditText editTagsTitle = findViewById(R.id.add_tasks_details_edit_Tags);
                intent.putExtra("tags", editTagsTitle.getText().toString());

                intent.putExtra("task_type", task_Type);

                setResult(Activity.RESULT_OK, intent);
                AddTasksActivity.this.finish();
            }
        });
    }
}