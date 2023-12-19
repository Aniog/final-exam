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

public class meizhouTasksFragment extends Fragment {
    private RecyclerView tasksRecyclerView;
    private meizhouTasksFragment.TasksAdapter tasksAdapter;

    private ArrayList<Tasks> daily_tasks;
    public meizhouTasksFragment() {
        // Required empty public constructor
    }

    public static meizhouTasksFragment newInstance(String param1, String param2) {
        meizhouTasksFragment fragment = new meizhouTasksFragment();
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
        // 通过提供的 inflater 将 fragment_book_list 布局实例化为视图
        // rootView 将包含 fragment_book_list.xml 中定义的视图
        View rootView = inflater.inflate(R.layout.tasks_list, container, false);
        // 从实例化的布局中查找具有特定 ID（R.id.recyclerview_main）的 RecyclerView
        tasksRecyclerView = rootView.findViewById(R.id.recyclerview_main);
        // 创建一个 LinearLayoutManager 来管理 RecyclerView 中的项目
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        // 将 LinearLayoutManager 的方向设置为垂直
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tasksRecyclerView.setLayoutManager(linearLayoutManager);
        daily_tasks = new ArrayList<>();
        daily_tasks.add(new Tasks("完整的看完一本书",20));
        daily_tasks.add(new Tasks("成功实现Andorid开发的一项功能",30));
        daily_tasks.add(new Tasks("和女朋友约会",15));
        tasksAdapter = new TasksAdapter(daily_tasks);
        tasksRecyclerView.setAdapter(tasksAdapter);
        registerForContextMenu(tasksRecyclerView);
        return rootView;
    }
    public class TasksAdapter extends RecyclerView.Adapter<meizhouTasksFragment.TasksAdapter.ViewHolder> {

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

            return new meizhouTasksFragment.TasksAdapter.ViewHolder(view);
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