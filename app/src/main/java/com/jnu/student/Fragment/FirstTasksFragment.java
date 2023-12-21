package com.jnu.student.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.R;
import com.jnu.student.data.DataScore;

public class FirstTasksFragment extends Fragment {
    private String []tabHeaderStrings = {"每日任务","每周任务","普通任务","副本任务"};
    private static int score;
    public FirstTasksFragment() {
    }

    public static FirstTasksFragment newInstance(String param1, String param2) {
        FirstTasksFragment fragment = new FirstTasksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tab_tasks, container, false);
        ViewPager2 viewPager = root.findViewById(R.id.button_view_pager);
        TabLayout tabLayout = root.findViewById(R.id.tab_layout);
        TextView textView = root.findViewById(R.id.textView);
        score = new DataScore().loadScore(this.getContext());
        textView.setText(String.valueOf(score));

        getParentFragmentManager().setFragmentResultListener("updateScore", this, (requestKey, result) -> {
            score = new DataScore().loadScore(this.getContext());
            int updatedScore = score;
            if (result.containsKey("dailyScore")) {
                updatedScore += result.getInt("dailyScore");
            }
            if (result.containsKey("weeklyScore")) {
                updatedScore += result.getInt("weeklyScore");
            }
            if (result.containsKey("generalScore")) {
                updatedScore += result.getInt("generalScore");
            }
            // 更新 TextView 的显示
            textView.setText(String.valueOf(updatedScore));
            new DataScore().saveScore(this.getContext(),updatedScore);
            if (getActivity() != null) {
                int all_score = new DataScore().loadScore(this.getContext());
                Bundle bundle = new Bundle();
                bundle.putInt("allScore", all_score);
                getParentFragmentManager().setFragmentResult("AllScore", bundle);
            }
        });
        //找到按钮
        Button buttonZero = root.findViewById(R.id.button_zero);
        // 设置按钮点击事件监听器
        buttonZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在此处执行按钮点击后的操作
                Toast.makeText(getContext(), "清除", Toast.LENGTH_SHORT).show();
                textView.setText(String.valueOf(0));
                new DataScore().saveScore(getContext(),0);
            }
        });
        // 创建适配器
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);

        // 将TabLayout和ViewPager2进行关联
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabHeaderStrings[position])
        ).attach();
        return root;
    }
    public class FragmentAdapter extends FragmentStateAdapter {
        private static final int NUM_TABS = 4;
        public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // 根据位置返回对应的Fragment实例
            switch (position) {
                case 0:
                    return new meiriTasksFragment();
                case 1:
                    return new meizhouTasksFragment();
                case 2:
                    return new putongTasksFragment();
                case 3:
                    return new fubengTasksFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_TABS;
        }
    }
}