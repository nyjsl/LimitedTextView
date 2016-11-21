package org.nyjsl.limitedtextview;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class TextClickableSpan extends ClickableSpan implements Clickable {

    private static final int STATE_SHRINK = 0;
    private static final int STATE_EXPAND = 1;

    private boolean mIsPressed;
    public void setPressed(boolean isSelected) {
        mIsPressed = isSelected;
    }

    private int mCurrState = 0;

    private int mToExpandHintColor = 0xFF3498DB;
    private int mToShrinkHintColor = 0xFFE74C3C;
    private int mToExpandHintColorBgPressed = 0x55999999;
    private int mToShrinkHintColorBgPressed = 0x55999999;

    private SpannableClickListener listener = null;

    public TextClickableSpan(int mToExpandHintColor, int mToShrinkHintColor, int mToExpandHintColorBgPressed, int mToShrinkHintColorBgPressed,SpannableClickListener listener) {
        this.mToExpandHintColor = mToExpandHintColor;
        this.mToShrinkHintColor = mToShrinkHintColor;
        this.mToExpandHintColorBgPressed = mToExpandHintColorBgPressed;
        this.mToShrinkHintColorBgPressed = mToShrinkHintColorBgPressed;
        this.listener = listener;
    }

    @Override
    public void togle(View view) {
        switch (mCurrState){
            case STATE_SHRINK:
                mCurrState = STATE_EXPAND;
                if(view instanceof Expandable){
                    Expandable exp = (Expandable) view;
                    exp.onExpand(view);
                }
                break;
            case STATE_EXPAND:
                mCurrState = STATE_SHRINK;
                if(view instanceof Expandable){
                Expandable exp = (Expandable) view;
                exp.onShrink(view);
            }
                break;
        }
        if(view instanceof LimitedTextView){
            LimitedTextView ltv = (LimitedTextView) view;
            ltv.setmCurrState(mCurrState);
        }
    }

    @Override
    public void onClick(View widget) {
        if(widget instanceof LimitedTextView){
            LimitedTextView view = (LimitedTextView) widget;
            togle(view);
        }
        listener.onClick(this);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        switch (mCurrState){
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
}