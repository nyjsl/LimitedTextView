package org.nyjsl.limitedtextview;

import android.view.View;

public interface Clickable {


    int STATE_SHRINK = 0;

    int STATE_EXPAND = 1;


    void onClick(View view);

    void togle(View view);
}