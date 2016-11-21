package org.nyjsl.limitedtextview;

import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by pc on 2016/11/21.
 */

public abstract class TogglableSpan extends ClickableSpan  implements Clickable {

    protected int mCurrState = 0;

    protected int mode = 0;

    public void togle(View view) {

        if(mode == EXPAND_MODE_NONE){
            return ;
        }
        switch (mCurrState){
            case STATE_SHRINK:
                mCurrState = STATE_EXPAND;
                if(view instanceof Expandable){
                    Expandable exp = (Expandable) view;
                    exp.onExpand(view);
                }
                break;
            case STATE_EXPAND:
                if(mode == EXPAND_EXPAND_ONLY){
                    return ;
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
    }
}
