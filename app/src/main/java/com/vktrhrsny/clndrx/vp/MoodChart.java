package com.vktrhrsny.clndrx.vp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.vktrhrsny.clndrx.vp.pojo.MemoryEntity;

import java.util.List;

public class MoodChart extends View {
    float start=0;
    int r;
    int[] data;
    int cx,cy;
    int segments;
    private int[] color;
    Paint paint,innerPaint;
    RectF rect;

    public void init() {

        setFocusable(true);
        segments=6;
        data=new int[]{1,0,0,0,0,0};
        color= new int[]{ Color.BLACK,Color.YELLOW,Color.GREEN,Color.CYAN,Color.MAGENTA,Color.RED};
        paint = new Paint();
        innerPaint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
    }

    public MoodChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        rect = new RectF(0,0,xNew,yNew);
        cx=cy=xNew/2;
        r=xNew/4;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
            float[] scale = measure();
            paint.setColor(Color.BLACK);
            for (int i = 0; i < segments; i++) {
                paint.setColor(color[i]);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawArc(rect, start, scale[i], true, paint);
                start = start + scale[i];
            }
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setColor(Color.BLACK);
        canvas.drawCircle(cx,cy,r,innerPaint);
    }

    private float[] measure() {
        float[] measured = new float[data.length];
        float total = getTotal();
        for (int i = 0; i < this.data.length; i++) {
            measured[i] = (this.data[i] / total) * 360;
        }
        return measured;
    }

    public void setData(List<MemoryEntity> memoryEntities){
        if(!memoryEntities.isEmpty()) {
            data = new int[segments];
            for (int i = 0; i < memoryEntities.size(); i++)
                data[memoryEntities.get(i).getEmotIcon()]+=1;
        }else
            data = new int[]{1,0,0,0,0,0};
        invalidate();
    }

    private float getTotal() {
        float total = 0;
        for (float val : this.data)
            total += val;
        return total;
    }


}
