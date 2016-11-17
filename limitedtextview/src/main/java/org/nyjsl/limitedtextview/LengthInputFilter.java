package org.nyjsl.limitedtextview;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by pc on 2016/11/17.
 */

public class LengthInputFilter extends InputFilter.LengthFilter {

    private String overFlowStr = "";

    public LengthInputFilter(int max,String overFlowStr) {
        super(max);
        this.overFlowStr = overFlowStr;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        final CharSequence result = super.filter(source, start, end, dest, dstart, dend);
        return result + overFlowStr;
    }
}
