package org.nyjsl.limitedtextview.spannable;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import org.nyjsl.limitedtextview.ClickImpl;
import org.nyjsl.limitedtextview.interfaces.SpannableInterface;

public class TextClickableSpan extends ClickableSpan implements SpannableInterface {

    private static final int STATE_SHRINK = 0;
    private static final int STATE_EXPAND = 1;

    private boolean mIsPressed;
    public void setPressed(boolean isSelected) {
        mIsPressed = isSelected;
    }

    private int mToExpandHintColor = 0xFF3498DB;
    private int mToShrinkHintColor = 0xFFE74C3C;
    private int mToExpandHintColorBgPressed = 0x55999999;
    private int mToShrinkHintColorBgPressed = 0x55999999;

    private ClickImpl clickable  = null;

    public TextClickableSpan(int mode,int mToExpandHintColor, int mToShrinkHintColor, int mToExpandHintColorBgPressed, int mToShrinkHintColorBgPressed) {
        clickable = getClickable(0,mode);
        this.mToExpandHintColor = mToExpandHintColor;
        this.mToShrinkHintColor = mToShrinkHintColor;
        this.mToExpandHintColorBgPressed = mToExpandHintColorBgPressed;
        this.mToShrinkHintColorBgPressed = mToShrinkHintColorBgPressed;
    }



    @Override
    public void onClick(View widget) {
        clickable.onClick(widget);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        switch (clickable.getmCurrState()){
            case STATE_SHRINK:
                ds.setColor(mToExpandHintColor);
                ds.bgColor = mIsPressed ? mToExpandHintColorBgPressed : 0;
                break;
            case STATE_EXPAND:
                ds.setColor(mToShrinkHintColor);
                ds.bgColor = mIsPressed ? mToShrinkHintColorBgPressed : 0;
                break;
        }
        ds.setUnderlineText(false);
    }


    @Override
    public ClickImpl getClickable(int status, int mode) {
        return new ClickImpl(status,mode);
    }
}