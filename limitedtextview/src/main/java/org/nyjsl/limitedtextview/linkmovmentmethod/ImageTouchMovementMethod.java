package org.nyjsl.limitedtextview.linkmovmentmethod;

import android.text.Spannable;
import android.view.MotionEvent;
import android.widget.TextView;

import org.nyjsl.limitedtextview.spannable.ImageClickableSpan;

public class ImageTouchMovementMethod extends GenericLinkeMovmentMethod<ImageClickableSpan> {

    @Override
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||action == MotionEvent.ACTION_DOWN) {

            mPressedSpan = getPressedSpan(textView,spannable,event,ImageClickableSpan.class);
            if (null != mPressedSpan) {
                if (action == MotionEvent.ACTION_UP) {
                    mPressedSpan.getClickable().onClick(textView);
                }
                return true;
            }
        }

        return super.onTouchEvent(textView, spannable, event);
    }

}