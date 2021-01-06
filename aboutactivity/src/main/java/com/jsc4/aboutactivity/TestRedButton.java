package com.jsc4.aboutactivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * 做一个圆形的红色按钮，中间有一个白色数字，数字起始20，每点击一次减少1
 */
public class TestRedButton extends View implements View.OnClickListener {

    private Paint mPaint;
    private Rect mRect;
    private int mNumber = 20;
    private int mBackgroundColor;
    private int mTextSize;

    public TestRedButton(Context context) {
        this(context, null);
    }

    public TestRedButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestRedButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TestRedButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * init the view
     */
    private void init(Context context, @Nullable AttributeSet attrs) {
        mPaint = new Paint();// 不可以在onDraw中new对象， 因为onDraw会被频繁调用
        mRect = new Rect();
        this.setOnClickListener(this);
        // 获取自定义属性的所有属性所在的数组
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestRedButton);
        // 获取自定义属性，并设置默认值为红色
        mBackgroundColor = typedArray.getColor(R.styleable.TestRedButton_backgroundColor, Color.RED);
        mTextSize = typedArray.getInteger(R.styleable.TestRedButton_textSize, 18);//18个像素
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 做一个圆形的红色按钮
        // 设置画布为红色
        mPaint.setColor(mBackgroundColor);

        canvas.drawCircle(getWidth()/2,getWidth()/2, getWidth()/2,mPaint);

        // 中间的白色数字
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(mTextSize);

        String text = String.valueOf(mNumber);
        mPaint.getTextBounds(text, 0, text.length(), mRect);

        int textWidth = mRect.width();
        int textHeight = mRect.height();

        canvas.drawText(text, getWidth()/2 - textWidth/2, getHeight()/2 + textHeight/2, mPaint);


    }

    @Override
    public void onClick(View v) {
        // 每点击一次减少1
        if(mNumber > 0) {
            mNumber--;
        }else if(mNumber == 0){
            mNumber = 20;
        }

        // 刷新视图
        invalidate();
    }
}
