package org.nyjsl.limitedtextview.linkmovmentmethod;

import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by pc on 2016/11/21.
 */

public abstract class GenericLinkeMovmentMethod<T> extends LinkMovementMethod {
    
    protected T mPressedSpan;

    protected T getPressedSpan(TextView textView, Spannable spannable, MotionEvent event,Class<T> klazz) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        x -= textView.getTotalPaddingLeft();
        y -= textView.getTotalPaddingTop();
        x += textView.getScrollX();
        y += textView.getScrollY();
        Layout layout = textView.getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);
        T[] link = spannable.getSpans(off, off, klazz);
        T touchedSpan = null;
        if (link.length > 0) {
            touchedSpan = link[0];
        }
        return touchedSpan;
    }
}
