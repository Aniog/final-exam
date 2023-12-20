package com.jnu.student.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jnu.student.Fragment.FirstTasksFragment;
import com.jnu.student.R;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView btmNavView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_tasks);

        // 第一次加载，显示任务页面
        if (savedInstanceState == null) {
            Fragment tasksFragment = new FirstTasksFragment();
            loadTasksFragment(tasksFragment);
        }

        // 获取底部导航栏
        btmNavView = findViewById(R.id.bottom_navigation_menu);
        btmNavView.setOnItemSelectedListener(item -> {
            // 处理底部导航栏项的选择
            if (item.getItemId() == R.id.navigation_rengwu) {
                // 用户点击了“任务”，加载ButtonTasksFragment
                Fragment tasksFragment = new FirstTasksFragment();
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

    @Override
    // 创建选项菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        // 通过 getMenuInflater() 方法获取一个 MenuInflater 对象，
        // 用于将 XML 文件解析并填充到 Menu 对象中
        getMenuInflater().inflate(R.menu.add, menu);
        // 返回父类的 onCreateOptionsMenu 方法的结果，
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.btn_msg)
        {
            // 创建一个 PopupMenu
            PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.btn_msg));
            // 在菜单中添加选项
            popupMenu.getMenu().add("新建任务");
            popupMenu.getMenu().add("加入副本");
            popupMenu.getMenu().add("排序");
            // 设置菜单项点击事件
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                // 处理菜单项点击事件
                switch (menuItem.getTitle().toString()) {
                    case "新建任务":
                        // 处理选项一点击事件
                        Toast.makeText(this, "新建任务", Toast.LENGTH_SHORT).show();
                        return true;
                    case "加入副本":
                        // 处理选项二点击事件
                        Toast.makeText(this, "加入副本任务", Toast.LENGTH_SHORT).show();
                        return true;
                    case "排序":
                        // 处理选项三点击事件
                        Toast.makeText(this, "排序", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            });
            // 显示 PopupMenu
            popupMenu.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}