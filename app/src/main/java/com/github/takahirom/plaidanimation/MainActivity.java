package com.github.takahirom.plaidanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            animateToolbar();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        imageView = (ImageView) findViewById(R.id.image_sample);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff191919")));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(R.drawable.image_sample);
                ViewCompat.setHasTransientState(imageView, true);
                final ObservableColorMatrix cm = new ObservableColorMatrix();
                final ObjectAnimator saturation = ObjectAnimator.ofFloat(
                        cm, ObservableColorMatrix.SATURATION, 0f, 1f);
                saturation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener
                        () {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        // just animating the color matrix does not invalidate the
                        // drawable so need this update listener.  Also have to create a
                        // new CMCF as the matrix is immutable :(
                        imageView.setColorFilter(new ColorMatrixColorFilter(cm));
                    }
                });
                saturation.setDuration(2000L);
                saturation.setInterpolator(new FastOutSlowInInterpolator());
                saturation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        imageView.clearColorFilter();
                        ViewCompat.setHasTransientState(imageView, false);
                    }
                });
                saturation.start();


            }
        }, 3000);
    }


    private void animateToolbar() {
        // this is gross but toolbar doesn't expose it's children to animate them :(
        View t = toolbar.getChildAt(0);
        if (t != null && t instanceof TextView) {
            TextView title = (TextView) t;

            // fade in and space out the title.  Animating the letterSpacing performs horribly so
            // fake it by setting the desired letterSpacing then animating the scaleX ¯\_(ツ)_/¯
            title.setAlpha(0f);
            title.setScaleX(0.8f);

            title.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setStartDelay(300)
                    .setDuration(900)
                    .setInterpolator(new FastOutSlowInInterpolator());
        }
        View amv = toolbar.getChildAt(1);
        if (amv != null & amv instanceof ActionMenuView) {
            ActionMenuView actions = (ActionMenuView) amv;
            popAnim(actions.getChildAt(0), 500, 200); // filter
            popAnim(actions.getChildAt(1), 700, 200); // overflow
        }
    }

    private void popAnim(View v, int startDelay, int duration) {
        if (v != null) {
            v.setAlpha(0f);
            v.setScaleX(0f);
            v.setScaleY(0f);

            v.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(startDelay)
                    .setDuration(duration)
                    .setInterpolator(AnimationUtils.loadInterpolator(this,
                            android.R.interpolator.overshoot));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
