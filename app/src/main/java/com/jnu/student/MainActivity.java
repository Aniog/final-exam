package com.jnu.student;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.view.Gravity;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView_hello = findViewById(R.id.text_vciew_hello);
        int stringId_hello = getResources().getIdentifier("hello","string",getPackageName());
        String hello = getString(stringId_hello);
        textView_hello.setText(hello);

        TextView textView_JNU = findViewById(R.id.text_vciew_JNU);
        int stringId_JNU = getResources().getIdentifier("JNU","string",getPackageName());
        String string_JNU = getString(stringId_JNU);
        textView_JNU.setText(string_JNU);

        Button button_change_text = (Button) findViewById(R.id.button_change_text);
        int stringId_change_text = getResources().getIdentifier("change_text","string",getPackageName());
        String string_change_text = getString(stringId_change_text);
        button_change_text.setText(string_change_text);

        Button button = findViewById(R.id.button_change_text);
        button.setOnClickListener(v -> {
            // do something...
            String temp = textView_JNU.getText().toString();
            textView_JNU.setText(textView_hello.getText().toString());
            textView_hello.setText(temp);

            Toast.makeText(MainActivity.this,"交换成功1",Toast.LENGTH_SHORT).show();
        });
        button_change_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = textView_JNU.getText().toString();
                textView_JNU.setText(textView_hello.getText().toString());
                textView_hello.setText(temp);

                //展示Toast
                Toast.makeText(MainActivity.this,"交换成功",Toast.LENGTH_SHORT).show();

                //展示AlertDialog
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("信息")
                        .setMessage("交换成功")
                        .setPositiveButton("确定",null)
                        .show();
            }
        });


    }
}