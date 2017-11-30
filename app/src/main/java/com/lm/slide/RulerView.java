package com.lm.slide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class RulerView extends View {
    private Context mContext;

    private Paint mSmallPaint;
    private Paint mBigPaint;
    private Paint mNomalPaint;
    private Paint mTextPaint;
    private Paint mPaint;

    private float mSmallWidth = 6;
    private float mNomalWidth = 6;
    private float mBigWidth = 6;
    private int mSmallColor = Color.parseColor("#cccccc");
    private int mNomalColor = Color.parseColor("#cccccc");
    private int mBigColor = Color.parseColor("#cccccc");
    private int mTxtColor = Color.parseColor("#000000");
    private int mBgColor = Color.parseColor("#2CE4AA");
    private float mSmallHeaght = 130;
    private float mBigHeaght = 200;
    private float mNomalHeaght = 170;
    private int mSpacing = 35;
    private int mTxtSize = 50;
    private int mStartScale = 6;
    private int mEndScale = 500;
    private int mMinimumVelocity, mMaximumVelocity, mMaxScaledTouchSlop;
    private int mMaxLenth = 0;//最大滑动X
    private int mLenth = 0;//宽度
    private int mTxtAndLineHeaght = 100;//字和线的间距
    private Scroller mScroller;//处理滑动

    private RulerListener mRulerListener;//刻度监听

    private float mCurrentScale = 0;//当前滑动刻度

    public void setRulerListener(RulerListener mRulerListener) {
        this.mRulerListener = mRulerListener;
    }

    public RulerView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(MeasureSpec.makeMeasureSpec(widthSize, widthMode), MeasureSpec.makeMeasureSpec(heightSize, heightMode));
    }

    private void init() {
        mScroller = new Scroller(mContext);

        final ViewConfiguration configuration = ViewConfiguration.get(mContext);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mMaxScaledTouchSlop = configuration.getScaledTouchSlop();//获得能够进行手势滑动的距离



        //第一次进入，跳转到设定刻度
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                goToScale(mCurrentScale);
            }


        });

    }

    /**
     * 初始化画笔
     */
    public void initPaint()
    {
        mSmallPaint = new Paint();
        mSmallPaint.setColor(mSmallColor);
        mSmallPaint.setStrokeWidth(mSmallWidth);
        mSmallPaint.setStrokeCap(Paint.Cap.ROUND);

        mNomalPaint = new Paint();
        mNomalPaint.setColor(mNomalColor);
        mNomalPaint.setStrokeWidth(mNomalWidth);
        mNomalPaint.setStrokeCap(Paint.Cap.ROUND);


        mBigPaint = new Paint();
        mBigPaint.setColor(mBigColor);
        mBigPaint.setStrokeWidth(mBigWidth);
        mBigPaint.setStrokeCap(Paint.Cap.ROUND);


        mTextPaint = new Paint();
        mTextPaint.setColor(mTxtColor);
        mTextPaint.setTextSize(mTxtSize);
        mTextPaint.setAntiAlias(true);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBgColor);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mMaxLenth = (int) calculationPoint(mEndScale) - (getWidth() / 2);
        mLenth = (int) calculationPoint(mEndScale);
    }

    /**
     * 计算位置
     *
     * @return
     */
    private float calculationPoint(int i) {
        int position = i - mStartScale;//所处位置
        Log.e("msg", "position=" + position);

        int num = (i / 5) - (mStartScale / 5);
        int big_num = i % 5 == 0 ? num : num + 1;//算大的
        big_num = big_num < 0 ? 0 : big_num;

        Log.e("msg", "big_num=" + big_num);

        int small_num = position - big_num < 0 ? 0 : position - big_num;
        Log.e("msg", "small_num=" + small_num);

        float curLocation = getWidth() / 2 + (position * mSpacing) + (big_num * mBigWidth) + (small_num * mSmallWidth);
        Log.e("msg", "curLocation=" + curLocation);
        return curLocation;
    }

    //把滑动位置scrollX转化为刻度Scale
    private float scrollXtoScale(int scrollX) {
        int only_x = scrollX - getWidth() / 2 + getWidth() / 2;
        return ((only_x + mSmallWidth) / (mSpacing + mSmallWidth)) + mStartScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画背景
        canvas.drawRect(0, 0, mLenth + getWidth() / 2, mBigHeaght + 140, mPaint);

        //画刻度
        for (int i = mStartScale; i <= mEndScale; i++) {
            float curLocation = calculationPoint(i);

            if (i % 5 == 0) {//长的

                if (i % 10 == 0) {//整10的
                    canvas.drawLine(curLocation, 0, curLocation, mBigHeaght, mBigPaint);
                    float textPaintWidth = mTextPaint.measureText(String.valueOf((float) i)) / 2;
                    canvas.drawText(String.valueOf((float) i), curLocation - textPaintWidth, mBigHeaght + mTxtAndLineHeaght, mTextPaint);
                } else {
                    canvas.drawLine(curLocation, 0, curLocation, mNomalHeaght, mNomalPaint);
                }

            } else {//短的
                canvas.drawLine(curLocation, 0, curLocation, mSmallHeaght, mSmallPaint);
            }

        }

    }

    //记录落点
    private float mLastX = 0;
    private float mLastY = 0;
    private VelocityTracker mVelocityTracker;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = currentX;
                mLastY = currentY;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:

                scrollBy((int) (mLastX - currentX), 0);
                mLastX = currentX;
                mLastY = currentY;
                break;
            case MotionEvent.ACTION_UP:
                // 在 ACTION_UP 事件中计算 velocityX 和 velocityY
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);  // 第一个参数是单位，1表示 "像素/ms"  1000表示 "像素/s"  第二个参数是最大值。耗时较大，需要时才调用.
                int velocityX = (int) mVelocityTracker.getXVelocity(); // 获取速度
                Log.e("ssg", "initialVelocity==" + velocityX);
                if (Math.abs(velocityX) > mMinimumVelocity) {
                    // 和系统预设的最小值比较
                    fling(-velocityX);
                } else {
                    scrollBackToCurrentScale();
                }

                releaseVelocityTracker();
                break;
            case MotionEvent.ACTION_CANCEL:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                releaseVelocityTracker();
                break;
        }
        return true;
    }


    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    public void fling(int velocityx) {
        mScroller.fling(getScrollX(), 0, velocityx, 0, 0, mMaxLenth, 0, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //这是最后OverScroller的最后一次滑动，如果这次滑动完了mCurrentScale不是整数，则把尺子移动到最近的整数位置
            if (!mScroller.computeScrollOffset() && mCurrentScale != Math.round(mCurrentScale)) {
                //Fling完进行一次检测回滚
                scrollBackToCurrentScale();
            }
            invalidate();
        }
    }


    //把移动后光标对准距离最近的刻度，就是回弹到最近刻度
    private void scrollBackToCurrentScale() {
        //渐变回弹
        mCurrentScale = Math.round(mCurrentScale);
        int scrollX = getScrollX();

        Log.e("mm", "scrollX:" + scrollX);
        int to_scrollX = (int) (calculationPoint((int) mCurrentScale) - (getWidth() / 2));

        Log.e("mm", "to_scrollX:" + to_scrollX);

      /*  mScroller.startScroll(getScrollX(), 0, to_scrollX, 0, 1000);//滑动到指定位置
        invalidate();*/
        // ObjectAnimator.ofFloat(this, "translationX", 0, to_scrollX - scrollX).setDuration(1000).start();

        //立刻回弹
        scrollTo((int) calculationPoint((int) mCurrentScale) - getWidth() / 2, 0);
    }


    //重写滑动方法，设置到边界的时候不滑。滑动完输出刻度
    @Override
    public void scrollTo(@Px int x, @Px int y) {
        if (x < 0) {
            x = 0;
        } else if (x > mLenth - getWidth() / 2) {
            x = mLenth - getWidth() / 2;
        }


        float scale = scrollXtoScale(x);
        mCurrentScale = scale;
        Log.e("刻度", "Scale:" + scale);

        if (mRulerListener != null) {
            mRulerListener.onScaleChange(Math.round(scale));
        }

        if (x != getScrollX()) {
            super.scrollTo(x, y);
        }
    }

    /**
     * h滑动到刻度位置
     *
     * @param currentScale
     */
    private void goToScale(float currentScale) {
        scrollTo((int) calculationPoint((int) currentScale) - getWidth() / 2, 0);
    }

    public interface RulerListener {
        void onScaleChange(float scale);
    }

    public void setSmallWidth(float mSmallWidth) {
        this.mSmallWidth = mSmallWidth;
    }

    public void setBigWidth(float mBigWidth) {
        this.mBigWidth = mBigWidth;
    }

    public void setSmallColor(int mSmallColor) {
        this.mSmallColor = mSmallColor;
    }

    public void setBigColor(int mBigColor) {
        this.mBigColor = mBigColor;
    }

    public void setTxtColor(int mTxtColor) {
        this.mTxtColor = mTxtColor;
    }

    public void setBgColor(int mBgColor) {
        this.mBgColor = mBgColor;
    }

    public void setSmallHeaght(float mSmallHeaght) {
        this.mSmallHeaght = mSmallHeaght;
    }

    public void setBigHeaght(float mBigHeaght) {
        this.mBigHeaght = mBigHeaght;
    }

    public void setNomalHeaght(float mNomalHeaght) {
        this.mNomalHeaght = mNomalHeaght;
    }

    public void setSpacing(int mSpacing) {
        this.mSpacing = mSpacing;
    }

    public void setStartScale(int mStartScale) {
        this.mStartScale = mStartScale;
    }

    public void setEndScale(int mEndScale) {
        this.mEndScale = mEndScale;
    }

    public void setCurrentScale(float mCurrentScale) {
        this.mCurrentScale = mCurrentScale;

    }

    public void setTxtAndLineHeaght(int mTxtAndLineHeaght) {
        this.mTxtAndLineHeaght = mTxtAndLineHeaght;
    }

    public void setNomalWidth(float mNomalWidth) {
        this.mNomalWidth = mNomalWidth;
    }

    public void setNomalColor(int mNomalColor) {
        this.mNomalColor = mNomalColor;
    }

    public void setTxtSize(int mTxtSize) {
        this.mTxtSize = mTxtSize;
    }
}
