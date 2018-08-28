package com.example.administrator.diycode.audioView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.androidtestdemo.R;

import java.util.Random;

public class VisualFrequencyView extends View {
    private int[] list = new int[12];
    private static final int WIDTH_COUNT = 12;
    private static final int HEIGHT_COUNT = 12;
    private static final int ACTION_UPDATE_VIEW = 100;
    //蓝色的竖条
    private Bitmap blueBlock;
    //灰色的竖条
    private Bitmap grayBolck;
    //竖条的宽高
    private int blockWidth;
    private int blockHeight;
    private boolean isUpdating = false;
    private Random random = new Random();

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (isUpdating) {
                updateView(getRandomArray());
                handler.sendEmptyMessageDelayed(ACTION_UPDATE_VIEW, 60);
            }
        };
    };
    public VisualFrequencyView(Context context) {
        super(context);
        init();
    }

    public VisualFrequencyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VisualFrequencyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化资源
     */
    private void init() {
        for (int i = 0; i < 12; i++) {
            list[i] = HEIGHT_COUNT;
        }
        blueBlock = getBitmap(getResources().getDrawable(R.drawable.plugin_audio_blue_block_shape));
        grayBolck = getBitmap(getResources().getDrawable(R.drawable.plugin_audio_gray_block_shape));
        blockHeight = blueBlock.getHeight();
        blockWidth = blueBlock.getWidth();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final Bitmap localGray = grayBolck;
        final Bitmap localBlue = blueBlock;
        //Bitmap的宽高
        final int w = blockWidth;
        final int h = blockHeight;

        final int[] localList = list;
        for (int i = 0; i < WIDTH_COUNT; i++) {
            final int itemValue = localList[i];
            for (int m = 0; m < HEIGHT_COUNT; m++) {
                if (m >= itemValue) {
                    canvas.drawBitmap(localBlue, i * w, m * h, null);
                } else {
                    canvas.drawBitmap(localGray, i * w, m * h, null);
                }
            }
        }
    }



    private void updateView(int[] randoms) {
        if (randoms == null || randoms.length != WIDTH_COUNT) {
            return;
        }
        for (int i = 0; i < WIDTH_COUNT; i++) {
            final int itemHeight = randoms[i];
            if (itemHeight > list[i]) {
                list[i] += 2;
            } else if (itemHeight < list[i]) {
                list[i] -= 2;
            }
        }
        invalidate();

    }

    public void startResponse() {
        if (!isUpdating) {
            handler.sendEmptyMessage(ACTION_UPDATE_VIEW);
            isUpdating = true;
        }
    }

    public void stopResponse() {
        handler.removeMessages(ACTION_UPDATE_VIEW);
        isUpdating = false;
        for (int i = 0; i < 12; i++) {
            list[i] = HEIGHT_COUNT;
        }
        invalidate();
    }

    public boolean isUpdatingView() {
        return isUpdating;
    }

    /**
     * 将Drawable转成Bitmap
     * @param drawable
     * @return
     */
    private Bitmap getBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    /**
     * 赋值数组元素随机数
     * @return
     */
    private int[] getRandomArray() {
        int[] array = new int[WIDTH_COUNT];
        for (int i = 0, size = array.length; i < size; i++) {
            array[i] = random.nextInt(HEIGHT_COUNT);
        }
        return array;
    }
}
