package com.vktrhrsny.clndrx.gl;

import android.graphics.Color;
import android.graphics.Paint;

import com.chillingvan.canvasgl.ICanvasGL;
import com.chillingvan.canvasgl.glcanvas.GLPaint;
import com.vktrhrsny.clndrx.util.Conv;

import java.util.concurrent.ThreadLocalRandom;

class StarGL {

        private float x;
        private float y;
        private float z;
        private float w,h;
        private float prev_z;

        private GLPaint paint;

        StarGL(float w, float h){
            this.w=w;
            this.h=h;
            x = ThreadLocalRandom.current().nextInt((int)-(w/2),(int)w/2);
            y = ThreadLocalRandom.current().nextInt((int)-h/2,(int)h/2);
            z = ThreadLocalRandom.current().nextInt((int)w);
            prev_z = z;
            paint = new GLPaint();
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
        }

        void update(float speed){
            z = z-speed;

            if(z<1){
                z = w/2;
                x = ThreadLocalRandom.current().nextInt((int)-w/2,(int)w/2);
                y = ThreadLocalRandom.current().nextInt((int)-h/2,(int)h/2);
                prev_z = z;
            }
        }

        void show(ICanvasGL c){
            float spX = Conv.map(x/z,0,1,0,w/2);
            float spY = Conv.map(y/z,0,1,0,h/2);

            float starRadius = Conv.map(z,0,w,5,0);
            c.drawCircle(spX,spY,starRadius,paint);

            float prev_x = Conv.map(x/prev_z,0,1,0,w/2);
            float prev_y = Conv.map(y/prev_z,0,1,0,h/2);

            prev_z = z;

            c.drawLine(prev_x,prev_y,spX,spY,paint);
        }
    }


