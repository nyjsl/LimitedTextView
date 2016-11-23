package org.nyjsl.limitedtextview.interfaces;

import android.view.View;

public interface Expandable {

    int STATE_SHRINK = 0;
    int STATE_EXPAND = 1;

    void onExpand(View view);
    void onShrink(View view);

}