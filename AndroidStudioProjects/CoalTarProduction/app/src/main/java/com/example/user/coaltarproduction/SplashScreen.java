package com.example.user.coaltarproduction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by User on 8/18/2017.
 */

public class SplashScreen extends AppCompatActivity {


    TextView textView1,textView2;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        textView2=(TextView)findViewById(R.id.textView);
        textView1=(TextView) findViewById(R.id.coal);
        sharedPreferences=getSharedPreferences("LoginVer",MODE_PRIVATE);

        Animation myAnim = AnimationUtils.loadAnimation(this,R.anim.blink);
        textView1.startAnimation(myAnim);
        textView2.startAnimation(myAnim);


        Thread mythread=new Thread(){
        @Override
        public void run() {
            try {
                for (int i=1;i<=3;i++) {


                    sleep(1000);


                }
                if (!(sharedPreferences.getString("Mobile","").equals("")) && ! sharedPreferences.getString("Mobile","").equals(null) )
                {
                    startActivity(new Intent(SplashScreen.this,HomeActivity.class));
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
            mythread.start();


    }
}
