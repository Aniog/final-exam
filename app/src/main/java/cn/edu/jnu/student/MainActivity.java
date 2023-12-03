package cn.edu.jnu.student;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;

import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import android.view.LayoutInflater;

import android.view.ViewGroup;

import cn.edu.jnu.st2021101996.R;
import cn.edu.jnu.student.data.bookItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mainrecyclerView = findViewById(R.id.recycle_view_books);
        mainrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<bookItem> bookItems = new ArrayList<>();
        for (int iLoop = 0; iLoop < 20; iLoop++) {
            bookItems.add(new bookItem("移动项目管理教程", R.drawable.book_1));
            bookItems.add(new bookItem("创新工程实践", R.drawable.book_2));
            bookItems.add(new bookItem("信息安全数学基础", R.drawable.book_1));
        }
        MyAdapter myAdapter = new MyAdapter(bookItems);
        mainrecyclerView.setAdapter(myAdapter);
        addItemLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode()== Activity.RESULT_OK){
                        Intent data = result.getData();
                        String name = data.getStringExtra("name");
                        String priceText=data.getStringExtra("price");//获取返回的数据

                        double price=Double.parseDouble(priceText);
                        bookItems.add(new bookItem(name, R.drawable.book_1));
                        myAdapter.notifyItemInserted(bookItems.size());
                    } else if (result.getResultCode()==Activity.RESULT_CANCELED){

                    }
                }
        );
    }

    ActivityResultLauncher<Intent> addItemLauncher;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()){
            case 0:
                Intent intent = new Intent(MainActivity.this, ShopItemDetailsActivity.class);
                addItemLauncher.launch(intent);
                break;
            case 1:

                break;
            case 2:

                break;
            default:
                return super.onContextItemSelected(item);
        }

        return true;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private final ArrayList<bookItem> data;

        public MyAdapter(ArrayList<bookItem> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_item_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.getTextView().setText(data.get(position).getTextId());
            holder.getImageView().setImageResource(data.get(position).getImageId());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            public TextView textView;
            public ImageView imageView;

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");
                menu.add(0, 0, this.getAdapterPosition(), "添加" + this.getAdapterPosition());
                menu.add(0, 1, this.getAdapterPosition(), "删除" + this.getAdapterPosition());
                menu.add(0, 2, this.getAdapterPosition(), "修改" + this.getAdapterPosition());
            }

            public ViewHolder(View shopItemView) {
                super(shopItemView);
                textView = shopItemView.findViewById(R.id.textview_book);
                imageView = shopItemView.findViewById(R.id.imageView);
                shopItemView.setOnCreateContextMenuListener(this);
            }

            public TextView getTextView() {
                return textView;
            }

            public ImageView getImageView() {
                return imageView;
            }


        }
    }
}



