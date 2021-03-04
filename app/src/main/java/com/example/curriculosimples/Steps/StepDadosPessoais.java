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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.curriculosimples.ActivityComprarVersaoPRO;
import com.example.curriculosimples.ActivityHomeScreen;
import com.example.curriculosimples.MaskWatcher;
import com.example.curriculosimples.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;

public class StepDadosPessoais extends Fragment implements Step, BlockingStep, RewardedVideoAdListener {

    // Dados Pessoais.
    EditText etNome;
    EditText etSobrenome;
    EditText etEstadoCivil;
    EditText etDataNascimento;
    EditText etEndereco;
    EditText etCidade;
    EditText etEstado;
    EditText etCEP;
    EditText etEmail;
    EditText etTelefone;
    CircleImageView civFoto;
    Button btnSelecionarFoto;
    TextView tvFoto;
    ProgressBar pbCarregarVideoPremiado;
    TextView tvPreencherDadosObrigatoriosDadosPessoais;

    // Variáveis.
    boolean premioSelecionarFoto = false;

    // Anúncios.
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout dadosPessoaisContent = (RelativeLayout) inflater.inflate(R.layout.step_dados_pessoais_layout, container, false);

        etNome = (EditText) dadosPessoaisContent.findViewById(R.id.etNome);
        etSobrenome = (EditText) dadosPessoaisContent.findViewById(R.id.etSobrenome);
        etEstadoCivil = (EditText) dadosPessoaisContent.findViewById(R.id.etEstadoCivil);
        etDataNascimento = (EditText) dadosPessoaisContent.findViewById(R.id.etDataNascimento);
        etEndereco = (EditText) dadosPessoaisContent.findViewById(R.id.etEndereco);
        etCidade = (EditText) dadosPessoaisContent.findViewById(R.id.etCidade);
        etEstado = (EditText) dadosPessoaisContent.findViewById(R.id.etEstado);
        etCEP = (EditText) dadosPessoaisContent.findViewById(R.id.etCEP);
        etEmail = (EditText) dadosPessoaisContent.findViewById(R.id.etEmail);
        etTelefone = (EditText) dadosPessoaisContent.findViewById(R.id.etTelefone);
        civFoto = (CircleImageView) dadosPessoaisContent.findViewById(R.id.civFoto);
        btnSelecionarFoto = (Button) dadosPessoaisContent.findViewById(R.id.btnSelecionarFoto);
        tvFoto = (TextView) dadosPessoaisContent.findViewById(R.id.tvFoto);
        pbCarregarVideoPremiado = (ProgressBar) dadosPessoaisContent.findViewById(R.id.pbCarregarVideoPremiado);
        tvPreencherDadosObrigatoriosDadosPessoais = (TextView) dadosPessoaisContent.findViewById(R.id.tvPreencherDadosObrigatoriosDadosPessoais);

        etDataNascimento.addTextChangedListener(new MaskWatcher("##/##/####"));

        if (ActivityHomeScreen.aplicativoComprado) {
            tvFoto.setText(getResources().getString(R.string.tvFoto));
        }

        etNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                verificarStepDadosPessoaisCompleto();
                ActivityHomeScreen.curriculo.getDadosBasicos().setNome(editable.toString());
            }
        });

        etSobrenome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                verificarStepDadosPessoaisCompleto();
                ActivityHomeScreen.curriculo.getDadosBasicos().setSobrenome(editable.toString());
            }
        });

        etEstadoCivil.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable editable) {
                verificarStepDadosPessoaisCompleto();
                ActivityHomeScreen.curriculo.getDadosBasicos().setEstadoCivil(editable.toString());
            }
        });

        etDataNascimento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                verificarStepDadosPessoaisCompleto();
                ActivityHomeScreen.curriculo.getDadosBasicos().setDataNascimento(editable.toString());
            }
        });

        etEndereco.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                verificarStepDadosPessoaisCompleto();
                ActivityHomeScreen.curriculo.getDadosBasicos().setEndereco(editable.toString());
            }
        });

        etCidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                verificarStepDadosPessoaisCompleto();
                ActivityHomeScreen.curriculo.getDadosBasicos().setCidade(editable.toString());
            }
        });

        etEstado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                verificarStepDadosPessoaisCompleto();
                ActivityHomeScreen.curriculo.getDadosBasicos().setEstado(editable.toString());
            }
        });

        etCEP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                verificarStepDadosPessoaisCompleto();
                ActivityHomeScreen.curriculo.getDadosBasicos().setCEP(editable.toString());
            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                verificarStepDadosPessoaisCompleto();
                ActivityHomeScreen.curriculo.getDadosBasicos().setEmail(editable.toString());
            }
        });

        etTelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                verificarStepDadosPessoaisCompleto();
                ActivityHomeScreen.curriculo.getDadosBasicos().setTelefone(editable.toString());
            }
        });

        civFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityHomeScreen.aplicativoComprado) {
                    Intent intent = new Intent(view.getContext(), AlbumSelectActivity.class);
                    intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                    startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);
                }
                else {
                    AlertDialog builder = new AlertDialog.Builder(view.getContext()).create();

                    builder.setTitle(getResources().getString(R.string.app_name));
                    builder.setMessage(getResources().getString(R.string.msgFuncaoVersaoPRO));

                    builder.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.msgAssistirVideoPremiado),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (isNetworkAvailable()) {
                                        mostrarVideoPremiado();
                                    }
                                    else {
                                        Toast.makeText(getActivity(), getResources().getString(R.string.msgSemConexaoInternet),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                    builder.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.msgComprarVersaoPRO),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getActivity(), ActivityComprarVersaoPRO.class);
                                    startActivity(intent);
                                }
                            });

                    builder.show();
                }
            }
        });

        btnSelecionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityHomeScreen.aplicativoComprado) {
                    if (btnSelecionarFoto.getText().toString().equals(getResources().getString(R.string.btnSelecionarFoto))) { // Selecionar Foto.
                        Intent intent = new Intent(view.getContext(), AlbumSelectActivity.class);
                        intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                        startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);
                    }
                    else if (btnSelecionarFoto.getText().toString().equals(getResources().getString(R.string.btnRemoverFoto))) { // Remover a foto.
                        ActivityHomeScreen.curriculo.getDadosBasicos().setFoto("");
                        civFoto.setImageResource(R.drawable.adicionar);
                        btnSelecionarFoto.setText(getResources().getString(R.string.btnSelecionarFoto));
                    }
                }
                else {
                    if (btnSelecionarFoto.getText().toString().equals(getResources().getString(R.string.btnRemoverFoto))) { // Remover a foto.
                        ActivityHomeScreen.curriculo.getDadosBasicos().setFoto("");
                        civFoto.setImageResource(R.drawable.adicionar);
                        btnSelecionarFoto.setText(getResources().getString(R.string.btnSelecionarFoto));
                    }
                    else { // Mostrar mensagem ao usuário.
                        AlertDialog builder = new AlertDialog.Builder(view.getContext()).create();

                        builder.setTitle(getResources().getString(R.string.app_name));
                        builder.setMessage(getResources().getString(R.string.msgFuncaoVersaoPRO));

                        builder.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.msgAssistirVideoPremiado),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (isNetworkAvailable()) {
                                            mostrarVideoPremiado();
                                        }
                                        else {
                                            Toast.makeText(getActivity(), getResources().getString(R.string.msgSemConexaoInternet),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                        builder.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.msgComprarVersaoPRO),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getActivity(), ActivityComprarVersaoPRO.class);
                                        startActivity(intent);
                                    }
                                });

                        builder.show();
                    }
                }
            }
        });

        consultarDados();

        // Carregar anúncio premiado.
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getActivity().getApplicationContext());
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        return dadosPessoaisContent;
    }

    private void mostrarVideoPremiado() {
        pbCarregarVideoPremiado.setVisibility(View.VISIBLE);

        mRewardedVideoAd.loadAd("adRewardedInterstitial",
                new AdRequest.Builder().build());

        Toast.makeText(getActivity(), getResources().getString(R.string.msgCarregandoVideoPremiado), Toast.LENGTH_SHORT).show();
    }

    private boolean verificarStepDadosPessoaisCompleto() {
        boolean completo = false;

        if (!etNome.getText().toString().isEmpty() && !etSobrenome.getText().toString().isEmpty() && !etDataNascimento.getText().toString().isEmpty() &&
                !etCidade.getText().toString().isEmpty() && !etEstado.getText().toString().isEmpty() && !etEmail.getText().toString().isEmpty() &&
                !etTelefone.getText().toString().isEmpty()) {
            completo = true;
            tvPreencherDadosObrigatoriosDadosPessoais.setVisibility(View.GONE);
        }
        else {
            completo = false;
            tvPreencherDadosObrigatoriosDadosPessoais.setVisibility(View.VISIBLE);
        }

        return completo;
    }

    private void consultarDados() {
        if (ActivityHomeScreen.consultarDados()) {
            // Dados Pessoais.
            etNome.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getNome());
            etSobrenome.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getSobrenome());

            if (ActivityHomeScreen.curriculo.getDadosBasicos().getEstadoCivil() !=  null &&
                    !ActivityHomeScreen.curriculo.getDadosBasicos().getEstadoCivil().isEmpty()) {
                etEstadoCivil.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getEstadoCivil());
            }

            etDataNascimento.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getDataNascimento());

            if (ActivityHomeScreen.curriculo.getDadosBasicos().getEndereco() != null &&
                    !ActivityHomeScreen.curriculo.getDadosBasicos().getEndereco().isEmpty()) {
                etEndereco.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getEndereco());
            }

            etCidade.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getCidade());
            etEstado.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getEstado());
            etEmail.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getEmail());
            etTelefone.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getTelefone());

            if (ActivityHomeScreen.curriculo.getDadosBasicos().getCEP() != null &&
                    !ActivityHomeScreen.curriculo.getDadosBasicos().getCEP().isEmpty()) {
                etCEP.setText(ActivityHomeScreen.curriculo.getDadosBasicos().getCEP());
            }

            if (ActivityHomeScreen.curriculo.getDadosBasicos().getFoto() != null &&
                    !ActivityHomeScreen.curriculo.getDadosBasicos().getFoto().isEmpty()) {
                File file = new File(ActivityHomeScreen.curriculo.getDadosBasicos().getFoto());
                if(file.exists()) {
                    Uri uri = Uri.fromFile(new File(ActivityHomeScreen.curriculo.getDadosBasicos().getFoto()));
                    civFoto.setImageURI(uri);
                    btnSelecionarFoto.setText(getResources().getString(R.string.btnRemoverFoto));
                }
            }
        }
        else {
            Toast.makeText(getActivity(), "Error loading data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void limparComponentes() {
        etNome.setText("");
        etSobrenome.setText("");
        etEstadoCivil.setText("");
        if (!etDataNascimento.getText().toString().isEmpty()) {
            etDataNascimento.setText("");
        }
        etEndereco.setText("");
        etCidade.setText("");
        etEstado.setText("");
        etCEP.setText("");
        etEmail.setText("");
        etTelefone.setText("");

        civFoto.setImageResource(R.drawable.adicionar);
        btnSelecionarFoto.setText(getResources().getString(R.string.btnSelecionarFoto));
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        if (verificarStepDadosPessoaisCompleto()) {
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
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) { }

    public void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e) { }
    }

    /**
     * Activity on result para atualizar os dados da lista.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            for (int i = 0; i < images.size(); i++) {
                Uri uri = Uri.fromFile(new File(images.get(i).path));
                civFoto.setImageURI(uri);
                ActivityHomeScreen.curriculo.getDadosBasicos().setFoto(uri.getPath());
                btnSelecionarFoto.setText(getResources().getString(R.string.btnRemoverFoto));
            }
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        removerPBCarregarVideoPremiado();
        mRewardedVideoAd.show();
    }

    @Override
    public void onRewardedVideoAdOpened() { }

    @Override
    public void onRewardedVideoStarted() { }

    @Override
    public void onRewardedVideoAdClosed() {
        if (premioSelecionarFoto) {
            premioSelecionarFoto = false;

            // Selecionar a foto.
            Intent intent = new Intent(getContext(), AlbumSelectActivity.class);
            intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
            startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);
        }
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        premioSelecionarFoto = true;
    }

    @Override
    public void onRewardedVideoAdLeftApplication() { }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        removerPBCarregarVideoPremiado();
        Toast.makeText(getActivity(), "Error loading ad.", Toast.LENGTH_SHORT).show();
    }

    private void removerPBCarregarVideoPremiado() {
        if (pbCarregarVideoPremiado.getVisibility() == View.VISIBLE) {
            pbCarregarVideoPremiado.setVisibility(View.GONE);
        }
    }
}
