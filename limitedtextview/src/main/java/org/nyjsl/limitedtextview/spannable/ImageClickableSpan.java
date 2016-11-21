package org.nyjsl.limitedtextview.spannable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import org.nyjsl.limitedtextview.ClickImpl;
import org.nyjsl.limitedtextview.interfaces.SpannableInterface;

public class ImageClickableSpan extends ImageSpan implements SpannableInterface {

    public ClickImpl getClickable() {
        return clickable;
    }

    private ClickImpl clickable = null;

    public ImageClickableSpan(Drawable d, int mode) {
        super(d);
        clickable = getClickable(0,mode);
    }

    @Override
    public String getSource() {
        return super.getSource();
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
//            int lenBigger = Math.round(mTextPaint.measureText(text, start, end));
//            final int lineHeightBigger = getLineHeight();
//            getDrawable().setBounds(0, 0, lenBigger+mExpandDrawableExtraPadding*2, lineHeightBigger);
        super.draw(canvas, text, start, end, x, top, y, bottom, paint);

//            TextPaint smaller = new TextPaint();
//            smaller = new TextPaint(Paint.ANTI_ALIAS_FLAG);
//            smaller.setColor(mExpandDrawableTextColor);
//            smaller.setTextSize(mExpandDrawableTextSize);
//            final int lenSmaller = Math.round(smaller.measureText(text, start, end));
//            final int startOffset = (lenBigger - lenSmaller)/2 +mExpandDrawableExtraPadding;
//            canvas.drawText(text.subSequence(start, end).toString(), x+startOffset, y, smaller);
    }

    @Override
    public ClickImpl getClickable(int status, int mode) {
        return new ClickImpl(status,mode);
    }
}