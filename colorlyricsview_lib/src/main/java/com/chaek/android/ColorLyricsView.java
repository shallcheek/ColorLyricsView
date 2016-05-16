package com.chaek.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.chaek.colorlyricsview.R;

public class ColorLyricsView extends View {

    public final static int TOP = 0x0002;
    public final static int LEFT = 0x0001;
    public final static int RIGHT = 0x0003;
    public final static int BOTTOM = 0x0004;
    private int mDirection = LEFT;
    private int mProgress = 0;
    private int mMaxProgress = 100;
    private int textWidth;
    private int textHeight;
    private int startX;
    private int centerY;
    private int centerX;
    private int textCenterY = 47;
    private int textSize = 24;
    private String text = "12";

    private PorterDuffXfermode porterDuffXfermode;
    private Paint textPaint;
    private RectF rectFront;

    private int textColor = 0xff333333;
    private int progressColor = 0xfffe871a;


    public ColorLyricsView(Context context) {
        super(context);
        init();
    }

    public ColorLyricsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ColorLyricsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColorLyricsView);
        textColor = a.getColor(R.styleable.ColorLyricsView_txtColor, textColor);
        progressColor = a.getColor(R.styleable.ColorLyricsView_progressColor, progressColor);
        textSize = a.getDimensionPixelSize(R.styleable.ColorLyricsView_txtSize, textSize);
        mProgress = a.getInteger(R.styleable.ColorLyricsView_progress, 0);
        mMaxProgress = a.getInteger(R.styleable.ColorLyricsView_maxProgress, 100);
        mDirection = a.getInt(R.styleable.ColorLyricsView_type, 0);
        a.recycle();
        init();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        if (null != text) {
            //get  text   width
            textWidth = (int) textPaint.measureText(text);
        }
        textHeight = textSize;
        startX = getWidth() / 2 - textWidth / 2;
        centerY = (bottom - top) / 2;
        rectFront.left = startX;
        rectFront.top = centerY - textHeight / 2;
        rectFront.right = startX + textWidth;
        rectFront.bottom = centerY + textHeight / 2;

        centerX = (right - left) / 2;

        textCenterY = (centerY * 2 - fontMetrics.bottom - fontMetrics.top) / 2;
        super.onLayout(changed, left, top, right, bottom);
    }

    private void setDirection(int direction) {
        switch (direction) {
            case TOP://top->bottom
                rectFront.bottom = (centerY - textHeight / 2) + ((float) textHeight / (float) mMaxProgress) * mProgress;
                break;
            case BOTTOM://bottom->top
                rectFront.top = (centerY + textHeight / 2) - ((float) textHeight / (float) mMaxProgress) * mProgress;
                break;
            case LEFT://left->right
                rectFront.right = startX + ((float) (textWidth) / mMaxProgress) * mProgress;
                break;
            case RIGHT://left->right
                rectFront.left = startX + (textWidth - ((float) (textWidth) / mMaxProgress) * mProgress);
                break;
        }
    }


    public void setProgress(int mProgress) {
        this.mProgress = mProgress;
        invalidate();
    }

    public void setDirectionType(int direction) {
        this.mDirection = direction;
        requestLayout();
        invalidate();
    }

    public void setText(String text) {
        this.text = text;
        requestLayout();
        invalidate();
    }

    private void init() {
        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        porterDuffXfermode = new PorterDuffXfermode(Mode.SRC_ATOP);

        rectFront = new RectF(startX, centerY - textHeight / 2, startX + textWidth, centerY + textHeight / 2);
        invalidate();
    }


    public void setTextSize(int textSize) {
        this.textSize = spToPx(textSize);
        if (textPaint != null) {
            textPaint.setTextSize(textSize);
        }
        requestLayout();
        invalidate();
    }

    private int spToPx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sp, getContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //save
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        setDirection(mDirection);

        textPaint.setColor(textColor);
        if (null != text)
            canvas.drawText(text, centerX, textCenterY, textPaint);

        textPaint.setXfermode(porterDuffXfermode);
        textPaint.setColor(progressColor);
        canvas.drawRect(rectFront, textPaint);

        // clear Xfermode
        textPaint.setXfermode(null);
        // restore
        canvas.restoreToCount(sc);
    }


    public void setTextColor(int color) {
        this.textColor = getContext().getResources().getColor(color);
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = getContext().getResources().getColor(progressColor);
        invalidate();
    }


}