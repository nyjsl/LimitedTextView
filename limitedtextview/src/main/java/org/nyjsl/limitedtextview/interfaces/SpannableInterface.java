package org.nyjsl.limitedtextview.interfaces;

import org.nyjsl.limitedtextview.ClickImpl;

/**
 * Created by pc on 2016/11/21.
 */

public interface SpannableInterface extends StateInterface{

    ClickImpl getClickable(int mode);
    void setClickableSteate(int state);
}
