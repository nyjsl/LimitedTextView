package org.nyjsl.limitedtextview.interfaces;

import android.view.View;

public interface Expandable extends StateInterface {
    void onExpand(View view);
    void onShrink(View view);
}