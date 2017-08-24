package com.example.a524202.asdemos;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class DaggerActivity extends AppCompatActivity {

    private static final String TAG = "DaggerActivity";
    private Button next = null;
    private TextView message = null;
    private float density = 0;

    @Inject
    DaggerPresenter presenter;

    @Inject
    OkHttpClient client;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        density = Resources.getSystem().getDisplayMetrics().density;

        message = (TextView) findViewById(R.id.message);
        next = (Button) findViewById(R.id.next);

        Log.i(TAG, "client = " + (client == null ? "null" : client));
        Log.i(TAG, "retrofit = " + (retrofit == null ? "null" : retrofit));

        AppComponent appComponent = ((MyApplication) getApplication()).getAppComponent();

        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
        presenter.showUserName();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                switch (resultCode) {
                    case 2:
                        message.setText(data.getStringExtra("result"));
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    public void showUserName(String name) {
        next.setText(name);
    }
}
