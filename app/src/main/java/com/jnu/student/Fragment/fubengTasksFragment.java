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
public class fubengTasksFragment extends Fragment {
    private RecyclerView tasksRecyclerView;
    private fubengTasksFragment.TasksAdapter tasksAdapter;

    private ArrayList<Tasks> general_tasks;
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
        general_tasks = new ArrayList<>();
        general_tasks.add(new Tasks("和妈妈视频通话",100));
        general_tasks.add(new Tasks("在家做家务",20));
        general_tasks.add(new Tasks("给家里人制作小礼物",10));
        tasksAdapter = new fubengTasksFragment.TasksAdapter(general_tasks);
        tasksRecyclerView.setAdapter(tasksAdapter);
        registerForContextMenu(tasksRecyclerView);
        return rootView;
    }
    public class TasksAdapter extends RecyclerView.Adapter<fubengTasksFragment.TasksAdapter.ViewHolder> {

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

        @Override
        public fubengTasksFragment.TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.tasks_show, viewGroup, false);

            return new fubengTasksFragment.TasksAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(fubengTasksFragment.TasksAdapter.ViewHolder viewHolder, final int position) {
            viewHolder.getTextViewTitle().setText(tasksArrayList.get(position).getTitle());
            viewHolder.getTextViewScore().setText(tasksArrayList.get(position).getScore()+ "");
        }

        @Override
        public int getItemCount() {
            return tasksArrayList.size();
        }
    }
}