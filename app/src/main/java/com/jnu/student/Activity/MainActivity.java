package com.jnu.student.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jnu.student.Fragment.ButtonTasksFragment;
import com.jnu.student.R;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView btmNavView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navegation);

        // 第一次加载，显示任务页面
        if (savedInstanceState == null) {
            Fragment tasksFragment = new ButtonTasksFragment();
            loadTasksFragment(tasksFragment);
        }

        // 获取底部导航栏
        btmNavView = findViewById(R.id.bottom_navigation_menu);
        btmNavView.setOnItemSelectedListener(item -> {
            // 处理底部导航栏项的选择
            if (item.getItemId() == R.id.navigation_rengwu) {
                // 用户点击了“任务”，加载ButtonTasksFragment
                Fragment tasksFragment = new ButtonTasksFragment();
                loadTasksFragment(tasksFragment);
                return true;
            }
            if (item.getItemId() == R.id.navigation_jiangli) {
                // 用户点击了“奖励”，这里可以添加对应逻辑
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

    // 加载Fragment的方法
    private void loadTasksFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}