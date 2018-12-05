package com.example.administrator.androidtestdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class AnimatedPieView extends View {

    protected final String TAG = this.getClass().getSimpleName();

    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private RectF drawBounds;
    private float centerX;
    private float centerY;

    private float touchX = -1;
    private float touchY = -1;

    RectF mDrawRectf = new RectF();

    public AnimatedPieView(Context context) {
        this(context,null);
    }

    public AnimatedPieView(Context context,  @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AnimatedPieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        drawBounds = new RectF();
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(80);
        paint1.setColor(Color.RED);

        paint2 = new Paint(paint1);
        paint2.setColor(Color.GREEN);

        paint3 = new Paint(paint1);
        paint3.setColor(Color.BLUE);
    }

    /**
     * 重绘
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final float width = getWidth() - getPaddingLeft() - getPaddingRight();
        final float height = getHeight() - getPaddingTop() - getPaddingBottom();
//
//        canvas.translate(width / 2, height / 2);
        canvas.translate(centerX, centerY);
        //半径
        final float radius = (float) (Math.min(width, height) / 2 * 0.85);
        mDrawRectf.set(-radius, -radius, radius, radius);

        canvas.drawArc(mDrawRectf, 0, 120, false, paint1);
        canvas.drawArc(mDrawRectf, 120, 120, false, paint2);
        canvas.drawArc(mDrawRectf, 240, 120, false, paint3);
    }

    /**
     * 测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getSize(UIUtil.dip2px(getContext(), 300f), widthMeasureSpec),
                getSize(UIUtil.dip2px(getContext(), 300f), heightMeasureSpec));
    }

    private int getSize(int defaultSize, int measureSpec) {
        int result = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                result = defaultSize;
                break;
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        drawBounds.set(getPaddingLeft(),getPaddingTop(),getWidth()-getPaddingRight(),getHeight()-getPaddingBottom());
        centerX=drawBounds.width()/2;
        centerY=drawBounds.height()/2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
//                PieInfoWrapper touchWrapper = pointToPieInfoWrapper(touchX, touchY);
//                if (touchWrapper == null) return false;
//                handleUp(touchWrapper);

                return true;
        }

        return super.onTouchEvent(event);
    }
}
