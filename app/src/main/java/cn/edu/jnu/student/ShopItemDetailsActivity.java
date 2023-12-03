package cn.edu.jnu.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.edu.jnu.st2021101996.R;

public class ShopItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_item_details);

        Button buttonOK=findViewById(R.id.button_item_details_ok);
        buttonOK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("key","Some result data");
                setResult(Activity.RESULT_OK,intent);
                ShopItemDetailsActivity.this.finish();
            }
        });

     }
}