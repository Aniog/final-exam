package com.jnu.student.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jnu.student.R;
import com.jnu.student.data.Tasks;

import java.util.ArrayList;

public class meiriTasksFragment extends Fragment {
    private RecyclerView tasksRecyclerView;
    private meiriTasksFragment.TasksAdapter tasksAdapter;

    private ArrayList<Tasks> daily_tasks;
    public meiriTasksFragment() {
        // Required empty public constructor
    }

    public static meiriTasksFragment newInstance(String param1, String param2) {
        meiriTasksFragment fragment = new meiriTasksFragment();
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

        View rootView = inflater.inflate(R.layout.tasks_list, container, false);
        // 从实例化的布局中查找具有特定 ID（R.id.recyclerview_main）的 RecyclerView
        tasksRecyclerView = rootView.findViewById(R.id.recyclerview_main);
        // 创建一个 LinearLayoutManager 来管理 RecyclerView 中的项目
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        // 将 LinearLayoutManager 的方向设置为垂直
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tasksRecyclerView.setLayoutManager(linearLayoutManager);
        daily_tasks = new ArrayList<>();
        daily_tasks.add(new Tasks("熬夜看小说",-20));
        daily_tasks.add(new Tasks("学习Android开发",+10));
        daily_tasks.add(new Tasks("出去打篮球",10));
        tasksAdapter = new TasksAdapter(daily_tasks);
        tasksRecyclerView.setAdapter(tasksAdapter);
        registerForContextMenu(tasksRecyclerView);
        return rootView;
    }
    public class TasksAdapter extends RecyclerView.Adapter<meiriTasksFragment.TasksAdapter.ViewHolder> {

        private ArrayList<Tasks> tasksArrayList;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;
            private final TextView textViewScore;

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");

                menu.add(0, 0, this.getAdapterPosition(), "添加提醒" + this.getAdapterPosition());
                menu.add(0, 1, this.getAdapterPosition(), "删除" + this.getAdapterPosition());
            }

            public ViewHolder(View tasksView) {
                super(tasksView);
                // Define click listener for the ViewHolder's View

                textViewTitle = tasksView.findViewById(R.id.text_title);
                textViewScore = tasksView.findViewById(R.id.text_score);
                tasksView.setOnCreateContextMenuListener(this);
            }

            public TextView getTextViewTitle() {
                return textViewTitle;
            }

            public TextView getTextViewScore() {
                return textViewScore;
            }

        }

        public TasksAdapter(ArrayList<Tasks> tasks) {
            tasksArrayList = tasks;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.tasks_show, viewGroup, false);

            return new meiriTasksFragment.TasksAdapter.ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.getTextViewTitle().setText(tasksArrayList.get(position).getTitle());
            viewHolder.getTextViewScore().setText(tasksArrayList.get(position).getScore()+ "");
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return tasksArrayList.size();
        }
    }
}