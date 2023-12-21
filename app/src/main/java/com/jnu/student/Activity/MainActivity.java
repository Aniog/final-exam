package com.jnu.student.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jnu.student.Fragment.FirstTasksFragment;
import com.jnu.student.Fragment.meiriTasksFragment;
import com.jnu.student.R;
import com.jnu.student.data.DataDailyTasks;
import com.jnu.student.data.DataGeneralTasks;
import com.jnu.student.data.DataWeeklyTasks;
import com.jnu.student.data.Tasks;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Fragment tasksFragment = new FirstTasksFragment();
    private Fragment statisticsFragment = new meiriTasksFragment();
    private BottomNavigationView btmNavView;
    ActivityResultLauncher<Intent> addTasksLauncher;
    ActivityResultLauncher<Intent> addDungeonLauncher;
    ActivityResultLauncher<Intent> SortLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_tasks);

        if (savedInstanceState == null) {
            Fragment tasksFragment = new FirstTasksFragment();
            loadTasksFragment(tasksFragment);
        }
        addTasksLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        String tasks_type = data.getStringExtra("task_type");
                        if("daily".equals(tasks_type)) //每日任务
                        {
                            ArrayList<Tasks> daily_tasks = new DataDailyTasks().LoadTasks(this);
                            int score = Integer.parseInt(data.getStringExtra("score"));
                            String title = data.getStringExtra("title");
                            String tags = data.getStringExtra("tags");
                            daily_tasks.add(new Tasks(title,score));
                            new DataDailyTasks().SaveTasks(this,daily_tasks);
                        } else if ("weekly".equals(tasks_type)) { //每周任务
                            ArrayList<Tasks> weekly_tasks = new DataWeeklyTasks().LoadTasks(this);
                            int score = Integer.parseInt(data.getStringExtra("score"));
                            String title = data.getStringExtra("title");
                            String tags = data.getStringExtra("tags");
                            weekly_tasks.add(new Tasks(title,score));
                            new DataWeeklyTasks().SaveTasks(this,weekly_tasks);
                        } else if ("regular".equals(tasks_type)) { //普通任务
                            ArrayList<Tasks> general_tasks = new DataGeneralTasks().LoadTasks(this);
                            int score = Integer.parseInt(data.getStringExtra("score"));
                            String title = data.getStringExtra("title");
                            String tags = data.getStringExtra("tags");
                            general_tasks.add(new Tasks(title,score));
                            new DataGeneralTasks().SaveTasks(this,general_tasks);
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );

        addDungeonLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        SortLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        // 获取底部导航栏
        btmNavView = findViewById(R.id.bottom_menu);
        btmNavView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_rengwu) {

                Fragment tasksFragment = new FirstTasksFragment();
                loadTasksFragment(tasksFragment);
                return true;
            }
            if (item.getItemId() == R.id.navigation_jiangli) {

                return true;
            }
            if (item.getItemId() == R.id.navigation_tongji) {


                return true;
            }
            if (item.getItemId() == R.id.navigation_wo) {

                return true;
            }
            return false;
        });
    }


    private void loadTasksFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    //点击添加按钮
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.btn_msg) {
            PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.btn_msg));
            popupMenu.getMenu().add("新建任务");
            popupMenu.getMenu().add("加入副本");
            popupMenu.getMenu().add("排序");
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getTitle().toString()) {
                    case "新建任务":
                        Intent intent1 = new Intent(this, AddTasksActivity.class);
                        addTasksLauncher.launch(intent1);
                        return true;
                    case "加入副本":
                        Intent intent2 = new Intent(this, AddFubengActivity.class);
                        addTasksLauncher.launch(intent2);
                        return true;
                    case "排序":
                        Toast.makeText(this, "你已经排序成功", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            });
            popupMenu.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
