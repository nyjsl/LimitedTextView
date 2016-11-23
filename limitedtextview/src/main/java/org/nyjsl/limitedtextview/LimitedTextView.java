package org.nyjsl.limitedtextview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.DynamicLayout;
import android.text.InputFilter;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import org.nyjsl.limitedtextview.interfaces.Expandable;
import org.nyjsl.limitedtextview.interfaces.SpannableInterface;
import org.nyjsl.limitedtextview.interfaces.Toggable;
import org.nyjsl.limitedtextview.linkmovmentmethod.ImageTouchMovementMethod;
import org.nyjsl.limitedtextview.linkmovmentmethod.TextTouchLinkMovementMethod;
import org.nyjsl.limitedtextview.spannable.ImageClickableSpan;
import org.nyjsl.limitedtextview.spannable.TextClickableSpan;

/**
 * Created by pc on 2016/11/17.
 */

public class LimitedTextView extends TextView implements Expandable,Toggable {


    public static final int LIMIT_MODE_NO = 0;
    public static final int LIMIT_MODE_YES = 1;

    private static final int SHRINK_EXPAND_NONE = 0;
    private static final int SHRINK_EXPAND_EXPAND_ONLY = 1;
    private static final int SHRINK_EXPAND_BOTH = 2;

    private BufferType mBufferType = BufferType.NORMAL;
    private TextPaint mTextPaint = null;
    private Layout mLayout = null;
    private int mTextLineCount = -1;
    private int mLayoutWidth = 0;
    private int mFutureTextViewWidth = 0;


    //  the original text of this view
    private CharSequence mOrigText;

    private String mEllipsisHint = "";
    private String mToExpandHint = "";
    private String mToShrinkHint = "";
    private int mMaxLinesOnShrink = 0;
    /**
     * 默认展开和文本之间增加一个间距
     */
    private String mGapText = " ";

    private String overFlowStr = "";
    private int overFlowLength = 0;
    private LengthInputFilter lengthFilter;

    private int mToExpandHintColor = 0xFF3498DB;
    private int mToShrinkHintColor = 0xFFE74C3C;
    private int mToExpandHintColorBgPressed = 0x55999999;
    private int mToShrinkHintColorBgPressed = 0x55999999;

    private int mOverflowMode = OVERFLOW_MODE_TEXT;

    private static final int OVERFLOW_MODE_TEXT = 0;
    private static final int OVERFLOW_MODE_DRAWABLE = 1;

    private Drawable mExpandDrwable = null;

    private int mOverflowDrawableTextSize = DEFAULT_OVERFLOWRAWABLTEXTSIZE;
    /**
     * 默认展开文本的大小
     */
    private static final int DEFAULT_OVERFLOWRAWABLTEXTSIZE = 13;

    private int mOverflowDrawableTextColor = DEFAULT_OVERFLOWDRAWABLTEXTCOLOR;
    /**
     * 默认展开文本的颜色
     */
    private static final int DEFAULT_OVERFLOWDRAWABLTEXTCOLOR = 0;

    private int mOverflowDrawableExtraPadding = DEFAULT_OVERFLOWDRAWABLEXTRAPADDING;
    /**
     * 默认文本到边框的左右边距
     */
    private static final int DEFAULT_OVERFLOWDRAWABLEXTRAPADDING = 5;


    public int getmCurrState() {
        return mCurrState;
    }

    private int mCurrState = STATE_SHRINK;

    public void setmCurrState(int mCurrState) {
        this.mCurrState = mCurrState;
    }

    private int mLimitMode = 0;
    private int mShrinkExpandMode = 0;
    private SpannableInterface spannable = null;

    public Expandable getExpandable() {
        return expandable;
    }

    private Expandable expandable = null;

    private LinkMovementMethod linkMovementMethod = null;

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
        setLengthFilter(mLimitMode);

