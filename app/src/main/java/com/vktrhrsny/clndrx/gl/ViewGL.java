package com.vktrhrsny.clndrx.gl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.chillingvan.canvasgl.ICanvasGL;
import com.chillingvan.canvasgl.glcanvas.GLPaint;
import com.chillingvan.canvasgl.glview.texture.GLContinuousTextureView;
import com.vktrhrsny.clndrx.util.Conv;

public class ViewGL extends GLContinuousTextureView {

    private StarGL[] stars;
    private float speed;
    private GLPaint paint;
    private int w,h;

        public ViewGL(Context context) {
            super(context);
            initialize();
        }

        public ViewGL(Context context, AttributeSet attrs) {
            super(context, attrs);
            initialize();
        }

    @Override
    protected void init() {
        super.init();
        setLayerType(LAYER_TYPE_HARDWARE,new Paint());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w=w;
        this.h=h;
        for(int i=0;i<stars.length;i++)
            stars[i] = new StarGL((float)w,(float)h);
    }


    private void initialize(){
        stars = new StarGL[100];
        speed = 0.1f;
        paint = new GLPaint();
        paint.setColor(Color.BLACK);
    }

        @Override
        protected void onGLDraw(ICanvasGL canvas) {
            canvas.drawRect(0,0,(float)w,(float)h,paint);
            for(StarGL star:stars){
                star.update(speed);
                star.show(canvas);
            }
        }

    public void setSpeed(float speed){
        this.speed = Conv.map(speed,0,100,0,1);
    }

}


