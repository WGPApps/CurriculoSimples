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

package com.example.curriculosimples.Steps;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.curriculosimples.ActivityHomeScreen;
import com.example.curriculosimples.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class StepHabilidadesCompetencias extends Fragment implements Step, BlockingStep {

    // Habilidades e competências.
    CheckBox cbGerencia;
    CheckBox cbLideranca;
    CheckBox cbVendas;
    CheckBox cbComunicacao;
    EditText etGerencia;
    EditText etLideranca;
    EditText etVendas;
    EditText etComunicacao;
    Dialog dialog;

    // Anúncios.
    private InterstitialAd mInterstitialAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout habilidadesCompetenciasContent = (RelativeLayout) inflater.inflate(R.layout.step_habilidades_competencias_layout, container, false);

        cbGerencia = (CheckBox) habilidadesCompetenciasContent.findViewById(R.id.cbGerencia);
        cbLideranca = (CheckBox) habilidadesCompetenciasContent.findViewById(R.id.cbLideranca);
        cbVendas = (CheckBox) habilidadesCompetenciasContent.findViewById(R.id.cbVendas);
        cbComunicacao = (CheckBox) habilidadesCompetenciasContent.findViewById(R.id.cbComunicacao);
        etGerencia = (EditText) habilidadesCompetenciasContent.findViewById(R.id.etGerencia);
        etLideranca = (EditText) habilidadesCompetenciasContent.findViewById(R.id.etLideranca);
        etVendas = (EditText) habilidadesCompetenciasContent.findViewById(R.id.etVendas);
        etComunicacao = (EditText) habilidadesCompetenciasContent.findViewById(R.id.etComunicacao);

        cbGerencia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    etGerencia.setVisibility(View.VISIBLE);
                    ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().setHabilidadeGerencia(true);
                }
                else {
                    etGerencia.setText("");
                    etGerencia.setVisibility(View.GONE);
                    ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().setHabilidadeGerencia(false);
                }
            }
        });

        cbLideranca.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    etLideranca.setVisibility(View.VISIBLE);
                    ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().setHabilidadeLideranca(true);
                }
                else {
                    etLideranca.setText("");
                    etLideranca.setVisibility(View.GONE);
                    ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().setHabilidadeLideranca(false);
                }
            }
        });

        cbVendas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    etVendas.setVisibility(View.VISIBLE);
                    ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().setHabilidadeVendas(true);
                }
                else {
                    etVendas.setText("");
                    etVendas.setVisibility(View.GONE);
                    ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().setHabilidadeVendas(false);
                }
            }
        });

        cbComunicacao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    etComunicacao.setVisibility(View.VISIBLE);
                    ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().setHabilidadeComunicacao(true);
                }
                else {
                    etComunicacao.setText("");
                    etComunicacao.setVisibility(View.GONE);
                    ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().setHabilidadeComunicacao(false);
                }
            }
        });

        etGerencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().setGerencia(editable.toString());
            }
        });

        etLideranca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().setLideranca(editable.toString());
            }
        });

        etVendas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().setVendas(editable.toString());
            }
        });

        etComunicacao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().setComunicacao(editable.toString());
            }
        });

        consultarDados();

        // Intersticial.
        mInterstitialAd = new InterstitialAd(getContext());
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
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        return habilidadesCompetenciasContent;
    }

    private void consultarDados() {
        if (ActivityHomeScreen.consultarDados()) {
            // Habilidades e Competências.
            if (ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().isHabilidadeGerencia()) {
                if (!ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().getGerencia().isEmpty()) {
                    cbGerencia.setChecked(true);
                    etGerencia.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().getGerencia());
                    etGerencia.setVisibility(View.VISIBLE);
                }
                else {
                    cbGerencia.setChecked(false);
                    etGerencia.setText("");
                    etGerencia.setVisibility(View.GONE);
                }
            }
            else {
                cbGerencia.setChecked(false);
                etGerencia.setText("");
                etGerencia.setVisibility(View.GONE);
            }

            if (ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().isHabilidadeLideranca()) {
                if (!ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().getLideranca().isEmpty()) {
                    cbLideranca.setChecked(true);
                    etLideranca.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().getLideranca());
                    etLideranca.setVisibility(View.VISIBLE);
                }
                else {
                    cbLideranca.setChecked(false);
                    etLideranca.setText("");
                    etLideranca.setVisibility(View.GONE);
                }
            }
            else {
                cbLideranca.setChecked(false);
                etLideranca.setText("");
                etLideranca.setVisibility(View.GONE);
            }

            if (ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().isHabilidadeVendas()) {
                if (!ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().getVendas().isEmpty()) {
                    cbVendas.setChecked(true);
                    etVendas.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().getVendas());
                    etVendas.setVisibility(View.VISIBLE);
                }
                else {
                    cbVendas.setChecked(false);
                    etVendas.setText("");
                    etVendas.setVisibility(View.GONE);
                }
            }
            else {
                cbVendas.setChecked(false);
                etVendas.setText("");
                etVendas.setVisibility(View.GONE);
            }

            if (ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().isHabilidadeComunicacao()) {
                if (!ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().getComunicacao().isEmpty()) {
                    cbComunicacao.setChecked(true);
                    etComunicacao.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getHabilidadesCompetencias().getComunicacao());
                    etComunicacao.setVisibility(View.VISIBLE);
                }
                else {
                    cbComunicacao.setChecked(false);
                    etComunicacao.setText("");
                    etComunicacao.setVisibility(View.GONE);
                }
            }
            else {
                cbComunicacao.setChecked(false);
                etComunicacao.setText("");
                etComunicacao.setVisibility(View.GONE);
            }
        }
        else {
            Toast.makeText(getActivity(), "Error loading data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void limparComponentes() {
        // Habilidades e competências.
        cbGerencia.setChecked(false);
        cbLideranca.setChecked(false);
        cbVendas.setChecked(false);
        cbComunicacao.setChecked(false);
        etGerencia.setText("");
        etGerencia.setVisibility(View.GONE);
        etLideranca.setText("");
        etLideranca.setVisibility(View.GONE);
        etVendas.setText("");
        etVendas.setVisibility(View.GONE);
        etComunicacao.setText("");
        etComunicacao.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() { }

    @Override
    public void onError(@NonNull VerificationError error) { }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            dialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_NoActionBar_TranslucentDecor);
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

        ActivityHomeScreen.salvarDadosBasicos(getActivity());
        hideSoftKeyboard();
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) { }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    public void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e) { }

    }
}
