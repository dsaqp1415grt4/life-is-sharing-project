package edu.upc.eetac.dsa.iarroyo.lifeissharing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nacho on 30/05/15.
 */
public class SplashScreenActivity extends Activity {

    private long splashDelay = 6000; //6 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(SplashScreenActivity.this, LifeMainActivity.class);
                startActivity(mainIntent);
                finish();//Destruimos esta activity para prevenit que el usuario retorne aqui presionando el boton Atras.
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, splashDelay);//Pasado los 6 segundos dispara la tarea
    }

}