package org.nyjsl.limitedtextviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by pc on 2016/11/23.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.expand_shrink_btn).setOnClickListener(this);
        findViewById(R.id.expand_only_btn).setOnClickListener(this);
        findViewById(R.id.text_overflow_btn).setOnClickListener(this);
        findViewById(R.id.drawable_overflow_btn).setOnClickListener(this);
        findViewById(R.id.composite_configuration_btn).setOnClickListener(this);
        findViewById(R.id.recyclerview_demo_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.expand_shrink_btn:
                startActivity(new Intent(MainActivity.this,ExpandShirkActivity.class));
                break;
            case R.id.expand_only_btn:
                break;
            case R.id.text_overflow_btn:
                break;
            case R.id.drawable_overflow_btn:
                break;
            case R.id.composite_configuration_btn:
                break;
            case R.id.recyclerview_demo_btn:
                startActivity(new Intent(MainActivity.this,RecyclerViewActivity.class));
                break;
            default:
                break;
        }
    }
}
