package com.example.demos.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.demos.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by 524202 on 2017/12/20.
 */

public class ShellCmdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell_cmd);
        TextView textView = (TextView) findViewById(R.id.text_view);

        try {
            Process p = Runtime.getRuntime().exec("mkdir test");
            String data = null;
            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String error = null;
            while ((error = ie.readLine()) != null
                    && !error.equals("null")) {
                data += error + "\n";
            }
            String line = null;
            while ((line = in.readLine()) != null
                    && !line.equals("null")) {
                data += line + "\n";
            }
            textView.setText(data);
        } catch (Exception e) {
            textView.setText(e.toString());
        }
    }

}
