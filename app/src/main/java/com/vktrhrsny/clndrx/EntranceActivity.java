package com.vktrhrsny.clndrx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class EntranceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        ImageView imageView = findViewById(R.id.image_view);

        AnimatedVectorDrawableCompat tehen = AnimatedVectorDrawableCompat.create(this,R.drawable.tehen_rablas);
        if (tehen != null)
            tehen.start();

        if (tehen != null) {
            tehen.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                @Override
                public void onAnimationEnd(Drawable drawable) {
                    //super.onAnimationEnd(drawable);
                    tehen.start();
                }
            });
        }
        imageView.setImageDrawable(tehen);
    }

    public void toMain(View view) {
        startActivity(new Intent(EntranceActivity.this,MainActivity.class));
    }
}
