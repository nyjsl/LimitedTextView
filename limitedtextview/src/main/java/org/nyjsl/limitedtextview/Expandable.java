package org.nyjsl.limitedtextview;

import android.view.View;

public interface Expandable {

    void onExpand(View view);
    void onShrink(View view);

}