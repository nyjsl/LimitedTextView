package org.nyjsl.limitedtextview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by pc on 2016/11/17.
 */

public class LimitedTextView extends TextView{

    public LimitedTextView(Context context) {
        this(context,null);
    }

    public LimitedTextView(Context context, AttributeSet attrs) {
        this(context, attrs,R.attr.limitedTextViewStyle);
    }

    public LimitedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, R.style.LimitedTextView_Default);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        readStyle(context, attrs, defStyleAttr, defStyleRes);
        readAttrs(context, attrs, defStyleAttr, defStyleRes);
        setLengthFilter();
    }

    private LengthInputFilter lengthFilter;
    private int overFlowLength = 0;
    private String overFlowStr = "";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LimitedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }
    private void readAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a;
        a = context.obtainStyledAttributes(attrs, R.styleable.LimitedTextView, defStyleAttr, defStyleRes);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if(attr == R.styleable.LimitedTextView_overflow_len){
                overFlowLength = a.getInt(attr,0);
            }else if (attr == R.styleable.LimitedTextView_overflow_str){
                overFlowStr = a.getString(attr);
            }
        }
        a.recycle();
    }

    private void readStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Themes_LimitedTextView, defStyleAttr, defStyleRes);
        TypedArray appearance = null;
        int ap = a.getResourceId(R.styleable.Themes_LimitedTextView_limitedTextViewStyle, -1);
        a.recycle();
        if (ap != -1) {
            appearance = context.obtainStyledAttributes(ap, R.styleable.LimitedTextView);
            if(appearance != null){
                int n = appearance.getIndexCount();
                for (int i = 0; i < n; i++) {
                    int attr = appearance.getIndex(i);
                    if(attr == R.styleable.LimitedTextView_overflow_len){
                        overFlowLength = appearance.getInt(attr,0);
                    }else if (attr == R.styleable.LimitedTextView_overflow_str){
                        overFlowStr = appearance.getString(attr);
                    }
                }
            }
            appearance.recycle();
        }
    }


    public void setLengthFilter(){
        if(overFlowLength>0){
            if(TextUtils.isEmpty(overFlowStr)){
                overFlowStr = getContext().getString(R.string.overflow_string_default);
            }
            lengthFilter = new LengthInputFilter(overFlowLength,overFlowStr);
            this.setFilters(new InputFilter[]{lengthFilter});
            setText(getText());
        }
    }
}
