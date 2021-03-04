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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import Control.Educacao;

public class StepEducacao extends Fragment implements Step, BlockingStep {

    // Formação.
    EditText etGrau1;
    EditText etNomeInstituicao1;
    EditText etCidadeFormacao1;
    CheckBox cbCursando1;
    EditText etAno1;
    EditText etInformacoesRelevantes1;
    EditText etGrau2;
    EditText etNomeInstituicao2;
    EditText etCidadeFormacao2;
    CheckBox cbCursando2;
    EditText etAno2;
    EditText etInformacoesRelevantes2;
    EditText etGrau3;
    EditText etNomeInstituicao3;
    EditText etCidadeFormacao3;
    CheckBox cbCursando3;
    EditText etAno3;
    EditText etInformacoesRelevantes3;
    EditText etGrau4;
    EditText etNomeInstituicao4;
    EditText etCidadeFormacao4;
    CheckBox cbCursando4;
    EditText etAno4;
    EditText etInformacoesRelevantes4;
    TextView tvEducacao3;
    TextView tvEducacao4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout formacaoContent = (RelativeLayout) inflater.inflate(R.layout.step_educacao_layout, container, false);

        etGrau1 = (EditText) formacaoContent.findViewById(R.id.etGrau1);
        cbCursando1 = (CheckBox) formacaoContent.findViewById(R.id.cbCursando1);
        etNomeInstituicao1 = (EditText) formacaoContent.findViewById(R.id.etNomeInstituicao1);
        etCidadeFormacao1 = (EditText) formacaoContent.findViewById(R.id.etCidadeFormacao1);
        etAno1 = (EditText) formacaoContent.findViewById(R.id.etAno1);
        etInformacoesRelevantes1 = (EditText) formacaoContent.findViewById(R.id.etInformacoesRelevantes1);

        etGrau2 = (EditText) formacaoContent.findViewById(R.id.etGrau2);
        cbCursando2 = (CheckBox) formacaoContent.findViewById(R.id.cbCursando2);
        etNomeInstituicao2 = (EditText) formacaoContent.findViewById(R.id.etNomeInstituicao2);
        etCidadeFormacao2 = (EditText) formacaoContent.findViewById(R.id.etCidadeFormacao2);
        etAno2 = (EditText) formacaoContent.findViewById(R.id.etAno2);
        etInformacoesRelevantes2 = (EditText) formacaoContent.findViewById(R.id.etInformacoesRelevantes2);

        tvEducacao3 = (TextView) formacaoContent.findViewById(R.id.tvEducacao3);
        etGrau3 = (EditText) formacaoContent.findViewById(R.id.etGrau3);
        cbCursando3 = (CheckBox) formacaoContent.findViewById(R.id.cbCursando3);
        etNomeInstituicao3 = (EditText) formacaoContent.findViewById(R.id.etNomeInstituicao3);
        etCidadeFormacao3 = (EditText) formacaoContent.findViewById(R.id.etCidadeFormacao3);
        etAno3 = (EditText) formacaoContent.findViewById(R.id.etAno3);
        etInformacoesRelevantes3 = (EditText) formacaoContent.findViewById(R.id.etInformacoesRelevantes3);

        tvEducacao4 = (TextView) formacaoContent.findViewById(R.id.tvEducacao4);
        etGrau4 = (EditText) formacaoContent.findViewById(R.id.etGrau4);
        cbCursando4 = (CheckBox) formacaoContent.findViewById(R.id.cbCursando4);
        etNomeInstituicao4 = (EditText) formacaoContent.findViewById(R.id.etNomeInstituicao4);
        etCidadeFormacao4 = (EditText) formacaoContent.findViewById(R.id.etCidadeFormacao4);
        etAno4 = (EditText) formacaoContent.findViewById(R.id.etAno4);
        etInformacoesRelevantes4 = (EditText) formacaoContent.findViewById(R.id.etInformacoesRelevantes4);

