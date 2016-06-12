package itcr.deportizate;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import itcr.deportizate.Activities.MainActivity;

public class Splash extends AppCompatActivity {
    public static final int segundos = 2;
    public static final int milisegundos = segundos * 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        esperar();
    }

    public void esperar(){

        new CountDownTimer(milisegundos, 1000){

            @Override
            public void onTick(long mili){


            }

            @Override
            public void onFinish(){

                finish();
                Intent nuevaVentana = new Intent(Splash.this, MainActivity.class);
                startActivity(nuevaVentana);

            }
        }.start();

    }
}
