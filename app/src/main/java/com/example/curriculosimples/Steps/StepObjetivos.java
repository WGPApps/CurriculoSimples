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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.example.curriculosimples.ActivityHomeScreen;
import com.example.curriculosimples.R;

import Control.DadosBasicos;

public class StepObjetivos extends Fragment implements Step, BlockingStep {

    // Objetivo.
    EditText etObjetivo;
    TextView tvPreencherDadosObrigatoriosObjetivo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout objetivoContent = (RelativeLayout) inflater.inflate(R.layout.step_objetivo_layout, container, false);

        etObjetivo = (EditText) objetivoContent.findViewById(R.id.etObjetivo);
        tvPreencherDadosObrigatoriosObjetivo = (TextView) objetivoContent.findViewById(R.id.tvPreencherDadosObrigatoriosObjetivo);

        etObjetivo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                verificarStepObjetivoCompleto();
                ActivityHomeScreen.curriculo.getDadosBasicos().setObjetivo(editable.toString());

            }
        });

        consultarDados();

        return objetivoContent;
    }

    private boolean verificarStepObjetivoCompleto() {
        boolean completo = false;

        if (!etObjetivo.getText().toString().isEmpty()) {
            completo = true;
            tvPreencherDadosObrigatoriosObjetivo.setVisibility(View.GONE);
        }
        else {
            completo = false;
            tvPreencherDadosObrigatoriosObjetivo.setVisibility(View.VISIBLE);
        }

        return completo;
    }

    private void consultarDados() {
        if (ActivityHomeScreen.consultarDados()) {
            // Objetivo.
            if (ActivityHomeScreen.curriculo.getDadosBasicos().getObjetivo() != null &&
                    !ActivityHomeScreen.curriculo.getDadosBasicos().getObjetivo().isEmpty()) {
                etObjetivo.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getObjetivo());
            }
        }
        else {
            Toast.makeText(getActivity(), "Error loading data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void limparComponentes() {
        // Objetivo.
        etObjetivo.setText("");
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
        if (verificarStepObjetivoCompleto()) {
            ActivityHomeScreen.salvarDadosBasicos(getActivity());
            hideSoftKeyboard();
            callback.goToNextStep();
        }
        else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.tvPreencherDadosObrigatorios).replace("- ", ""), Toast.LENGTH_LONG).show();
        }
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
