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
import com.example.curriculosimples.MaskWatcher;
import com.example.curriculosimples.R;

import java.util.List;

import Control.ExperienciaProfissional;

public class StepExperienciaProfissional extends Fragment implements Step, BlockingStep {

    // Histórico de trabalho
    EditText etCargo1;
    EditText etEmpresa1;
    EditText etDataInicio1;
    EditText etDataTermino1;
    EditText etResponsabilidades1;
    EditText etCargo2;
    EditText etEmpresa2;
    EditText etDataInicio2;
    EditText etDataTermino2;
    EditText etResponsabilidades2;
    EditText etCargo3;
    EditText etEmpresa3;
    EditText etDataInicio3;
    EditText etDataTermino3;
    EditText etResponsabilidades3;
    EditText etCargo4;
    EditText etEmpresa4;
    EditText etDataInicio4;
    EditText etDataTermino4;
    EditText etResponsabilidades4;
    TextView tvExperienciaProfissional3;
    TextView tvExperienciaProfissional4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout historicoTrabalhoContent = (RelativeLayout) inflater.inflate(R.layout.step_experiencias_profissional_layout, container, false);

        etCargo1 = (EditText) historicoTrabalhoContent.findViewById(R.id.etCargo1);
        etEmpresa1 = (EditText) historicoTrabalhoContent.findViewById(R.id.etEmpresa1);
        etDataInicio1 = (EditText) historicoTrabalhoContent.findViewById(R.id.etDataInicio1);
        etDataTermino1 = (EditText) historicoTrabalhoContent.findViewById(R.id.etDataTermino1);
        etResponsabilidades1 = (EditText) historicoTrabalhoContent.findViewById(R.id.etResponsabilidades1);

        etCargo2 = (EditText) historicoTrabalhoContent.findViewById(R.id.etCargo2);
        etEmpresa2 = (EditText) historicoTrabalhoContent.findViewById(R.id.etEmpresa2);
        etDataInicio2 = (EditText) historicoTrabalhoContent.findViewById(R.id.etDataInicio2);
        etDataTermino2 = (EditText) historicoTrabalhoContent.findViewById(R.id.etDataTermino2);
        etResponsabilidades2 = (EditText) historicoTrabalhoContent.findViewById(R.id.etResponsabilidades2);

        tvExperienciaProfissional3 = (TextView) historicoTrabalhoContent.findViewById(R.id.tvExperienciaProfissional3);
        etCargo3 = (EditText) historicoTrabalhoContent.findViewById(R.id.etCargo3);
        etEmpresa3 = (EditText) historicoTrabalhoContent.findViewById(R.id.etEmpresa3);
        etDataInicio3 = (EditText) historicoTrabalhoContent.findViewById(R.id.etDataInicio3);
        etDataTermino3 = (EditText) historicoTrabalhoContent.findViewById(R.id.etDataTermino3);
        etResponsabilidades3 = (EditText) historicoTrabalhoContent.findViewById(R.id.etResponsabilidades3);

        tvExperienciaProfissional4 = (TextView) historicoTrabalhoContent.findViewById(R.id.tvExperienciaProfissional4);
        etCargo4 = (EditText) historicoTrabalhoContent.findViewById(R.id.etCargo4);
        etEmpresa4 = (EditText) historicoTrabalhoContent.findViewById(R.id.etEmpresa4);
        etDataInicio4 = (EditText) historicoTrabalhoContent.findViewById(R.id.etDataInicio4);
        etDataTermino4 = (EditText) historicoTrabalhoContent.findViewById(R.id.etDataTermino4);
        etResponsabilidades4 = (EditText) historicoTrabalhoContent.findViewById(R.id.etResponsabilidades4);

        etDataInicio1.addTextChangedListener(new MaskWatcher("##/####"));
        etDataTermino1.addTextChangedListener(new MaskWatcher("##/####"));
        etDataInicio2.addTextChangedListener(new MaskWatcher("##/####"));
        etDataTermino2.addTextChangedListener(new MaskWatcher("##/####"));

