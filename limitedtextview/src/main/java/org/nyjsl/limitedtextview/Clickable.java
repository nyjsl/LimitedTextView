package org.nyjsl.limitedtextview;

import android.view.View;

public interface Clickable {


    int STATE_SHRINK = 0;

    int STATE_EXPAND = 1;

    int EXPAND_MODE_NONE = 0;
    int EXPAND_EXPAND_ONLY = 1;
    int EXPAND_BOTH= 2;

    void onClick(View view);

    void toggle(View view);

}