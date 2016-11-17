package org.nyjsl.limitedtextviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.nyjsl.limitedtextview.LimitedTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LimitedTextView text = (LimitedTextView) findViewById(R.id.text);
    }
}
