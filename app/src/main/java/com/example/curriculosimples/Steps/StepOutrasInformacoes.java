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
import android.widget.Toast;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.example.curriculosimples.ActivityHomeScreen;
import com.example.curriculosimples.R;

public class StepOutrasInformacoes extends Fragment implements Step, BlockingStep {

    // Outras Informações.
    EditText etHabilidades;
    EditText etLinguas;
    EditText etInteresses;
    EditText etOutros;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout outrasInformacoesContent = (RelativeLayout) inflater.inflate(R.layout.step_outras_informacoes_layout, container, false);

        etHabilidades = (EditText) outrasInformacoesContent.findViewById(R.id.etHabilidades);
        etLinguas = (EditText) outrasInformacoesContent.findViewById(R.id.etLinguas);
        etInteresses = (EditText) outrasInformacoesContent.findViewById(R.id.etInteresses);
        etOutros = (EditText) outrasInformacoesContent.findViewById(R.id.etOutros);

        etHabilidades.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getOutrasInformacoes().setHabilidades(editable.toString());
            }
        });

        etLinguas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getOutrasInformacoes().setLinguas(editable.toString());
            }
        });

        etInteresses.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getOutrasInformacoes().setInteresses(editable.toString());
            }
        });

        etOutros.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getOutrasInformacoes().setOutros(editable.toString());
            }
        });

        consultarDados();

        return outrasInformacoesContent;
    }

    private void consultarDados() {
        if (ActivityHomeScreen.consultarDados()) {
            if (ActivityHomeScreen.curriculo.getOutrasInformacoes().getHabilidades() != null &&
                    !ActivityHomeScreen.curriculo.getOutrasInformacoes().getHabilidades().isEmpty()) {
                etHabilidades.setText(ActivityHomeScreen.curriculo.getOutrasInformacoes().getHabilidades());
            }
            if (ActivityHomeScreen.curriculo.getOutrasInformacoes().getLinguas() != null &&
                    !ActivityHomeScreen.curriculo.getOutrasInformacoes().getLinguas().isEmpty()) {
                etLinguas.setText(ActivityHomeScreen.curriculo.getOutrasInformacoes().getLinguas());
            }
            if (ActivityHomeScreen.curriculo.getOutrasInformacoes().getInteresses() != null &&
                    !ActivityHomeScreen.curriculo.getOutrasInformacoes().getInteresses().isEmpty()) {
                etInteresses.setText(ActivityHomeScreen.curriculo.getOutrasInformacoes().getInteresses());
            }
            if (ActivityHomeScreen.curriculo.getOutrasInformacoes().getOutros() != null &&
                    !ActivityHomeScreen.curriculo.getOutrasInformacoes().getOutros().isEmpty()) {
                etOutros.setText(ActivityHomeScreen.curriculo.getOutrasInformacoes().getOutros());
            }
        }
        else {
            Toast.makeText(getActivity(), "Error loading data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void limparComponentes() {
        // Outas Informações.
        etHabilidades.setText("");
        etLinguas.setText("");
        etInteresses.setText("");
        etOutros.setText("");
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) { }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        ActivityHomeScreen.salvarDadosOutrasInformacoes(getActivity());
        hideSoftKeyboard();
        callback.complete();
    }

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
