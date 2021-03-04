/*
 * Copyright (C) 2018-2021, Wíliam Gonçalves <wgp_apps@hotmail.com>
 *
 * This file is part of Easy Curriculum.
 *
 * Easy Curriculum is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.example.curriculosimples;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    // SPLASHSCREEN.
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    // Variáveis.
    boolean aplicativoComprado;

    // Preferências
    SharedPreferences preferencias;
    public static final String PREFS_CURRICULOFACIL = "AppPreferences";

    // Anúncios.
    private InterstitialAd mInterstitialAd;
    static AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar.setVisibility(View.GONE);

        verificarAplicativoComprado();

        if (aplicativoComprado) {
            // Create an Intent that will start the main activity.
            Intent mainIntent = new Intent(MainActivity.this, ActivityHomeScreen.class);
            MainActivity.this.startActivity(mainIntent);
            finish();
        }
        else {
            // Intersticial.
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId("appInterstitialId");
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() { }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }

                @Override
                public void onAdOpened() { }

                @Override
                public void onAdLeftApplication() { }

                @Override
                public void onAdClosed() {
                    Intent mainIntent = new Intent(MainActivity.this, ActivityHomeScreen.class);
                    MainActivity.this.startActivity(mainIntent);
                    finish();
                }
            });

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                    else {
                        Intent mainIntent = new Intent(MainActivity.this, ActivityHomeScreen.class);
                        MainActivity.this.startActivity(mainIntent);
                        finish();
                    }
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }

    private void verificarAplicativoComprado() {
        // Verificar se o aplicativo foi comprado e alterar a Navigation Drawer.
        preferencias = getSharedPreferences(PREFS_CURRICULOFACIL, MODE_PRIVATE);
        aplicativoComprado = preferencias.getBoolean("proversion", false);
    }
}