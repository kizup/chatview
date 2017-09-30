package ru.kizup.chat.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Round Image view for picture on message
 */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView {

    private Path mClipPath;
    private Paint mBgPaint;

    public RoundImageView(Context context) {
        super(context);
        initClipPath();
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initClipPath();
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initClipPath();
    }

    private void initClipPath() {
        mClipPath = new Path();
        RectF rect = new RectF(0, 0, getWidth(), getHeight());
        float radius = getWidth();
        mClipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initClipPath();
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mBgPaint);
        canvas.clipPath(mClipPath);
        super.onDraw(canvas);
    }
}
