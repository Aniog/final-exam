package com.jnu.student2021100175.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnu.student2021100175.BookDetailsActivity;
import com.jnu.student.R;
import com.jnu.student2021100175.data.Book;
import com.jnu.student2021100175.data.DataBank;

import java.util.ArrayList;
import java.util.Random;

public class BookListFragment extends Fragment {
    private RecyclerView bookRecyclerView;
    private DataBank bookDataBank;
    private ArrayList<Book> books;
    private BooksAdapter booksAdapter;
    public BookListFragment() {
        // Required empty public constructor
    }

    public static BookListFragment newInstance() {
        BookListFragment fragment = new BookListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_book_list, container, false);
        bookRecyclerView = rootView.findViewById(R.id.recyclerview_main);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bookRecyclerView.setLayoutManager(linearLayoutManager);
        // 获取数据
        //books = new ArrayList<Book>();
        books = new DataBank().LoadBook(this.getContext());
        if(books.size() == 0)
        {
            books.add(new Book("信息安全数学基础（第2版）",R.drawable.book_1));
            books.add(new Book("软件项目管理案例教程（第4版）",R.drawable.book_2));
            books.add(new Book("创新工程实践",R.drawable.book_no_name));
        }
        booksAdapter = new BooksAdapter(books);
        bookRecyclerView.setAdapter(booksAdapter);

        registerForContextMenu(bookRecyclerView);//注册上下文菜单
        addItemLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        String title = data.getStringExtra("title");
                        int randomDrawable = getRandomDrawableResource();
                        books.add(new Book(title,randomDrawable));
                        booksAdapter.notifyItemInserted(books.size());
                        //保存数据
                        new DataBank().SaveBooks(this.getContext(),books);
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        UpdateItemLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        String title = data.getStringExtra("title");
                        int position = data.getIntExtra("position",0);
                        int randomDrawable = getRandomDrawableResource();
                        Book book = books.get(position);
                        book.setTitle(title);
                        book.setDrawable(randomDrawable);
                        booksAdapter.notifyItemChanged(position);
                        //保存数据
                        new DataBank().SaveBooks(this.getContext(),books);
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    }
                }
        );
        return rootView;
        //return inflater.inflate(R.layout.fragment_book_list, container, false);
    }
    //随机选择三张图片的某一张
    private int getRandomDrawableResource() {
        // Define an array of drawable resources
        int[] drawableResources = {R.drawable.book_no_name, R.drawable.book_1, R.drawable.book_2};

        // Generate a random index
        int randomIndex = new Random().nextInt(drawableResources.length);

        // Return the selected drawable resource
        return drawableResources[randomIndex];
    }
    ActivityResultLauncher<Intent> addItemLauncher;
    ActivityResultLauncher<Intent> UpdateItemLauncher;
    //点击响应
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent intent = new Intent(this.getContext(), BookDetailsActivity.class);
                addItemLauncher.launch(intent);
                // Do something for item 1
                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure you want to delete this data?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        books.remove(item.getOrder());
                        booksAdapter.notifyItemRemoved(item.getOrder());
                        new DataBank().SaveBooks(BookListFragment.this.getContext(),books);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
                // Do something for item 2
                break;
            case 2:
                Intent intentUpdate = new Intent(this.getContext(), BookDetailsActivity.class);
                Book mybook = books.get(item.getOrder());
                intentUpdate.putExtra("title",mybook.getTitle());
                intentUpdate.putExtra("position",item.getOrder());
                UpdateItemLauncher.launch(intentUpdate);
                // Do something for item 3
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
    public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
        private ArrayList<Book> bookArrayList;

        //定义删除的变量：
        int position;

        public BooksAdapter(ArrayList<Book> books) {
            bookArrayList = books;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.book_item_row, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.getTextViewBookTitle().setText(bookArrayList.get(position).getTitle());
            viewHolder.getIamgeViewItem().setImageResource(bookArrayList.get(position).getImageResourceId());
        }

        @Override
        public int getItemCount() {
            return bookArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewBookTitle;
            private final ImageView iamgeViewItem;

            //显示菜单
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");
                position = this.getAdapterPosition();
                menu.add(0, 0, this.getAdapterPosition(), "添加");
                menu.add(0, 1, this.getAdapterPosition(), "删除");
                menu.add(0, 2, this.getAdapterPosition(), "修改");
            }

            public ViewHolder(View shopItemView) {
                super(shopItemView);

                textViewBookTitle = shopItemView.findViewById(R.id.text_view_book_title);
                iamgeViewItem = shopItemView.findViewById(R.id.image_view_book_cover);
                shopItemView.setOnCreateContextMenuListener(this);
            }

            public ImageView getIamgeViewItem() {
                return iamgeViewItem;
            }

            public TextView getTextViewBookTitle() {
                return textViewBookTitle;
            }
        }
    }
}