        etAno1.addTextChangedListener(new MaskWatcher("##/####"));
        etAno2.addTextChangedListener(new MaskWatcher("##/####"));

        if (ActivityHomeScreen.aplicativoComprado) {
            etGrau3.setEnabled(true);
            cbCursando3.setEnabled(true);
            etNomeInstituicao3.setEnabled(true);
            etCidadeFormacao3.setEnabled(true);
            etAno3.setEnabled(true);
            etInformacoesRelevantes3.setEnabled(true);

            etGrau4.setEnabled(true);
            cbCursando4.setEnabled(true);
            etNomeInstituicao4.setEnabled(true);
            etCidadeFormacao4.setEnabled(true);
            etAno4.setEnabled(true);
            etInformacoesRelevantes4.setEnabled(true);

            etAno3.addTextChangedListener(new MaskWatcher("##/####"));
            etAno4.addTextChangedListener(new MaskWatcher("##/####"));

            tvEducacao3.setText(getResources().getString(R.string.tvEducacao3));
            tvEducacao4.setText(getResources().getString(R.string.tvEducacao4));

            // Formação 3
            etGrau3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getFormacoes().get(2).setGrau(editable.toString());
                }
            });

            cbCursando3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        etAno3.setEnabled(false);
                        if (!etAno3.getText().toString().isEmpty()) {
                            etAno3.setText("");
                        }
                        ActivityHomeScreen.curriculo.getFormacoes().get(2).setCursando(true);
                    }
                    else {
                        etAno3.setEnabled(true);
                        ActivityHomeScreen.curriculo.getFormacoes().get(2).setCursando(false);
                    }
                }
            });

            etNomeInstituicao3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getFormacoes().get(2).setNomeInstituicao(editable.toString());
                }
            });

            etCidadeFormacao3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getFormacoes().get(2).setCidade(editable.toString());
                }
            });

            etAno3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getFormacoes().get(2).setAno(editable.toString());
                }
            });

            etInformacoesRelevantes3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getFormacoes().get(2).setInformacoesRelevantes(editable.toString());
                }
            });

            // Formação 4
            etGrau4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getFormacoes().get(3).setGrau(editable.toString());
                }
            });

            cbCursando4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        etAno4.setEnabled(false);
                        if (!etAno4.getText().toString().isEmpty()) {
                            etAno4.setText("");
                        }
                        ActivityHomeScreen.curriculo.getFormacoes().get(3).setCursando(true);
                    }
                    else {
                        etAno4.setEnabled(true);
                        ActivityHomeScreen.curriculo.getFormacoes().get(3).setCursando(false);
                    }
                }
            });

            etNomeInstituicao4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getFormacoes().get(3).setNomeInstituicao(editable.toString());
                }
            });

            etCidadeFormacao4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getFormacoes().get(3).setCidade(editable.toString());
                }
            });

            etAno4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getFormacoes().get(3).setAno(editable.toString());
                }
            });

            etInformacoesRelevantes4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    ActivityHomeScreen.curriculo.getFormacoes().get(3).setInformacoesRelevantes(editable.toString());
                }
            });
        }

        // Formação 1
        etGrau1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getFormacoes().get(0).setGrau(editable.toString());
            }
        });

        cbCursando1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    etAno1.setEnabled(false);
                    if (!etAno1.getText().toString().isEmpty()) {
                        etAno1.setText("");
                    }
                    ActivityHomeScreen.curriculo.getFormacoes().get(0).setCursando(true);
                }
                else {
                    etAno1.setEnabled(true);
                    ActivityHomeScreen.curriculo.getFormacoes().get(0).setCursando(false);
                }
            }
        });

        etNomeInstituicao1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getFormacoes().get(0).setNomeInstituicao(editable.toString());
            }
        });

        etCidadeFormacao1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getFormacoes().get(0).setCidade(editable.toString());
            }
        });

        etAno1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getFormacoes().get(0).setAno(editable.toString());
            }
        });

        etInformacoesRelevantes1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getFormacoes().get(0).setInformacoesRelevantes(editable.toString());
            }
        });

        // Formação 2
        etGrau2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getFormacoes().get(1).setGrau(editable.toString());
            }
        });

        cbCursando2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    etAno2.setEnabled(false);
                    if (!etAno2.getText().toString().isEmpty()) {
                        etAno2.setText("");
                    }
                    ActivityHomeScreen.curriculo.getFormacoes().get(1).setCursando(true);
                }
                else {
                    etAno2.setEnabled(true);
                    ActivityHomeScreen.curriculo.getFormacoes().get(1).setCursando(false);
                }
            }
        });

        etNomeInstituicao2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getFormacoes().get(1).setNomeInstituicao(editable.toString());
            }
        });

        etCidadeFormacao2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getFormacoes().get(1).setCidade(editable.toString());
            }
        });

        etAno2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getFormacoes().get(1).setAno(editable.toString());
            }
        });

        etInformacoesRelevantes2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityHomeScreen.curriculo.getFormacoes().get(1).setInformacoesRelevantes(editable.toString());
            }
        });

        consultarDados();

        return formacaoContent;
    }

    private void consultarDados() {
        if (ActivityHomeScreen.consultarDados()) {
            // Formação 1.
            if (ActivityHomeScreen.curriculo.getFormacoes().get(0).isCursando()) {
                cbCursando1.setChecked(true);
                etAno1.setText("");
                etAno1.setEnabled(false);
            }
            else {
                cbCursando1.setChecked(false);
                etAno1.setEnabled(true);
                if (ActivityHomeScreen.curriculo.getFormacoes().get(0).getAno() != null &&
                        !ActivityHomeScreen.curriculo.getFormacoes().get(0).getAno().isEmpty()) {
                    etAno1.setText(ActivityHomeScreen.curriculo.getFormacoes().get(0).getAno());
                }
            }
            if (ActivityHomeScreen.curriculo.getFormacoes().get(0).getGrau() != null &&
                    !ActivityHomeScreen.curriculo.getFormacoes().get(0).getGrau().isEmpty()) {
                etGrau1.setText(ActivityHomeScreen.curriculo.getFormacoes().get(0).getGrau());
            }
            if (ActivityHomeScreen.curriculo.getFormacoes().get(0).getNomeInstituicao() != null &&
                    !ActivityHomeScreen.curriculo.getFormacoes().get(0).getNomeInstituicao().isEmpty()) {
                etNomeInstituicao1.setText(ActivityHomeScreen.curriculo.getFormacoes().get(0).getNomeInstituicao());
            }
            if (ActivityHomeScreen.curriculo.getFormacoes().get(0).getCidade() != null &&
                    !ActivityHomeScreen.curriculo.getFormacoes().get(0).getCidade().isEmpty()) {
                etCidadeFormacao1.setText(ActivityHomeScreen.curriculo.getFormacoes().get(0).getCidade());
            }
            if (ActivityHomeScreen.curriculo.getFormacoes().get(0).getInformacoesRelevantes() != null &&
                    !ActivityHomeScreen.curriculo.getFormacoes().get(0).getInformacoesRelevantes().isEmpty()) {
                etInformacoesRelevantes1.setText(ActivityHomeScreen.curriculo.getFormacoes().get(0).getInformacoesRelevantes());
            }

            // Formação 2.
            if (ActivityHomeScreen.curriculo.getFormacoes().get(1).isCursando()) {
                cbCursando2.setChecked(true);
                etAno2.setText("");
                etAno2.setEnabled(false);
            }
            else {
                cbCursando2.setChecked(false);
                etAno2.setEnabled(true);
                if (ActivityHomeScreen.curriculo.getFormacoes().get(1).getAno() != null &&
                        !ActivityHomeScreen.curriculo.getFormacoes().get(1).getAno().isEmpty()) {
                    etAno2.setText(ActivityHomeScreen.curriculo.getFormacoes().get(1).getAno());
                }
            }
            if (ActivityHomeScreen.curriculo.getFormacoes().get(1).getGrau() != null &&
                    !ActivityHomeScreen.curriculo.getFormacoes().get(1).getGrau().isEmpty()) {
                etGrau2.setText(ActivityHomeScreen.curriculo.getFormacoes().get(1).getGrau());
            }
            if (ActivityHomeScreen.curriculo.getFormacoes().get(1).getNomeInstituicao() != null &&
                    !ActivityHomeScreen.curriculo.getFormacoes().get(1).getNomeInstituicao().isEmpty()) {
                etNomeInstituicao2.setText(ActivityHomeScreen.curriculo.getFormacoes().get(1).getNomeInstituicao());
            }
            if (ActivityHomeScreen.curriculo.getFormacoes().get(1).getCidade() != null &&
                    !ActivityHomeScreen.curriculo.getFormacoes().get(1).getCidade().isEmpty()) {
                etCidadeFormacao2.setText(ActivityHomeScreen.curriculo.getFormacoes().get(1).getCidade());
            }
            if (ActivityHomeScreen.curriculo.getFormacoes().get(1).getInformacoesRelevantes() != null &&
                    !ActivityHomeScreen.curriculo.getFormacoes().get(1).getInformacoesRelevantes().isEmpty()) {
                etInformacoesRelevantes2.setText(ActivityHomeScreen.curriculo.getFormacoes().get(1).getInformacoesRelevantes());
            }

            if (ActivityHomeScreen.aplicativoComprado) {
                // Formação 3.
                if (indexExists(ActivityHomeScreen.curriculo.getFormacoes(), 2)) {
                    if (ActivityHomeScreen.curriculo.getFormacoes().get(2).isCursando()) {
                        cbCursando3.setChecked(true);
                        etAno3.setText("");
                        etAno3.setEnabled(false);
                    }
                    else {
                        cbCursando3.setChecked(false);
                        etAno3.setEnabled(true);
                        if (ActivityHomeScreen.curriculo.getFormacoes().get(2).getAno() != null &&
                                !ActivityHomeScreen.curriculo.getFormacoes().get(2).getAno().isEmpty()) {
                            etAno3.setText(ActivityHomeScreen.curriculo.getFormacoes().get(2).getAno());
                        }
                    }
                    if (ActivityHomeScreen.curriculo.getFormacoes().get(2).getGrau() != null &&
                            !ActivityHomeScreen.curriculo.getFormacoes().get(2).getGrau().isEmpty()) {
                        etGrau3.setText(ActivityHomeScreen.curriculo.getFormacoes().get(2).getGrau());
                    }
                    if (ActivityHomeScreen.curriculo.getFormacoes().get(2).getNomeInstituicao() != null &&
                            !ActivityHomeScreen.curriculo.getFormacoes().get(2).getNomeInstituicao().isEmpty()) {
                        etNomeInstituicao3.setText(ActivityHomeScreen.curriculo.getFormacoes().get(2).getNomeInstituicao());
                    }
                    if (ActivityHomeScreen.curriculo.getFormacoes().get(2).getCidade() != null &&
                            !ActivityHomeScreen.curriculo.getFormacoes().get(2).getCidade().isEmpty()) {
                        etCidadeFormacao3.setText(ActivityHomeScreen.curriculo.getFormacoes().get(2).getCidade());
                    }
                    if (ActivityHomeScreen.curriculo.getFormacoes().get(2).getInformacoesRelevantes() != null &&
                            !ActivityHomeScreen.curriculo.getFormacoes().get(2).getInformacoesRelevantes().isEmpty()) {
                        etInformacoesRelevantes3.setText(ActivityHomeScreen.curriculo.getFormacoes().get(2).getInformacoesRelevantes());
                    }
                }
                else {
                    ActivityHomeScreen.curriculo.getFormacoes().add(new Educacao());
                }

                // Formação 4.
                if (indexExists(ActivityHomeScreen.curriculo.getFormacoes(), 3)) {
                    if (ActivityHomeScreen.curriculo.getFormacoes().get(3).isCursando()) {
                        cbCursando4.setChecked(true);
                        etAno4.setText("");
                        etAno4.setEnabled(false);
                    }
                    else {
                        cbCursando4.setChecked(false);
                        etAno4.setEnabled(true);
                        if (ActivityHomeScreen.curriculo.getFormacoes().get(3).getAno() != null &&
                                !ActivityHomeScreen.curriculo.getFormacoes().get(3).getAno().isEmpty()) {
                            etAno4.setText(ActivityHomeScreen.curriculo.getFormacoes().get(3).getAno());
                        }
                    }
                    if (ActivityHomeScreen.curriculo.getFormacoes().get(3).getGrau() != null &&
                            !ActivityHomeScreen.curriculo.getFormacoes().get(3).getGrau().isEmpty()) {
                        etGrau4.setText(ActivityHomeScreen.curriculo.getFormacoes().get(3).getGrau());
                    }
                    if (ActivityHomeScreen.curriculo.getFormacoes().get(3).getNomeInstituicao() != null &&
                            !ActivityHomeScreen.curriculo.getFormacoes().get(3).getNomeInstituicao().isEmpty()) {
                        etNomeInstituicao4.setText(ActivityHomeScreen.curriculo.getFormacoes().get(3).getNomeInstituicao());
                    }
                    if (ActivityHomeScreen.curriculo.getFormacoes().get(3).getCidade() != null &&
                            !ActivityHomeScreen.curriculo.getFormacoes().get(3).getCidade().isEmpty()) {
                        etCidadeFormacao4.setText(ActivityHomeScreen.curriculo.getFormacoes().get(3).getCidade());
                    }
                    if (ActivityHomeScreen.curriculo.getFormacoes().get(3).getInformacoesRelevantes() != null &&
                            !ActivityHomeScreen.curriculo.getFormacoes().get(3).getInformacoesRelevantes().isEmpty()) {
                        etInformacoesRelevantes4.setText(ActivityHomeScreen.curriculo.getFormacoes().get(3).getInformacoesRelevantes());
                    }
                }
                else {
                    ActivityHomeScreen.curriculo.getFormacoes().add(new Educacao());
                }
            }
        }
        else {
            Toast.makeText(getActivity(), "Error loading data.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
    }

    public void limparComponentes() {
    // Formação.
        etGrau1.setText("");
        etNomeInstituicao1.setText("");
        etCidadeFormacao1.setText("");
        cbCursando1.setChecked(false);
        if (!etAno1.getText().toString().isEmpty()) {
            etAno1.setText("");
        }
        etAno1.setEnabled(true);
        etInformacoesRelevantes1.setText("");
        etGrau2.setText("");
        etNomeInstituicao2.setText("");
        etCidadeFormacao2.setText("");
        cbCursando2.setChecked(false);
        if (!etAno2.getText().toString().isEmpty()) {
            etAno2.setText("");
        }
        etAno2.setEnabled(true);
        etInformacoesRelevantes2.setText("");

        if (ActivityHomeScreen.aplicativoComprado) {
            etGrau3.setText("");
            etNomeInstituicao3.setText("");
            etCidadeFormacao3.setText("");
            cbCursando3.setChecked(false);
            if (!etAno3.getText().toString().isEmpty()) {
                etAno3.setText("");
            }
            etAno3.setEnabled(true);
            etInformacoesRelevantes3.setText("");
            etGrau4.setText("");
            etNomeInstituicao4.setText("");
            etCidadeFormacao4.setText("");
            cbCursando4.setChecked(false);
            if (!etAno4.getText().toString().isEmpty()) {
                etAno4.setText("");
            }
            etAno4.setEnabled(true);
            etInformacoesRelevantes4.setText("");
        }
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
        ActivityHomeScreen.salvarDadosEducacao(getActivity());
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
