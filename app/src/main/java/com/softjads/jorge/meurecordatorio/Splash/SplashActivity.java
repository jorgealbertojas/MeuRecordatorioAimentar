package com.softjads.jorge.meurecordatorio.Splash;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.softjads.jorge.meurecordatorio.R;


public class SplashActivity extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 9000;
    KenBurnsView kbv;

    public ImageView icon_main;
    public ImageView icon_softjads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        icon_main = (ImageView) findViewById(R.id.icon_main);
        icon_softjads = (ImageView) findViewById(R.id.icon_softjads);

        kbv=(KenBurnsView) findViewById(R.id.fragmentloginKenBurnsView1);

        icon_main.animate().setStartDelay(3000).setDuration(2000).alpha(1).start();

        icon_softjads.animate().setStartDelay(2000).setDuration(3000).alpha(1).start();

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                try {

                    finish();
                } catch (Exception e) {
                    finish();
                }







            }
        }, SPLASH_TIME_OUT);
    }
}
