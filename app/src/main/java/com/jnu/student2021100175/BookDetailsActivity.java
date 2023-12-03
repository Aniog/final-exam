package com.jnu.student2021100175;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jnu.student.R;

public class BookDetailsActivity extends AppCompatActivity  {
    private  int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Intent intent = getIntent();
        if(intent != null){
            String title = intent.getStringExtra("title");

            if(null != title)
            {
                position = intent.getIntExtra("position",-1);
                EditText editBookName = findViewById(R.id.edittext_book_name);
                editBookName.setText(title);
            }
        }
        Button buttonOk = findViewById(R.id.button_item_details_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EditText editBookTitle = findViewById(R.id.edittext_book_name);
                intent.putExtra("title",editBookTitle.getText().toString());
                intent.putExtra("position",position);
                setResult(Activity.RESULT_OK,intent);
                BookDetailsActivity.this.finish();
            }
        });
    }
}