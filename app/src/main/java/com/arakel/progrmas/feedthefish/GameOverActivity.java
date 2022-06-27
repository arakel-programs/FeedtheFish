package com.arakel.progrmas.feedthefish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class GameOverActivity extends AppCompatActivity {

    private Button StartGameAgain, GoBackBtn;
    private TextView DisplayScore;
    private String score;

    public InterstitialAd interstitialAd;//Interstitial Ad
    public int transition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //Ad start
        MobileAds.initialize(this, "ca-app-pub-5332101373774587~9504687795");
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-5332101373774587/1330420975");//ca-app-pub-3940256099942544/1033173712
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
        //Ad end

        //Closing ad with x
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                try{
                    switch (transition){
                        case 0: break;
                        case 1: Intent intent = new Intent(GameOverActivity.this, GameStartActivity.class);
                            startActivity(intent);finish();break;
                        case 2: Intent intent1 = new Intent(GameOverActivity.this, MainActivity.class);
                            startActivity(intent1);finish();break;
                        default:break;
                    }
                }catch (Exception e){
                    //empty
                }
            }
        });
        //Closing ad with x

        score = getIntent().getExtras().get("score").toString();

        StartGameAgain = (Button) findViewById(R.id.playAgainBtn);
        DisplayScore = (TextView) findViewById(R.id.displayScore);

        GoBackBtn = (Button) findViewById(R.id.goBackBtn);

        StartGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interstitialAd.isLoaded()){
                    transition = 2;
                    interstitialAd.show();
                }else {
                    try {
                        Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        //empty
                    }
                }
            }
        });

        GoBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interstitialAd.isLoaded()){
                    transition = 1;
                    interstitialAd.show();
                }else {
                    try {
                        Intent intent = new Intent(GameOverActivity.this, GameStartActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        //empty
                    }
                }
            }
        });

        DisplayScore.setText(getString(R.string.score_equel) + score);
    }
}