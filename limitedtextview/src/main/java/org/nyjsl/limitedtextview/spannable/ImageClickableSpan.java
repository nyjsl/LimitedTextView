package org.nyjsl.limitedtextview.spannable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.style.ImageSpan;

import org.nyjsl.limitedtextview.ClickImpl;
import org.nyjsl.limitedtextview.LimitedTextView;
import org.nyjsl.limitedtextview.interfaces.SpannableInterface;

public class ImageClickableSpan extends ImageSpan implements SpannableInterface {

    public ClickImpl getClickable() {
        return clickable;
    }

    private ClickImpl clickable = null;

    private LimitedTextView ltv = null;

    private int mOverflowDrawableTextColor = 0;
    private int mOverflowDrawableExtraPadding = 0;
    private int mOverflowDrawableTextSize = 0;

    public ImageClickableSpan(int mOverflowDrawableTextColor,int mOverflowDrawableExtraPadding,int mOverflowDrawableTextSize,LimitedTextView ltv,Drawable d, int mode) {
        super(d);
        this.mOverflowDrawableExtraPadding = mOverflowDrawableExtraPadding;
        this.mOverflowDrawableTextColor = mOverflowDrawableTextColor;
        this.mOverflowDrawableTextSize = mOverflowDrawableTextSize;
        this.ltv = ltv;
        clickable = getClickable(0,mode);
    }

    @Override
    public String getSource() {
        return super.getSource();
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int lenBigger = Math.round(ltv.getPaint().measureText(text, start, end));
        final int lineHeightBigger = ltv.getLineHeight();
        getDrawable().setBounds(0, 0, lenBigger+ mOverflowDrawableExtraPadding *2, lineHeightBigger);
        super.draw(canvas, text, start, end, x, top, y, bottom, paint);

        TextPaint smaller = new TextPaint();
        smaller = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        smaller.setColor(mOverflowDrawableTextColor);
        smaller.setTextSize(mOverflowDrawableTextSize);
        final int lenSmaller = Math.round(smaller.measureText(text, start, end));
        final int startOffset = (lenBigger - lenSmaller)/2 + mOverflowDrawableExtraPadding;
        canvas.drawText(text.subSequence(start, end).toString(), x+startOffset, y, smaller);
    }

    @Override
    public ClickImpl getClickable(int status, int mode) {
        return new ClickImpl(status,mode);
    }
}