        if (ActivityHomeScreen.aplicativoComprado) {
            etCargo3.setEnabled(true);
            etEmpresa3.setEnabled(true);
            etDataInicio3.setEnabled(true);
            etDataTermino3.setEnabled(true);
            etResponsabilidades3.setEnabled(true);

            etCargo4.setEnabled(true);
            etEmpresa4.setEnabled(true);
            etDataInicio4.setEnabled(true);
            etDataTermino4.setEnabled(true);
            etResponsabilidades4.setEnabled(true);

            etDataInicio3.addTextChangedListener(new MaskWatcher("##/####"));
            etDataTermino3.addTextChangedListener(new MaskWatcher("##/####"));
            etDataInicio4.addTextChangedListener(new MaskWatcher("##/####"));
            etDataTermino4.addTextChangedListener(new MaskWatcher("##/####"));

            tvExperienciaProfissional3.setText(getResources().getString(R.string.tvExperienciaProfissional3));
            tvExperienciaProfissional4.setText(getResources().getString(R.string.tvExperienciaProfissional4));

            // Experiência Profissional 3
            etCargo3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).setCargo(editable.toString());
                }
            });

            etEmpresa3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).setEmpresa(editable.toString());
                }
            });

            etDataInicio3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).setDataInicio(editable.toString());
                }
            });

            etDataTermino3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).setDataTermino(editable.toString());
                }
            });

            etResponsabilidades3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).setResponsabilidades(editable.toString());
                }
            });

            // Experiência Profissional 4
            etCargo4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).setCargo(editable.toString());
                }
            });

            etEmpresa4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).setEmpresa(editable.toString());
                }
            });

            etDataInicio4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).setDataInicio(editable.toString());
                }
            });

            etDataTermino4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).setDataTermino(editable.toString());
                }
            });

            etResponsabilidades4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).setResponsabilidades(editable.toString());
                }
            });
        }

        // Experiência Profissional 1
        etCargo1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).setCargo(editable.toString());
            }
        });

        etEmpresa1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).setEmpresa(editable.toString());
            }
        });

        etDataInicio1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).setDataInicio(editable.toString());
            }
        });

        etDataTermino1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).setDataTermino(editable.toString());
            }
        });

        etResponsabilidades1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).setResponsabilidades(editable.toString());
            }
        });

        // Experiência Profissional 2
        etCargo2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).setCargo(editable.toString());
            }
        });

        etEmpresa2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).setEmpresa(editable.toString());
            }
        });

        etDataInicio2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).setDataInicio(editable.toString());
            }
        });

        etDataTermino2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).setDataTermino(editable.toString());
            }
        });

        etResponsabilidades2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).setResponsabilidades(editable.toString());
            }
        });

        consultarDados();

        return historicoTrabalhoContent;
    }

    private void consultarDados() {
        if (ActivityHomeScreen.consultarDados()) {
            // Experiência Profissional 1.
            if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getDataInicio() != null &&
                    !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getDataInicio().isEmpty()) {
                etDataInicio1.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getDataInicio());
            }
            if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getDataTermino() != null &&
                    !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getDataTermino().isEmpty()) {
                etDataTermino1.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getDataTermino());
            }
            if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getCargo() != null &&
                    !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getCargo().isEmpty()) {
                etCargo1.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getCargo());
            }
            if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getEmpresa() != null &&
                    !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getEmpresa().isEmpty()) {
                etEmpresa1.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getEmpresa());
            }
            if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getResponsabilidades() != null &&
                    !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getResponsabilidades().isEmpty()) {
                etResponsabilidades1.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(0).getResponsabilidades());
            }

            // Experiencia Profissional 2.
            if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getDataInicio() != null &&
                    !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getDataInicio().isEmpty()) {
                etDataInicio2.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getDataInicio());
            }
            if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getDataTermino() != null &&
                    !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getDataTermino().isEmpty()) {
                etDataTermino2.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getDataTermino());
            }
            if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getCargo() != null &&
                    !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getCargo().isEmpty()) {
                etCargo2.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getCargo());
            }
            if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getEmpresa() != null &&
                    !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getEmpresa().isEmpty()) {
                etEmpresa2.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getEmpresa());
            }
            if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getResponsabilidades() != null &&
                    !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getResponsabilidades().isEmpty()) {
                etResponsabilidades2.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(1).getResponsabilidades());
            }

            if (ActivityHomeScreen.aplicativoComprado) {
                // Experiência Profissional 3.
                if (indexExists(ActivityHomeScreen.curriculo.getHistoricosTrabalho(), 2)) {
                    if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getDataInicio() != null &&
                            !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getDataInicio().isEmpty()) {
                        etDataInicio3.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getDataInicio());
                    }
                    if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getDataTermino() != null &&
                            !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getDataTermino().isEmpty()) {
                        etDataTermino3.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getDataTermino());
                    }
                    if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getCargo() != null &&
                            !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getCargo().isEmpty()) {
                        etCargo3.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getCargo());
                    }
                    if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getEmpresa() != null &&
                            !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getEmpresa().isEmpty()) {
                        etEmpresa3.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getEmpresa());
                    }
                    if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getResponsabilidades() != null &&
                            !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getResponsabilidades().isEmpty()) {
                        etResponsabilidades3.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(2).getResponsabilidades());
                    }
                }
                else {
                    ActivityHomeScreen.curriculo.getHistoricosTrabalho().add(new ExperienciaProfissional());
                }

                // Experiência Profissional 4.
                if (indexExists(ActivityHomeScreen.curriculo.getHistoricosTrabalho(), 3)) {
                    if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getDataInicio() != null &&
                            !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getDataInicio().isEmpty()) {
                        etDataInicio4.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getDataInicio());
                    }
                    if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getDataTermino() != null &&
                            !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getDataTermino().isEmpty()) {
                        etDataTermino4.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getDataTermino());
                    }
                    if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getCargo() != null &&
                            !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getCargo().isEmpty()) {
                        etCargo4.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getCargo());
                    }
                    if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getEmpresa() != null &&
                            !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getEmpresa().isEmpty()) {
                        etEmpresa4.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getEmpresa());
                    }
                    if (ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getResponsabilidades() != null &&
                            !ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getResponsabilidades().isEmpty()) {
                        etResponsabilidades4.setText(ActivityHomeScreen.curriculo.getHistoricosTrabalho().get(3).getResponsabilidades());
                    }
                }
                else {
                    ActivityHomeScreen.curriculo.getHistoricosTrabalho().add(new ExperienciaProfissional());
                }
            }
        }
        else {
            Toast.makeText(getActivity(), "Error loading data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void limparComponentes() {
        // Histórico de trabalho
        etCargo1.setText("");
        etEmpresa1.setText("");
        if (!etDataInicio1.getText().toString().isEmpty()) {
            etDataInicio1.setText("");
        }
        if (!etDataTermino1.getText().toString().isEmpty()) {
            etDataTermino1.setText("");
        }
        etResponsabilidades1.setText("");
        etCargo2.setText("");
        etEmpresa2.setText("");
        if (!etDataInicio2.getText().toString().isEmpty()) {
            etDataInicio2.setText("");
        }
        if (!etDataTermino2.getText().toString().isEmpty()) {
            etDataTermino2.setText("");
        }
        etResponsabilidades2.setText("");

        if (ActivityHomeScreen.aplicativoComprado) {
            etCargo3.setText("");
            etEmpresa3.setText("");
            if (!etDataInicio3.getText().toString().isEmpty()) {
                etDataInicio3.setText("");
            }
            if (!etDataTermino3.getText().toString().isEmpty()) {
                etDataTermino3.setText("");
            }
            etResponsabilidades3.setText("");
            etCargo4.setText("");
            etEmpresa4.setText("");
            if (!etDataInicio4.getText().toString().isEmpty()) {
                etDataInicio4.setText("");
            }
            if (!etDataTermino4.getText().toString().isEmpty()) {
                etDataTermino4.setText("");
            }
            etResponsabilidades4.setText("");
        }
    }

    public boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
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
        ActivityHomeScreen.salvarDadosExperienciaProfissional(getActivity());
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
