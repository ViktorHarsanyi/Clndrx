package com.vktrhrsny.clndrx;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.vktrhrsny.clndrx.gl.ViewGL;
import com.vktrhrsny.clndrx.vp.FragPagerAdapter;
import java.util.Date;



public class MainActivity extends FragmentActivity implements DialogFrag.EventCoordinator {

    private Date date;
    private ViewPager2 viewPager2;
    private ViewGL sfCanvas;
    private ImageView globeKnob;
    private RotateAnimation rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date= new Date();
        viewPager2 = findViewById(R.id.viewPager2);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setUserInputEnabled(false);

        FragPagerAdapter adapter = new FragPagerAdapter(getSupportFragmentManager(),getLifecycle());
        EventBus.getInstance();
        viewPager2.setAdapter(adapter);
        sfCanvas = findViewById(R.id.canvas);

        globeKnob = findViewById(R.id.frame);
        globeKnob.setOnClickListener(listener->{
            int i = viewPager2.getCurrentItem();
            if(i<2) scrollTo(++i);
            else scrollTo(0);
        });
        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setInterpolator(new OvershootInterpolator());

        final TextView[] textViews = {
                findViewById(R.id.vp1),
                findViewById(R.id.vp2),
                findViewById(R.id.vp3),

        };
        textViews[0].getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.XOR);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<textViews.length;i++) {
                    if (i!=position) textViews[i].getBackground().clearColorFilter();
                    else textViews[position].getBackground().setColorFilter(Color.BLUE,PorterDuff.Mode.XOR);;
                }

                globeKnob.startAnimation(rotate);
            }
        });


        SeekBar seekBar = findViewById(R.id.speed_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                sfCanvas.setSpeed(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void scrollTo(int pageNumber){
        viewPager2.setCurrentItem(pageNumber);
        globeKnob.startAnimation(rotate);
    }

    public void showDialog(Date date){
        FragmentManager fm = getSupportFragmentManager();
        DialogFrag dialogFrag = DialogFrag.instantiate(date);
        EventBus.getInstance().publish(date);
        dialogFrag.show(fm, "dialog_frag_tag");

    }

    public void vpScroll(View view) {
        viewPager2.setCurrentItem(Integer.parseInt((String)view.getTag()),true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sfCanvas.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sfCanvas.onResume();
    }

    @Override
    public void passMemo(Date date) {
        this.date = date;
    }

    public Date getDate(){return date;}

}