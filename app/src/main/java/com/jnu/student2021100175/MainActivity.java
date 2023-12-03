package com.jnu.student2021100175;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.student.R;
import com.jnu.student2021100175.data.Book;
import com.jnu.student2021100175.data.DataBank;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    BooksAdapter booksAdapter;
    ArrayList<Book> books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        RecyclerView mainRecyclerView = findViewById(R.id.recyclerview_main);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器


        books = new DataBank().LoadBook(this.getApplicationContext());


        if(books.size() == 0)
        {
            books.add(new Book("信息安全数学基础（第2版）",R.drawable.book_1));
            books.add(new Book("软件项目管理案例教程（第4版）",R.drawable.book_2));
            books.add(new Book("创新工程实践",R.drawable.book_no_name));
        }
        //ShopItemAdapter shopItemAdapter = new ShopItemAdapter(books);
        booksAdapter = new BooksAdapter(books);
        mainRecyclerView.setAdapter(booksAdapter);

        registerForContextMenu(mainRecyclerView);//注册上下文菜单


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
                        new DataBank().SaveBooks(this.getApplicationContext(),books);
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
                        new DataBank().SaveBooks(this.getApplicationContext(),books);
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
    }

    private int getRandomDrawableResource() {

        int[] drawableResources = {R.drawable.book_no_name, R.drawable.book_1, R.drawable.book_2};


        int randomIndex = new Random().nextInt(drawableResources.length);


        return drawableResources[randomIndex];
    }
    ActivityResultLauncher<Intent> addItemLauncher;
    ActivityResultLauncher<Intent> UpdateItemLauncher;
    //点击响应
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this,"添加"+item.getOrder(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);
                addItemLauncher.launch(intent);

                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure you want to delete this data?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        books.remove(item.getOrder());
                        booksAdapter.notifyItemRemoved(item.getOrder());
                        new DataBank().SaveBooks(MainActivity.this,books);
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
                Intent intentUpdate = new Intent(MainActivity.this, BookDetailsActivity.class);
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

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
            private final TextView textViewBookTitle;
            private final ImageView iamgeViewItem;

            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo){
                menu.setHeaderTitle("具体操作");
                position = this.getAdapterPosition();
                menu.add(0,0, this.getAdapterPosition(),"添加");
                menu.add(0,1, this.getAdapterPosition(),"删除");
                menu.add(0,2, this.getAdapterPosition(),"修改");
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