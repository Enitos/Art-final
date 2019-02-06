package com.somada.lope_mora.art3.activities;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.airbnb.lottie.LottieAnimationView;
import com.somada.lope_mora.art3.R;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottieAnimationView);
        startCheckAnimation();
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), MainActivity.class);
//                view.getContext().startActivity(intent);
                mostrarMainActivity();
            }
        });

        TextView sair = (TextView) findViewById(R.id.sair);
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestroy();
            }
        });
    }

    private void startCheckAnimation(){
        final ValueAnimator animator = ValueAnimator.ofFloat(0f,1f).setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lottieAnimationView.setProgress((Float)animator.getAnimatedValue());
            }
        });

        if (lottieAnimationView.getProgress() == 0f){
            animator.setStartDelay(1500);
            animator.start();
        }else {
            lottieAnimationView.setProgress(0f);
        }
    }

    private void mostrarMainActivity(){
        Intent intent = new Intent(
                SplashActivity.this,MainActivity.class
        );
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
