package com.jnu.student.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jnu.student.Fragment.FirstTasksFragment;
import com.jnu.student.Fragment.jiangliFragment;
import com.jnu.student.Fragment.meiriTasksFragment;
import com.jnu.student.Fragment.tongjiFragment;
import com.jnu.student.R;
import com.jnu.student.data.meiriDataTasks;
import com.jnu.student.data.putongdataTasks;
import com.jnu.student.data.jiangliTasks;
import com.jnu.student.data.meizhouDataTasks;
import com.jnu.student.data.MyfirstTasks;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Fragment tasksFragment = new FirstTasksFragment();//任务
    private Fragment tongjiFragment = new tongjiFragment();
    private Fragment jiangliFragment = new jiangliFragment();
    private int tabposition;
    private Fragment statisticsFragment = new meiriTasksFragment();//统计
    private BottomNavigationView btmNavView;

    private ActivityResultLauncher<Intent> addTasksLauncher;
    private ActivityResultLauncher<Intent> addjiangliLauncher;

    private ActivityResultLauncher<Intent> addDungeonLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_tasks);
        // 第一次加载，显示任务页面
        if (savedInstanceState == null) {
            Fragment tasksFragment = new FirstTasksFragment();
            loadTasksFragment(tasksFragment);
        }
        // 为启动带有返回结果的活动(Activity)注册一个处理程序(添加任务)
        addTasksLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        String tasks_type = data.getStringExtra("task_type");
                        if("daily".equals(tasks_type)) //每日任务
                        {
                            ArrayList<MyfirstTasks> daily_tasks = new meiriDataTasks().LoadTasks(this);
                            int score = Integer.parseInt(data.getStringExtra("score"));
                            String title = data.getStringExtra("title");
                            String tags = data.getStringExtra("tags");
                            daily_tasks.add(new MyfirstTasks(title,score));
                            new meiriDataTasks().SaveTasks(this,daily_tasks);
                            loadTasksFragment(new FirstTasksFragment());
                        } else if ("weekly".equals(tasks_type)) { //每周任务
                            ArrayList<MyfirstTasks> weekly_tasks = new meizhouDataTasks().LoadTasks(this);
                            int score = Integer.parseInt(data.getStringExtra("score"));
                            String title = data.getStringExtra("title");
                            String tags = data.getStringExtra("tags");
                            weekly_tasks.add(new MyfirstTasks(title,score));
                            new meizhouDataTasks().SaveTasks(this,weekly_tasks);
                            loadTasksFragment(new FirstTasksFragment(1));
                        } else if ("regular".equals(tasks_type)) { //普通任务
                            ArrayList<MyfirstTasks> general_tasks = new putongdataTasks().LoadTasks(this);
                            int score = Integer.parseInt(data.getStringExtra("score"));
                            String title = data.getStringExtra("title");
                            String tags = data.getStringExtra("tags");
                            general_tasks.add(new MyfirstTasks(title,score));
                            new putongdataTasks().SaveTasks(this,general_tasks);
                            loadTasksFragment(new FirstTasksFragment(2));
                        } else if("fubeng".equals(tasks_type)){
                            ArrayList<MyfirstTasks> fubeng_tasks = new putongdataTasks().LoadTasks(this);
                            int score = Integer.parseInt(data.getStringExtra("score"));
                            String title = data.getStringExtra("title");
                            String tags = data.getStringExtra("tags");
                            fubeng_tasks.add(new MyfirstTasks(title,score));
                            new putongdataTasks().SaveTasks(this,fubeng_tasks);
                            loadTasksFragment(new FirstTasksFragment(3));
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        addjiangliLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        ArrayList<MyfirstTasks> reward_tasks = new jiangliTasks().LoadTasks(this);
                        int score = Integer.parseInt(data.getStringExtra("reward_score"));
                        String title = data.getStringExtra("reward_title");
                        reward_tasks.add(new MyfirstTasks(title,-score));
                        new jiangliTasks().SaveTasks(this,reward_tasks);
                        loadTasksFragment(new jiangliFragment());
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );

        btmNavView = findViewById(R.id.bottom_menu);
        btmNavView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_rengwu) {
                loadTasksFragment(tasksFragment);
                return true;
            }
            if (item.getItemId() == R.id.navigation_wo) {

                return true;
            }
            if (item.getItemId() == R.id.navigation_tongji) {
                loadTasksFragment(tongjiFragment);
                return true;
            }
            if (item.getItemId() == R.id.navigation_jiangli) {
                loadTasksFragment(jiangliFragment);
                return true;
            }
            return false;
        });

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.setFragmentResultListener("tabposition", this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        // 获取从 Fragment 传来的数据
                        int position = result.getInt("position", tabposition);
                        tabposition = position;

                    }
                });
    }

    // 加载Fragment的方法
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

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.btn_msg)
        {

            PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.btn_msg));
            popupMenu.getMenu().add("新建任务");
            popupMenu.getMenu().add("新建奖励");
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getTitle().toString()) {
                    case "新建任务":

                        Intent intent1 = new Intent(this, AddTasksActivity.class);
                        addTasksLauncher.launch(intent1);

                        return true;
                    case "新建奖励":
                        Intent intent2 = new Intent(this, AddjiangliActivity.class);
                        addjiangliLauncher.launch(intent2);
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
