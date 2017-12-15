package com.zlt.funny;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start_to_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.et_name_edit);
                String name = editText.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(MainActivity.this,"请设置姓名!",Toast.LENGTH_SHORT).show();
                    return;
                }

                ToggleButton toggleButton = (ToggleButton) findViewById(R.id.tb_sex_btn);
                String sex = toggleButton.isChecked() ? "m" : "w";
                Intent intent = new Intent(MainActivity.this, MainActivity1.class);
                intent.putExtra("name", name);
                intent.putExtra("sex", sex);
                startActivity(intent);
            }
        });
    }
}
