package com.example.finalproject.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Dimension;

import com.example.finalproject.R;

public class GraphView extends View {
    private Paint mLinePaint, mTextPaint;

    private float mTextGap;
    private int[] mPoints, mPointX, mPointY;
    private int mUnit, mOrigin, mDivide;

    public GraphView(Context context, AttributeSet attrs) {
        super(context,attrs);
        setTypes(context, attrs);
    }

    //그래프 옵션을 받는다
    private void setTypes(Context context, AttributeSet attrs) {
        TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.GraphView);

        //수치 옵션
        Paint paint = new Paint();
        paint.setColor(types.getColor(R.styleable.GraphView_textColor, Color.BLACK));
        paint.setTextSize(types.getDimension(R.styleable.GraphView_textSize, 0));
        paint.setTextAlign(Paint.Align.CENTER);
        mTextPaint = paint;

        //막대와 수치와의 거리
        mTextGap = types.getDimension(R.styleable.GraphView_textGap, 0);

        //막대 옵션
        paint = new Paint();
        paint.setColor(types.getColor(R.styleable.GraphView_lineColor, Color.BLACK));
        paint.setStrokeWidth(types.getDimension(R.styleable.GraphView_lineThickness, 0));
        mLinePaint = paint;
    }

    //그래프 정보를 받는다
    public void setPoints(int[] points, int unit, int origin, int divide) {
        mPoints = points;   //y축 값 배열

        mUnit = unit;       //y축 단위
        mOrigin = origin;   //y축 원점
        mDivide = divide;   //y축 값 갯수
    }

    //그래프를 만든다
    public void draw() {
        int height = getHeight();
        int width = getWidth();
        int[] points = mPoints;

        //x축 막대 사이의 거리
        //float gapx = (float)getWidth() / points.length;
        float gapy = height/points.length;

        //y축 단위 사이의 거리
        //float gapy = height / mDivide;
        float gapx = (float)width/mDivide;

        //float halfgab = gapy / 2;
        float halfgab = gapx/2;

        int length = points.length;
        mPointX = new int[length];
        mPointY = new int[length];

        for(int i = 0 ; i < length ; i++) {
            //막대 좌표를 구한다
            int y = (int)(halfgab + (i * gapy));
            int x = (int)(((points[i] / mUnit) - mOrigin) * gapx);

            mPointX[i] = x;
            mPointY[i] = y;
        }
    }

    //그래프를 그린다(onCreate 등에서 호출시)
    public void drawForBeforeDrawView() {
        //뷰의 크기를 계산하여 그래프를 그리기 때문에 뷰가 실제로 만들어진 시점에서 함수를 호출해 준다
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                draw();

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(mPointX != null && mPointY != null) {
            int length = mPointX.length;

            int bottom = getWidth();
            Paint grey_back = new Paint();
            grey_back.setColor(Color.parseColor("#55aaaaaa"));
            grey_back.setStrokeWidth(mLinePaint.getStrokeWidth());


            for (int i = 0; i < length; i++) {
                int x = mPointX[i];
                int y = mPointY[i];


                //믹대를 그린다
                if(i==0){
                    Paint paint = new Paint();
                    paint.setColor(Color.parseColor("#3DB7CC"));
                    paint.setStrokeWidth(mLinePaint.getStrokeWidth());
                    canvas.drawLine(0, y, x,y, paint);
                    canvas.drawLine(x,y,getWidth(),y,grey_back);
                }else if(i==1){
                    Paint paint = new Paint();
                    paint.setColor(Color.parseColor("#5CE753"));
                    paint.setStrokeWidth(mLinePaint.getStrokeWidth());
                    canvas.drawLine(0, y, x,y, paint);
                    canvas.drawLine(x,y,getWidth(),y,grey_back);
                }else if(i==2){
                    Paint paint = new Paint();
                    paint.setColor(Color.parseColor("#E73836"));
                    paint.setStrokeWidth(mLinePaint.getStrokeWidth());
                    canvas.drawLine(0, y, x,y, paint);
                    canvas.drawLine(x,y,getWidth(),y,grey_back);
                }else{
                    canvas.drawLine(0, y, x,y, mLinePaint);
                }


                //믹대 위 수치를 쓴다
                canvas.drawText("" + mPoints[i], x-mTextGap ,y+16, mTextPaint);
            }
        }
    }
}
