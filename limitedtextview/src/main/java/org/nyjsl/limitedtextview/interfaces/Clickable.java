package org.nyjsl.limitedtextview.interfaces;

import android.view.View;

public interface Clickable extends StateInterface {

    int EXPAND_MODE_NONE = 0;
    int EXPAND_EXPAND_ONLY = 1;
    int EXPAND_BOTH= 2;
    void onClick(View view);
    void toggle(View view);

}