        if(mOverflowMode == OVERFLOW_MODE_TEXT){
            spannable = new TextClickableSpan(mShrinkExpandMode,mToExpandHintColor,mToShrinkHintColor,mToExpandHintColorBgPressed,mToShrinkHintColorBgPressed);
            linkMovementMethod = new TextTouchLinkMovementMethod();
        }else if (mOverflowMode == OVERFLOW_MODE_DRAWABLE){
            spannable = new ImageClickableSpan(mOverflowDrawableTextColor,mOverflowDrawableExtraPadding,mOverflowDrawableTextSize,mExpandDrwable,mShrinkExpandMode);
            linkMovementMethod = new ImageTouchMovementMethod();
        }
        setMovementMethod(linkMovementMethod);


        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = getViewTreeObserver();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    obs.removeOnGlobalLayoutListener(this);
                } else {
                    obs.removeGlobalOnLayoutListener(this);
                }
                setTextInternal(getShirkOrExpandTextByConfig(), mBufferType);
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LimitedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }
    private void readAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a;
        a = context.obtainStyledAttributes(attrs, R.styleable.LimitedTextView, defStyleAttr, defStyleRes);
        int n = a.getIndexCount();
        getAttrsByType(a, n);
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
                getAttrsByType(appearance, n);
            }
            appearance.recycle();
        }
    }

    private void getAttrsByType(TypedArray a, int n) {
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if(attr == R.styleable.LimitedTextView_overflow_len){
                overFlowLength = a.getInt(attr,0);
            }else if (attr == R.styleable.LimitedTextView_overflow_str){
                overFlowStr = a.getString(attr);
            }else if (attr == R.styleable.LimitedTextView_ellipsis_hint_str){
                mEllipsisHint = a.getString(attr);
            }else if (attr == R.styleable.LimitedTextView_toexpandhint_str){
                mToExpandHint = a.getString(attr);
            }else if (attr == R.styleable.LimitedTextView_toshrinkhint_str){
                mToShrinkHint = a.getString(attr);
            }else if (attr == R.styleable.LimitedTextView_maxlinesonshrink){
                mMaxLinesOnShrink = a.getInt(attr,0);
            }else if (attr == R.styleable.LimitedTextView_limited_mode){
                mLimitMode = a.getInt(attr,LIMIT_MODE_NO);
            }else if (attr == R.styleable.LimitedTextView_shrink_expand_mode){
                mShrinkExpandMode = a.getInt(attr,SHRINK_EXPAND_NONE);
            }else if (attr == R.styleable.LimitedTextView_toExpandHintColor){
                mToExpandHintColor = a.getColor(attr,0xFF3498DB);
            }else if (attr == R.styleable.LimitedTextView_toShrinkHintColor){
                mToShrinkHintColor = a.getColor(attr,0xFFE74C3C);
            }else if (attr == R.styleable.LimitedTextView_toExpandHintColorBgPressed){
                mToExpandHintColorBgPressed = a.getColor(attr,0x55999999);
            }else if (attr == R.styleable.LimitedTextView_toShrinkHintColorBgPressed){
                mToShrinkHintColorBgPressed = a.getColor(attr,0x55999999);
            }else if(attr == R.styleable.LimitedTextView_overflow_mode){
                mOverflowMode = a.getInt(attr,0);
            }else if (attr == R.styleable.LimitedTextView_overflow_drawable){
                mExpandDrwable = a.getDrawable(attr);
            }else if (attr == R.styleable.LimitedTextView_overflow_drawable_textSize){
                mOverflowDrawableTextSize = a.getDimensionPixelSize(attr, DEFAULT_OVERFLOWRAWABLTEXTSIZE);
            }else if (attr == R.styleable.LimitedTextView_overflow_drawable_textColor){
                mOverflowDrawableTextColor = a.getColor(attr, DEFAULT_OVERFLOWDRAWABLTEXTCOLOR);
            }else if (attr == R.styleable.LimitedTextView_overflow_drawable_extrapadding){
                mOverflowDrawableExtraPadding = a.getDimensionPixelSize(attr, DEFAULT_OVERFLOWDRAWABLEXTRAPADDING);
            }
        }

        if(null == mExpandDrwable){
            mExpandDrwable = ContextCompat.getDrawable(getContext(),R.drawable.default_expand_drawable);
        }
        mExpandDrwable.setBounds(0, 0, mExpandDrwable.getIntrinsicWidth(), mExpandDrwable.getIntrinsicHeight());
    }

    /**
     *设置是否限制长度
     * @param mode LIMIT_MODE_YES  LIMIT_MODE_NO
     */
    public void setLengthFilter(int mode){
        mLimitMode = mode;
        if(mLimitMode == LIMIT_MODE_YES){
            if(overFlowLength>0){
                if(TextUtils.isEmpty(overFlowStr)){
                    overFlowStr = getContext().getString(R.string.overflow_string_default);
                }
                lengthFilter = new LengthInputFilter(overFlowLength,overFlowStr);
                this.setFilters(new InputFilter[]{lengthFilter});

            }
        }else if (mLimitMode == LIMIT_MODE_NO){
            this.setFilters(new InputFilter[0]);
        }else{
            return;
        }
        setText(mOrigText);
    }


    public void setExpandListener(Expandable expandable){
        this.expandable = expandable;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        mOrigText = text;
        mBufferType = type;
        setTextInternal(getShirkOrExpandTextByConfig(),type);
    }

    public void updateForRecyclerView(CharSequence text, int futureTextViewWidth, int expandState){
        mFutureTextViewWidth = futureTextViewWidth;
        mCurrState = expandState;
        spannable.setClickableSteate(mCurrState);
        setText(text);
    }

    private void setTextInternal(CharSequence text,BufferType type) {
        super.setText(text, type);
    }

    private CharSequence getShirkOrExpandTextByConfig(){
        if(mShrinkExpandMode == SHRINK_EXPAND_NONE){
            return mOrigText;
        }else if(mShrinkExpandMode == SHRINK_EXPAND_BOTH){
            return getNewTextByConfig();
        }else if(mShrinkExpandMode == SHRINK_EXPAND_EXPAND_ONLY){
            return getNewTextByConfig();
        }else{
            return mOrigText;
        }
    }

    private CharSequence getNewTextByConfig(){
        if(TextUtils.isEmpty(mOrigText)){
            return mOrigText;
        }

        if(mShrinkExpandMode == SHRINK_EXPAND_NONE){
            return mOrigText;
        }

        mLayout = getLayout();
        if(mLayout != null){
            mLayoutWidth = mLayout.getWidth();
        }

        if(mLayoutWidth <= 0){
            if(getWidth() == 0) {
                if (mFutureTextViewWidth == 0) {
                    return mOrigText;
                } else {
                    mLayoutWidth = mFutureTextViewWidth - getPaddingLeft() - getPaddingRight();
                }
            }else{
                mLayoutWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            }
        }

        mTextPaint = getPaint();

        mTextLineCount = -1;
        switch (mCurrState){
            case STATE_SHRINK: {
                mLayout = new DynamicLayout(mOrigText, mTextPaint, mLayoutWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                mTextLineCount = mLayout.getLineCount();

                if (mTextLineCount <= mMaxLinesOnShrink) {
                    return mOrigText;
                }
                Layout layout = getValidLayout();
                int start = layout.getLineStart(mMaxLinesOnShrink - 1);
                int end = layout.getLineEnd(mMaxLinesOnShrink - 1) - start;
                CharSequence content = mOrigText.subSequence(start, mOrigText.length());
                float moreWidth = mTextPaint.measureText(getContentOfString(mEllipsisHint) + getContentOfString(mToExpandHint) + getLengthOfString(mGapText)) ;
                float maxWidth = layout.getWidth() - moreWidth;
                int len = getPaint().breakText(content, 0, content.length(), true, maxWidth, null);
                if (content.charAt(end - 1) == '\n') {
                    end = end - 1;
                }
                len = Math.min(len, end);
                String fixText = mOrigText.subSequence(0, start+len).toString();
                SpannableStringBuilder ssbShrink = new SpannableStringBuilder(fixText).append(mEllipsisHint);
                ssbShrink.append(getContentOfString(mGapText)+getContentOfString(mToExpandHint));
                ssbShrink.setSpan(spannable, ssbShrink.length() - getLengthOfString(mToExpandHint), ssbShrink.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return ssbShrink;
            }
            case STATE_EXPAND: {
                if(mShrinkExpandMode == SHRINK_EXPAND_EXPAND_ONLY){
                    return mOrigText;
                }
                mLayout = new DynamicLayout(mOrigText, mTextPaint, mLayoutWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                mTextLineCount = mLayout.getLineCount();
                if (mTextLineCount <= mMaxLinesOnShrink) {
                    return mOrigText;
                }
                SpannableStringBuilder ssbExpand = new SpannableStringBuilder(mOrigText).append(mGapText).append(mToShrinkHint);
                ssbExpand.setSpan(spannable, ssbExpand.length() - getLengthOfString(mToShrinkHint), ssbExpand.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return ssbExpand;
            }
        }
        return mOrigText;
    }

    private Layout getValidLayout(){
        return mLayout != null ? mLayout : getLayout();
    }


    private int getLengthOfString(String string){
        if(string == null)
            return 0;
        return string.length();
    }

    private String getContentOfString(String string){
        if(string == null)
            return "";
        return string;
    }

    @Override
    public void onExpand(View view) {
        if(null != expandable){
            expandable.onExpand(view);
        }
    }

    @Override
    public void onShrink(View view) {
        if(null != expandable){
            expandable.onShrink(view);
        }
    }

    @Override
    public void toggle() {
        setTextInternal(getShirkOrExpandTextByConfig(), mBufferType);
    }
}
