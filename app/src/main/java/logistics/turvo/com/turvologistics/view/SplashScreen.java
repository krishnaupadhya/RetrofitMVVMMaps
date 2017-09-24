package logistics.turvo.com.turvologistics.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import logistics.turvo.com.turvologistics.R;

/**
 * Created by Krishna Upadhya
 */
public class SplashScreen extends AppCompatActivity  {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        launchHomeScreen();
    }

    private void launchHomeScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectToHomeScreen();
            }
        }, 3000);
    }

    private void redirectToHomeScreen() {
        Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
