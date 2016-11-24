package org.nyjsl.limitedtextview.spannable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.style.ImageSpan;

import org.nyjsl.limitedtextview.ClickImpl;
import org.nyjsl.limitedtextview.interfaces.SpannableInterface;

public class ImageClickableSpan extends ImageSpan implements SpannableInterface {

    public ClickImpl getClickable() {
        return clickable;
    }

    private ClickImpl clickable = null;

    private int mOverflowDrawableTextColor = 0;
    private int mOverflowDrawableExtraPadding = 0;
    private int mOverflowDrawableTextSize = 0;

    public ImageClickableSpan(int mOverflowDrawableTextColor,int mOverflowDrawableExtraPadding,int mOverflowDrawableTextSize,Drawable d, int mode) {
        super(d);
        this.mOverflowDrawableExtraPadding = mOverflowDrawableExtraPadding;
        this.mOverflowDrawableTextColor = mOverflowDrawableTextColor;
        this.mOverflowDrawableTextSize = mOverflowDrawableTextSize;
        clickable = getClickable(mode);
    }

    @Override
    public String getSource() {
        return super.getSource();
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        float bgX = x - (mOverflowDrawableExtraPadding * 0.5F); //使得在水平方向居中
        int width =  getWidth(text, start, end, paint);
        int hight = getHeight(paint);
        getDrawable().setBounds(0, 0, width, hight);
        super.draw(canvas, text, start, end, bgX, top, y, bottom, paint);
        TextPaint smaller = new TextPaint();
        smaller = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        smaller.setColor(mOverflowDrawableTextColor);
        smaller.setTextSize(mOverflowDrawableTextSize);
        final int lenSmaller = Math.round(smaller.measureText(text, start, end));
        final int xOffset = (int) ((width - lenSmaller)/2 - mOverflowDrawableExtraPadding * 0.5F);
        final int heightSmaller = getHeight(smaller);
        final int yOffSet = (hight - heightSmaller)/8;
        //need temp here otherwise infinit loop
        final String textTemp = text.toString();
        canvas.drawText(textTemp.subSequence(start, end).toString(),x+xOffset, y-yOffSet, smaller);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return getWidth(text, start, end, paint);
    }


    /**
     * 计算span的宽度
     * @param text
     * @param start
     * @param end
     * @param paint
     * @return
     */
    private int getWidth(CharSequence text, int start, int end, Paint paint) {
        return Math.round(paint.measureText(text, start, end)) + mOverflowDrawableExtraPadding*2;
    }

    /**
     * 计算span的高度
     * @param paint
     * @return
     */
    private int getHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }



    @Override
    public ClickImpl getClickable(int mode) {
        return null == clickable? new ClickImpl(STATE_SHRINK,mode) : clickable;
    }

    @Override
    public void setClickableSteate(int state) {
        if(null != clickable){
            clickable.setmCurrState(state);
        }
    }
}