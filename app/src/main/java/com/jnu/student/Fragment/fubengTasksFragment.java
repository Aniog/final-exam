package com.jnu.student.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jnu.student.R;
import com.jnu.student.data.tongjiDataTasks;
import com.jnu.student.data.meizhouDataTasks;
import com.jnu.student.data.MyfirstTasks;

import java.util.ArrayList;

public class fubengTasksFragment extends Fragment {
    public static int fubeng_score = 0;
    private RecyclerView tasksRecyclerView;
    private fubengTasksFragment.TasksAdapter tasksAdapter;

    private ArrayList<MyfirstTasks> fubeng_tasks;
    public fubengTasksFragment() {
        // Required empty public constructor
    }

    public static fubengTasksFragment newInstance(String param1, String param2) {
        fubengTasksFragment fragment = new fubengTasksFragment();
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
        tasksRecyclerView = rootView.findViewById(R.id.recyclerview_main);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tasksRecyclerView.setLayoutManager(linearLayoutManager);
        fubeng_tasks = new meizhouDataTasks().LoadTasks(this.getContext());
        if(fubeng_tasks.size() == 0)
        {
            View root= inflater.inflate(R.layout.empty_tasks, container, false);
            TextView textView1 = root.findViewById(R.id.empty_textView1);
            textView1.setText("用户可自行点击右上角加号添加");
            return root;
        }
        tasksAdapter = new fubengTasksFragment.TasksAdapter(fubeng_tasks);
        tasksRecyclerView.setAdapter(tasksAdapter);
        registerForContextMenu(tasksRecyclerView);


        return rootView;
    }
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getGroupId() != 1)
        {
            return false;
        }
        switch (item.getItemId()) {
            case 0:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getContext());
                builder1.setTitle("添加提醒");
                builder1.setMessage("请注意完成");
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
                        fubeng_tasks.remove(item.getOrder());
                        tasksAdapter.notifyItemRemoved(item.getOrder());
                        new meizhouDataTasks().SaveTasks(fubengTasksFragment.this.getContext(),fubeng_tasks);
                    }
                });
                builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder2.create().show();
                // Do something for item 2
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
    public class TasksAdapter extends RecyclerView.Adapter<fubengTasksFragment.TasksAdapter.ViewHolder> {

        private ArrayList<MyfirstTasks> myfirstTasksArrayList;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;
            private final TextView textViewScore;
            private final CheckBox checkBox;

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");

                menu.add(1, 0, this.getAdapterPosition(), "添加提醒" + this.getAdapterPosition());
                menu.add(1, 1, this.getAdapterPosition(), "删除" + this.getAdapterPosition());
            }

            public ViewHolder(View tasksView) {
                super(tasksView);
                textViewTitle = tasksView.findViewById(R.id.text_title);
                textViewScore = tasksView.findViewById(R.id.text_score);
                checkBox = tasksView.findViewById(R.id.checkBox); // 初始化 CheckBox
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            TextView scoreTextView = getTextViewScore();
                            fubeng_score = Integer.parseInt(scoreTextView.getText().toString());


                            ArrayList<MyfirstTasks> finish_task = new tongjiDataTasks().LoadTasks(getContext());
                            finish_task.add(new MyfirstTasks(textViewTitle.getText().toString(), fubeng_score));
                            new tongjiDataTasks().SaveTasks(getContext(),finish_task);

                            buttonView.setChecked(false);
                            if (getActivity() != null) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("fubengScore", fubeng_score);
                                getParentFragmentManager().setFragmentResult("updateScore", bundle);
                            }
                        } else {

                        }
                    }
                });
                tasksView.setOnCreateContextMenuListener(this);
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
        public fubengTasksFragment.TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.tasks_show, viewGroup, false);

            return new fubengTasksFragment.TasksAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(fubengTasksFragment.TasksAdapter.ViewHolder viewHolder, final int position) {
            viewHolder.getTextViewTitle().setText(myfirstTasksArrayList.get(position).getTitle());
            viewHolder.getTextViewScore().setText(myfirstTasksArrayList.get(position).getScore()+ "");
        }

        @Override
        public int getItemCount() {
            return myfirstTasksArrayList.size();
        }
    }
}