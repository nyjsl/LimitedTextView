package org.nyjsl.limitedtextview;

import android.text.Spannable;
import android.view.MotionEvent;
import android.widget.TextView;

public class ImageTouchMovementMethod extends GenericLinkeMovmentMethod<ImageClickableSpan> {

    @Override
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||action == MotionEvent.ACTION_DOWN) {

            ImageClickableSpan clickableImageSpan= getPressedSpan(textView,spannable,event,ImageClickableSpan.class);
            if (null != clickableImageSpan) {
                if (action == MotionEvent.ACTION_UP) {
                    mPressedSpan.getClickable().onClick(textView);
                }
                return true;
            }
        }

        return super.onTouchEvent(textView, spannable, event);
    }

}