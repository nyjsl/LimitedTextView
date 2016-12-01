package org.nyjsl.limitedtextview;

import android.view.View;

import org.nyjsl.limitedtextview.interfaces.Clickable;
import org.nyjsl.limitedtextview.interfaces.Expandable;
import org.nyjsl.limitedtextview.interfaces.Toggable;

/**
 * Created by pc on 2016/11/21.
 */

public class ClickImpl implements Clickable {


    public ClickImpl(int mCurrState, int mode) {
        this.mCurrState = mCurrState;
        this.mode = mode;
    }

    public void setmCurrState(int mCurrState) {
        this.mCurrState = mCurrState;
    }

    protected int mCurrState = 0;

    protected int mode = 0;

    public int getmCurrState() {
        return mCurrState;
    }

    @Override
    public void onClick(View view) {
        if(mode == EXPAND_MODE_NONE){
            return ;
        }
        switch (mCurrState){
            case STATE_SHRINK:
                if(mode == EXPAND_SHRINK_ONLY){
                    break;
                }
                if(view instanceof Expandable){
                    mCurrState = STATE_EXPAND;
                    Expandable exp = (Expandable) view;
                    exp.onExpand(view);
                }
                break;
            case STATE_EXPAND:
                if(mode == EXPAND_EXPAND_ONLY){
                    break;
                }
                if(view instanceof Expandable) {
                    mCurrState = STATE_SHRINK;
                    Expandable exp = (Expandable) view;
                    exp.onShrink(view);
                }
                break;
        }
        if(view instanceof LimitedTextView){
            LimitedTextView ltv = (LimitedTextView) view;
            ltv.setmCurrState(mCurrState);
        }
        toggle(view);
    }

    @Override
    public void toggle(View view) {
        if(view instanceof Toggable){
            Toggable toggable = (Toggable) view;
            toggable.toggle();
        }
    }
}
