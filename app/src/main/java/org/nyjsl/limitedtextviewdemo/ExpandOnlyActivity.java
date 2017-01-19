package org.nyjsl.limitedtextviewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by pc on 2016/11/24.
 */

public class ExpandOnlyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //use default config
        setContentView(R.layout.activity_expand_only);
        TextView textView = (TextView) findViewById(R.id.tv);
    }
}