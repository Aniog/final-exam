package com.jnu.student.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.R;
import com.jnu.student.data.tongjiDataTasks;
import com.jnu.student.data.MyfirstTasks;

import java.util.ArrayList;

public class tongjiFragment extends Fragment {
    private ArrayList<MyfirstTasks> finish_tasks;
    private RecyclerView tasksRecyclerView;
    private TasksAdapter tasksAdapter;
    public tongjiFragment() {
        // Required empty public constructor
    }

    public static tongjiFragment newInstance(String param1, String param2) {
        tongjiFragment fragment = new tongjiFragment();
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
        View rootView = inflater.inflate(R.layout.meiri_tasks, container, false);
        tasksRecyclerView = rootView.findViewById(R.id.recycle_meiri);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tasksRecyclerView.setLayoutManager(linearLayoutManager);
        finish_tasks = new tongjiDataTasks().LoadTasks(this.getContext());
        if(finish_tasks.size() == 0)
        {
            View root= inflater.inflate(R.layout.empty_tasks, container, false);
            TextView textView1 = root.findViewById(R.id.empty_textView1);
            textView1.setText("暂无完成任务");
            return root;
        }

        tasksAdapter = new TasksAdapter(finish_tasks);
        tasksRecyclerView.setAdapter(tasksAdapter);
        registerForContextMenu(tasksRecyclerView);
        return rootView;
    }

    public boolean onContextItemSelected(MenuItem item) {
        if(item.getGroupId() != 4)
        {
            return false;
        }
        switch (item.getItemId()) {
            case 0:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getContext());
                builder1.setTitle("添加提醒");
                builder1.setMessage("记得添加呀");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder1.create().show();
                break;
            case 1:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this.getContext());
                builder2.setTitle("删除");
                builder2.setMessage("你要删除吗?");
                builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "确定按钮被点击", Toast.LENGTH_SHORT).show();
                        finish_tasks.remove(item.getOrder());
                        tasksAdapter.notifyItemRemoved(item.getOrder());
                        new tongjiDataTasks().SaveTasks(tongjiFragment.this.getContext(),finish_tasks);
                    }
                });
                builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder2.create().show();
                break;

            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
    public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

        private ArrayList<MyfirstTasks> myfirstTasksArrayList;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;
            private final TextView textViewScore;
            private final CheckBox checkBox;

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");

                menu.add(4, 0, this.getAdapterPosition(), "添加提醒");
                menu.add(4, 1, this.getAdapterPosition(), "删除" );
            }

            public ViewHolder(View tasksView) {
                super(tasksView);
                textViewTitle = tasksView.findViewById(R.id.text_title);
                textViewScore = tasksView.findViewById(R.id.text_score);
                checkBox = tasksView.findViewById(R.id.checkBox); // 初始化 CheckBox
                tasksView.setOnCreateContextMenuListener(this);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (getActivity() != null) {
                            }
                        } else {
                        }
                    }
                });
            }

            public TextView getTextViewTitle() {
                return textViewTitle;
            }

            public TextView getTextViewScore() {
                return textViewScore;
            }

        }
        public TasksAdapter(ArrayList<MyfirstTasks> tasks) {
            myfirstTasksArrayList = tasks;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.tasks_show, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.getTextViewTitle().setText(myfirstTasksArrayList.get(position).getTitle());
            viewHolder.getTextViewScore().setText(myfirstTasksArrayList.get(position).getScore()+ "");
        }

        public int getItemCount() {
            return myfirstTasksArrayList.size();
        }

        // 添加 CheckBox 的点击事件监听器
    }
}