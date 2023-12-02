package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.student.data.ShopItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这是用布局实现
        setContentView(R.layout.activity_main);

        RecyclerView mainRecyclerView = findViewById(R.id.recyclerview_main);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器

        ArrayList<ShopItem> shopItems = new ArrayList<ShopItem>();
        for(int i = 0; i < 20; i++)
        {
            shopItems.add(new ShopItem("信息数学安全基础"+i,1.5,R.drawable.book_1));
            shopItems.add(new ShopItem("软件项目管理案例开发"+i,2.5,R.drawable.book_2));
            shopItems.add(new ShopItem("没有名字"+i,3.5,R.drawable.book_no_name));
        }
        ShopItemAdapter shopItemAdapter = new ShopItemAdapter(shopItems);
        mainRecyclerView.setAdapter(shopItemAdapter);

        registerForContextMenu(mainRecyclerView);



    }
    //点击响应
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case 0:
                // Do something for item 1
                break;
            case 1:
                // Do something for item 2
                break;
            case 2:
                // Do something for item 3
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ViewHolder> {
        private ArrayList<ShopItem> shopItemArrayList;

        public ShopItemAdapter(ArrayList<ShopItem> shopItems) {
            shopItemArrayList = shopItems;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.shop_item_row, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.getTextViewName().setText(shopItemArrayList.get(position).getName());
            viewHolder.getTextViewPrice().setText(shopItemArrayList.get(position).getPrice()+"");
            viewHolder.getIamgeViewItem().setImageResource(shopItemArrayList.get(position).getImageResourceId());
        }

        @Override
        public int getItemCount() {
            return shopItemArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
            private final TextView textViewName;
            private final TextView textViewPrice;
            private final ImageView iamgeViewItem;

            //显示菜单
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo){
                menu.setHeaderTitle("具体操作");
                menu.add(0,0, Menu.NONE,"添加");
                menu.add(0,0, Menu.NONE,"删除");
                menu.add(0,0, Menu.NONE,"修改");
            }

            public ViewHolder(View shopItemView) {
                super(shopItemView);

                textViewName = shopItemView.findViewById(R.id.textview_item_name);
                textViewPrice = shopItemView.findViewById(R.id.textview_item_price);
                iamgeViewItem = shopItemView.findViewById(R.id.imageview_item);
                shopItemView.setOnCreateContextMenuListener(this);
            }
            public ImageView getIamgeViewItem() {
                return iamgeViewItem;
            }
            public TextView getTextViewName() {
                return textViewName;
            }
            public TextView getTextViewPrice() {
                return textViewPrice;
            }
        }
    }
}