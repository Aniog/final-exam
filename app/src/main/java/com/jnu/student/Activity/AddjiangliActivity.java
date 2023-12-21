package com.jnu.student.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jnu.student.R;

public class AddjiangliActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jiangli);

        Button buttonOk = findViewById(R.id.add_reward_button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EditText editRewardTitle = findViewById(R.id.add_reward_titleEditText);
                intent.putExtra("reward_title", editRewardTitle.getText().toString());
                EditText editScoreTitle = findViewById(R.id.reward_score_edit_text);
                intent.putExtra("reward_score", editScoreTitle.getText().toString());
                setResult(Activity.RESULT_OK, intent);

                AddjiangliActivity.this.finish();
            }
        });
        Button buttonCancel = findViewById(R.id.add_reward_button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddjiangliActivity.this.finish();
            }
        });
    }
}