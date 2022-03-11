package com.example.libmana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Hello extends AppCompatActivity {
    TextView txtHello;
    ImageView imgHello;
    Animation animationText,animationImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);

        txtHello=findViewById(R.id.helo);
        imgHello=findViewById(R.id.logo);
        animationText= AnimationUtils.loadAnimation(this,R.anim.text_hello);
        txtHello.setAnimation(animationText);
        animationImage=AnimationUtils.loadAnimation(this,R.anim.img_hello);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Hello.this,Login.class));
                finish();
            }
        },1500);
    }
}