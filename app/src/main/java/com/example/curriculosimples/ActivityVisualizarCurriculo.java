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

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bvapp.arcmenulibrary.ArcMenu;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;

public class ActivityVisualizarCurriculo extends AppCompatActivity {

    // Componentes.
    PDFView pdfView;
    ArcMenu amCompartilhar;

    // Variáveis.
    String pathArquivoCurriculo;
    String path;
    File file;
    private static final int[] ITEM_DRAWABLES = { R.drawable.email_arcmenu, R.drawable.whatsapp_arcmenu };
    private String[] str = new String[] {"E-mail", "WhatsApp"};
    private String compartilhar = "";
    Dialog dialog;

    // Anúncios.
    private AdView adView;
    private InterstitialAd mInterstitialAd;
    static AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_curriculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        pdfView = (PDFView) findViewById(R.id.pdfView);
        adView = (AdView) findViewById(R.id.adView);
        amCompartilhar = (ArcMenu) findViewById(R.id.amCompartilhar);

        // Recuperar os dados da activity anterior para abrir o arquivo PDF.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pathArquivoCurriculo = extras.getString("PathArquivoCurriculo");
            path = extras.getString("Path");

            if (pathArquivoCurriculo != null && !pathArquivoCurriculo.isEmpty()) {
                file = new File(pathArquivoCurriculo);

                if (file.exists()) {
                    pdfView.fromFile(file).load();
                }
                else {
                    onBackPressed();
                }
            }
            else {
                onBackPressed();
            }
        }

        amCompartilhar.setToolTipTextSize(14);

        amCompartilhar.setAnim(500,500,ArcMenu.ANIM_MIDDLE_TO_DOWN,ArcMenu.ANIM_MIDDLE_TO_RIGHT,
                ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE,ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE);

        amCompartilhar.showTooltip(true);

        initArcMenu(amCompartilhar, str, ITEM_DRAWABLES);

        Toast.makeText(getApplicationContext(), getResources().getString(R.string.msgArquivoSalvo), Toast.LENGTH_LONG).show();

        if (ActivityHomeScreen.aplicativoComprado) {
            // Desabilitar a publicidade.
            adView.setVisibility(View.GONE);
            adView.setEnabled(false);
        }
        else {
            // Carregar anúncios.
            try {
                // Carregar o anúncio.
                adRequest = new AdRequest.Builder()
                        .build();

                adView.loadAd(adRequest);

                adView.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() { }

                    @Override
                    public void onAdFailedToLoad(int error) {
                        adView.loadAd(adRequest);
                    }

                    @Override
                    public void onAdLeftApplication() { }

                    @Override
                    public void onAdOpened() { }

                    @Override
                    public void onAdLoaded() {
                        adView.setVisibility(View.VISIBLE);
                        adView.setEnabled(true);
                    }
                });

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
                        if (compartilhar.equals("Email")) {
                            compartilharCurriculoEmail();
                        }
                        else if (compartilhar.equals("WhatsApp")) {
                            compartilharCurriculoWhatsApp();
                        }

                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }
                });
            }
            catch (Exception ex) { }
        }
    }

    private void initArcMenu(final ArcMenu menu, final String[] str, int[] itemDrawables) {
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(itemDrawables[i]);

            final int position = i;
            menu.addItem(item, str[i], new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0) {
                        compartilhar = "Email";

                        if (ActivityHomeScreen.aplicativoComprado) {
                            compartilharCurriculoEmail();
                        }
                        else {
                            if (mInterstitialAd.isLoaded()) {
                                dialog = new Dialog(ActivityVisualizarCurriculo.this, android.R.style.Theme_DeviceDefault_NoActionBar_TranslucentDecor);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.dialog_layout);
                                dialog.show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                        mInterstitialAd.show();
                                    }
                                }, 2000);
                            }
                            else {
                                compartilharCurriculoEmail();
                            }
                        }
                    }
                    else if (position == 1) {
                        compartilhar = "WhatsApp";

                        if (ActivityHomeScreen.aplicativoComprado) {
                            compartilharCurriculoWhatsApp();
                        }
                        else {
                            if (mInterstitialAd.isLoaded()) {
                                dialog = new Dialog(ActivityVisualizarCurriculo.this, android.R.style.Theme_DeviceDefault_NoActionBar_TranslucentDecor);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.dialog_layout);
                                dialog.show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                        mInterstitialAd.show();
                                    }
                                }, 2000);
                            }
                            else {
                                compartilharCurriculoWhatsApp();
                            }
                        }
                    }
                }
            });
        }
    }

    private void compartilharCurriculoEmail() {
        // Compartilhar via E-mail.
        if (file != null) {
            try {
                Uri uri = FileProvider.getUriForFile(this, "com.example.curriculosimples", file);
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, "");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.msgShare));
                emailIntent.setType("application/pdf");
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(emailIntent, "Send e-mail..."));
            }
            catch (Exception ex) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.msgErroCompartilharCurriculo), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void compartilharCurriculoWhatsApp() {
        // Compartilhar no WhatsApp.
        if (file != null) {
            try {
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.msgShare));
                share.setType("application/pdf");
                share.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(ActivityVisualizarCurriculo.this,
                        "com.example.curriculosimples", file));
                share.setPackage("com.whatsapp");
                startActivity(share);
            }
            catch (Exception ex) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.msgErroCompartilharCurriculo), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_visualizar_curriculo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        //noinspection SimplifiableIfStatement

        if (item.getItemId() == R.id.action_abrir_diretorio_arquivo) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);


            try {
                alert.setTitle(getResources().getString(R.string.app_name));
                alert.setMessage(String.format(getResources().getString(R.string.msgAbrirDiretorioCurriculo), file.getParent()));
                alert.setPositiveButton(getResources().getString(R.string.msgSim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String[] localArquivo = file.getCanonicalPath().split("\\/");
                            String diretorioArquivo = "";

                            for (int i = 0; i < localArquivo.length - 1; i++) {
                                diretorioArquivo += localArquivo[i] + "/";
                            }

                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            Uri uri = Uri.parse(diretorioArquivo);
                            intent.setDataAndType(uri, "text/csv");
                            startActivity(Intent.createChooser(intent, "Open folder"));
                        }
                        catch (Exception e) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.msgErroAbrirDiretorioCurriculo), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                alert.setNegativeButton(getResources().getString(R.string.msgNao), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });

                alert.show();
            }
            catch (Exception e) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.msgErroAbrirDiretorioCurriculo), Toast.LENGTH_LONG).show();
            }
            return true;
        }
        else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
