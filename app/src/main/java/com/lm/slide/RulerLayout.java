package com.lm.slide;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class RulerLayout extends LinearLayout {
    private Context mContext;
    private RulerView mRulerView;//尺子控件
    private Paint mLinePaint;//中心线画笔
    private Paint mBaseLinePaint;//中心线画笔
    private int mLineWidth = 10;//中心线宽度
    private int mLineHeaght = 140;//中心线高度
    private int mLineColor = Color.parseColor("#2C6FE4");
    private int mBaseLineColor = Color.parseColor("#2C6FE4");

    public RulerLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public RulerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mRulerView = new RulerView(mContext);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RulerLayout);
        mLineColor = a.getColor(R.styleable.RulerLayout_lineColor, Color.parseColor("#2C6FE4"));
        mLineHeaght = a.getDimensionPixelSize(R.styleable.RulerLayout_lineHeight, 140);
        mLineWidth = a.getDimensionPixelSize(R.styleable.RulerLayout_lineWidth, 10);
        mBaseLineColor = a.getColor(R.styleable.RulerLayout_baseLineColor, Color.parseColor("#cccccc"));
        int bigHeight = a.getDimensionPixelSize(R.styleable.RulerLayout_bigHeight, 200);
        int smallHeight = a.getDimensionPixelSize(R.styleable.RulerLayout_smallHeight, 130);
        int nomalHeight = a.getDimensionPixelSize(R.styleable.RulerLayout_nomalHeight, 170);
        int bigWidth = a.getDimensionPixelSize(R.styleable.RulerLayout_bigWidth, 6);
        int smallWidth = a.getDimensionPixelSize(R.styleable.RulerLayout_smallWidth, 6);
        int nomalWidth = a.getDimensionPixelSize(R.styleable.RulerLayout_nomalWidth, 6);
        int spacing = a.getDimensionPixelSize(R.styleable.RulerLayout_spacing, 35);
        int txtSize = a.getDimensionPixelSize(R.styleable.RulerLayout_txtSize, 50);
        int bigColor = a.getColor(R.styleable.RulerLayout_bigColor, Color.parseColor("#cccccc"));
        int nomalColor = a.getColor(R.styleable.RulerLayout_nomalColor, Color.parseColor("#cccccc"));
        int smallColor = a.getColor(R.styleable.RulerLayout_smallColor, Color.parseColor("#cccccc"));
        int txtColor = a.getColor(R.styleable.RulerLayout_txtColor, Color.parseColor("#000000"));
        int bgColor = a.getColor(R.styleable.RulerLayout_bgColor, Color.parseColor("#2CE4AA"));
        int startScale = a.getInteger(R.styleable.RulerLayout_startScale, 6);
        int endScale = a.getInteger(R.styleable.RulerLayout_endScale, 500);
        int currentScale = a.getInteger(R.styleable.RulerLayout_currentScale, 10);
        int txtAndLineHeaght = a.getDimensionPixelSize(R.styleable.RulerLayout_txtAndLineHeaght, 100);

        mRulerView.setBigHeaght(bigHeight);
        mRulerView.setBigWidth(bigWidth);
        mRulerView.setBigColor(bigColor);

        mRulerView.setSmallHeaght(smallHeight);
        mRulerView.setSmallWidth(smallWidth);
        mRulerView.setSmallColor(smallColor);

        mRulerView.setNomalHeaght(nomalHeight);
        mRulerView.setNomalWidth(nomalWidth);
        mRulerView.setNomalColor(nomalColor);

        mRulerView.setSpacing(spacing);
        mRulerView.setBgColor(bgColor);
        mRulerView.setTxtColor(txtColor);
        mRulerView.setTxtAndLineHeaght(txtAndLineHeaght);
        mRulerView.setStartScale(startScale);
        mRulerView.setEndScale(endScale);
        mRulerView.setCurrentScale(currentScale);
        mRulerView.setTxtSize(txtSize);

        mRulerView.initPaint();
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRulerView.setLayoutParams(layoutParams);
        addView(mRulerView);
        init();
    }

    private void init() {
        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);


        mBaseLinePaint = new Paint();
        mBaseLinePaint.setStyle(Paint.Style.FILL);
        mBaseLinePaint.setColor(mBaseLineColor);
        mBaseLinePaint.setStrokeWidth(2);
        setWillNotDraw(false);   //设置ViewGroup可绘画
    }


    public void setRulerListener(RulerView.RulerListener rulerListener) {
        mRulerView.setRulerListener(rulerListener);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //画中间的选定光标，要在这里画，因为dispatchDraw()执行在onDraw()后面，这样子光标才能不被尺子的刻度遮蔽
        int x = getWidth() / 2;
        //画中心线

        canvas.drawLine(0, 0, getWidth(), 0, mBaseLinePaint);
        canvas.drawLine(x, 0, x, mLineHeaght, mLinePaint);

    }

}
