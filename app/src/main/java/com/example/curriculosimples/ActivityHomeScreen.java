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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;
import com.example.curriculosimples.Steps.StepDadosPessoais;
import com.example.curriculosimples.Steps.StepEducacao;
import com.example.curriculosimples.Steps.StepExperienciaProfissional;
import com.example.curriculosimples.Steps.StepHabilidadesCompetencias;
import com.example.curriculosimples.Steps.StepObjetivos;
import com.example.curriculosimples.Steps.StepOutrasInformacoes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Control.Curriculo;
import Control.DadosBasicos;
import Control.Educacao;
import Control.ExperienciaProfissional;
import Control.OutrasInformacoes;

public class ActivityHomeScreen extends AppCompatActivity implements StepperLayout.StepperListener {

    // Componentes.
    StepperLayout stepperLayout;

    // Variáveis.
    public static boolean aplicativoComprado;
    public static Curriculo curriculo;
    private static String[] stepsCurriculo;
    File file;
    File diretorio;
    private static boolean gerarArquivoCurriculo = false;
    private static Resources resources;
    private static StepDadosPessoais stepDadosPessoais;
    private static StepObjetivos stepObjetivos;
    private static StepHabilidadesCompetencias stepHabilidadesCompetencias;
    private static StepExperienciaProfissional stepExperienciaProfissional;
    private static StepEducacao stepEducacao;
    private static StepOutrasInformacoes stepOutrasInformacoes;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_WRITE_EXTERNAL_STORAGE = 1;
    Dialog dialog;

    // Anúncios.
    private AdView adView;
    private InterstitialAd mInterstitialAd;
    static AdRequest adRequest;

    // Dados/Preferências.
    private SharedPreferences preferencias;
    private static SharedPreferences preferenciasConsultarDados;
    private static SharedPreferences.Editor preferenciasEditor;
    private static final String PREFS_CURRICULO_SIMPLES = "AppPreferences";
    private static final String dadosBasicos = "DadosBasicos";
    private static final String dadosExperienciaProfissional = "ExperienciaProfissional";
    private static final String dadosEducacao = "Educacao";
    private static final String dadosOutrasInformacoes = "OutrasInformacoes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        curriculo = new Curriculo();

        // Finding the view
        stepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        adView = (AdView) findViewById(R.id.adView);

        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        stepsCurriculo = getResources().getStringArray(R.array.steps_array);
        resources = getResources();

        // Verificar permissões.
        checkAndRequestPermissions();

        // Verificar se o aplicativo foi comprado.
        verificarAplicativoComprado();

        //  Find the form view, set it up and initialize it.
        stepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this));
        stepperLayout.setListener(this);

        preferenciasConsultarDados = getSharedPreferences(PREFS_CURRICULO_SIMPLES, MODE_PRIVATE);
        preferenciasEditor = getSharedPreferences(PREFS_CURRICULO_SIMPLES, MODE_PRIVATE).edit();
    }

    public static class StepperAdapter extends AbstractFragmentStepAdapter {
        public StepperAdapter(FragmentManager fm, Context context) {
            super(fm, context);
        }

        @Override
        public Step createStep(int position) {
            switch (position) {
                case 0: // Dados Pessoais.
                    stepDadosPessoais = new StepDadosPessoais();
                    Bundle b1 = new Bundle();
                    b1.putInt("messageResourceId", 0);
                    stepDadosPessoais.setArguments(b1);
                    return stepDadosPessoais;
                case 1: // Objetivos.
                    stepObjetivos = new StepObjetivos();
                    Bundle b2 = new Bundle();
                    b2.putInt("Objetivos", 0);
                    stepObjetivos.setArguments(b2);
                    return stepObjetivos;
                case 2: // Habilidades e Competências.
                    stepHabilidadesCompetencias = new StepHabilidadesCompetencias();
                    Bundle b3 = new Bundle();
                    b3.putInt("HabilidadesCompetencias", 0);
                    stepHabilidadesCompetencias.setArguments(b3);
                    return stepHabilidadesCompetencias;
                case 3: // Experiência Profissional.
                    stepExperienciaProfissional = new StepExperienciaProfissional();
                    Bundle b4 = new Bundle();
                    b4.putInt("ExperienciaProfissional", 0);
                    stepExperienciaProfissional.setArguments(b4);
                    return stepExperienciaProfissional;
                case 4: // Educação.
                    stepEducacao = new StepEducacao();
                    Bundle b5 = new Bundle();
                    b5.putInt("Educacao", 0);
                    stepEducacao.setArguments(b5);
                    return stepEducacao;
                case 5: // Outras Informações.
                    stepOutrasInformacoes = new StepOutrasInformacoes();
                    Bundle b6 = new Bundle();
                    b6.putInt("OutrasInformacoes", 0);
                    stepOutrasInformacoes.setArguments(b6);
                    return stepOutrasInformacoes;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @NonNull
        @Override
        public StepViewModel getViewModel(@IntRange(from = 0) int position) {
            StepViewModel.Builder builder = new StepViewModel.Builder(context)
                    .setTitle("Teste");
            switch (position) {
                case 0:
                    builder
                            .setTitle(stepsCurriculo[position])
                            .setBackButtonLabel(resources.getString(R.string.btnVoltar))
                            .setEndButtonLabel(resources.getString(R.string.btnProximo));
                    break;
                case 1:
                    builder
                            .setTitle(stepsCurriculo[position])
                            .setBackButtonLabel(resources.getString(R.string.btnVoltar))
                            .setEndButtonLabel(resources.getString(R.string.btnProximo));
                    break;
                case 2:
                    builder
                            .setTitle(stepsCurriculo[position])
                            .setBackButtonLabel(resources.getString(R.string.btnVoltar))
                            .setEndButtonLabel(resources.getString(R.string.btnProximo));
                    break;
                case 3:
                    builder
                            .setTitle(stepsCurriculo[position])
                            .setBackButtonLabel(resources.getString(R.string.btnVoltar))
                            .setEndButtonLabel(resources.getString(R.string.btnProximo));
                    break;
                case 4:
                    builder
                            .setTitle(stepsCurriculo[position])
                            .setBackButtonLabel(resources.getString(R.string.btnVoltar))
                            .setEndButtonLabel(resources.getString(R.string.btnProximo));
                    break;
                case 5:
                    builder
                            .setTitle(stepsCurriculo[position])
                            .setBackButtonLabel(resources.getString(R.string.btnVoltar))
                            .setEndButtonLabel(resources.getString(R.string.btnFinalizar));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported position: " + position);
            }
            return builder.create();
        }
    }

    @Override
    public void onCompleted(View completeButton) {
        try {
            // Salvar os dados.
            salvarDadosBasicos(getApplicationContext());
            salvarDadosExperienciaProfissional(getApplicationContext());
            salvarDadosEducacao(getApplicationContext());
            salvarDadosOutrasInformacoes(getApplicationContext());

            // Verificar se a permissão já foi concedida.
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // A Permissão Write External Storage já foi concedida.
                    exportarPDF();
                }
                else {
                    gerarArquivoCurriculo = true;

                    // Solicitar as permissões necessárias.
                    checkAndRequestPermissions();
                }
            }
            else {
                exportarPDF();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(VerificationError verificationError) { }

    @Override
    public void onStepSelected(int newStepPosition) { }

    @Override
    public void onReturn() { }

    private void exportarPDF() throws IOException {
        // Gerar o arquivo PDF do currículo no diretório "/sdcard/Documents".
        String trechoErro = ""; // Variável para identificar onde o erro aconteceu.
        trechoErro = "Criar Diretório e Arquivo.";

        // Obter o primeiro diretório selecionado.
        //String dir = files[0];
        FileOutputStream fileOut = null;

        // Definir o nome do arquivo.
        String fileName = getResources().getString(R.string.nomeArquivo) + "_" +
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
                + ".pdf";

        // Verificar se o diretório existe para salvar o arquivo.
        String folder = Environment.getExternalStorageDirectory() + "/Documents";

        diretorio = new File(folder);

        // Criar o diretório se ele não existir.
        if (!diretorio.isDirectory()) {
            diretorio.mkdirs();
        }

        // Criar o arquivo PDF no diretório Documents.
        file = new File(diretorio, fileName);

        try {
            fileOut = new FileOutputStream(file);

            Document document = new Document();
            Paragraph paragraph;
            LineSeparator lineSeparator;
            com.itextpdf.text.Image image;
            Font fontNome = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.NORMAL, BaseColor.DARK_GRAY);
            Font fontSobrenome = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD, new BaseColor(38, 50, 56));
            Font fontNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font fontData = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.DARK_GRAY);
            Font fontCabecalho = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, new BaseColor(38, 50, 56));
            Font fontContato = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(2, 119, 189));
            Font fontCargoTitulo = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(2, 119, 189));
            Font fontEmpresaInstituicao = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.DARK_GRAY);
            Font fontHabilidade = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(2, 119, 189));

            PdfWriter writer = PdfWriter.getInstance(document, fileOut);

            document.open();

            document.add(new Paragraph(Chunk.NEWLINE));

            trechoErro = "Dados Pessoais.";

            // Criar o conteúdo do PDF.
            // Dados Pessoais.
            if (curriculo.getDadosBasicos().getFoto() != null && !curriculo.getDadosBasicos().getFoto().isEmpty()) {
                File file = new File(curriculo.getDadosBasicos().getFoto());

                if (file.exists()) {
                    PdfPTable table;
                    PdfPCell cell;
                    Phrase phrase;
                    Chunk chunk;

                    table = new PdfPTable(2);
                    table.setWidthPercentage(100);
                    table.setWidths(new float[] { 85, 15 });
                    table.getDefaultCell().setBorder(0);

                    // Adicionar os dados.
                    phrase = new Phrase();

                    Chunk nome = new Chunk(curriculo.getDadosBasicos().getNome().toUpperCase().replaceAll("\\s+$", "") + " ", fontNome);
                    Chunk sobrenome = new Chunk(curriculo.getDadosBasicos().getSobrenome().toUpperCase().replaceAll("\\s+$", ""), fontSobrenome);

                    paragraph = new Paragraph();
                    paragraph.add(nome);
                    paragraph.add(sobrenome);
                    phrase.add(paragraph);

                    paragraph = new Paragraph(Chunk.NEWLINE + getResources().getString(R.string.msgDataNascimento) + " " +
                            curriculo.getDadosBasicos().getDataNascimento().replaceAll("\\s+$", ""), fontNormal);
                    phrase.add(paragraph);

                    // Adicionar o estado civil.
                    if (curriculo.getDadosBasicos().getEstadoCivil() != null && !curriculo.getDadosBasicos().getEstadoCivil().isEmpty()) {
                        paragraph = new Paragraph(Chunk.NEWLINE + getResources().getString(R.string.tvEstadoCivil) + " " +
                                curriculo.getDadosBasicos().getEstadoCivil(), fontNormal);
                        phrase.add(paragraph);
                    }

                    String endereco = getResources().getString(R.string.msgEndereco) + " ";

                    if (curriculo.getDadosBasicos().getEndereco() != null && !curriculo.getDadosBasicos().getEndereco().isEmpty()) {
                        endereco += curriculo.getDadosBasicos().getEndereco().replaceAll("\\s+$", "") + ", ";
                    }

                    endereco += curriculo.getDadosBasicos().getCidade().replaceAll("\\s+$", "") + ", " +
                            curriculo.getDadosBasicos().getEstado().replaceAll("\\s+$", "");

                    if (curriculo.getDadosBasicos().getCEP() != null && !curriculo.getDadosBasicos().getCEP().isEmpty()) {
                        endereco += ", " + curriculo.getDadosBasicos().getCEP().replaceAll("\\s+$", "");
                    }

                    paragraph = new Paragraph(Chunk.NEWLINE + endereco, fontNormal);
                    phrase.add(paragraph);

                    String contato = getResources().getString(R.string.msgEmail) + " " + curriculo.getDadosBasicos().getEmail().replaceAll("\\s+$", "")
                            + " | " + getResources().getString(R.string.msgTelefone) + " " + curriculo.getDadosBasicos().getTelefone().replaceAll("\\s+$", "");

                    paragraph = new Paragraph(Chunk.NEWLINE + contato, fontContato);
                    phrase.add(paragraph);

                    cell = new PdfPCell();
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    cell.addElement(phrase);

                    table.addCell(cell);

                    // Adicionar a foto.
                    phrase = new Phrase();

                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 80, 80,true);
                    Bitmap bitmapRoundImage = getCircularBitmap(getResizedBitmap(bitmap, 80, 80));
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmapRoundImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    image = com.itextpdf.text.Image.getInstance(stream.toByteArray());

                    cell = new PdfPCell(image, true);
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);

                    table.addCell(cell);

                    table.completeRow();
                    document.add(table);
                }
                else { // Adicionar os dados do curriculo sem a foto.
                    Chunk nome = new Chunk(curriculo.getDadosBasicos().getNome().toUpperCase().replaceAll("\\s+$", "") + " ", fontNome);
                    Chunk sobrenome = new Chunk(curriculo.getDadosBasicos().getSobrenome().toUpperCase().replaceAll("\\s+$", ""), fontSobrenome);

                    paragraph = new Paragraph();
                    paragraph.add(nome);
                    paragraph.add(sobrenome);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    document.add(paragraph);

                    Chunk dataNascimento = new Chunk(getResources().getString(R.string.msgDataNascimento) + " " +
                            curriculo.getDadosBasicos().getDataNascimento().replaceAll("\\s+$", ""), fontNormal);

                    paragraph = new Paragraph();
                    paragraph.add(dataNascimento);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    document.add(paragraph);

                    // Adicionar o estado civil.
                    if (curriculo.getDadosBasicos().getEstadoCivil() != null && !curriculo.getDadosBasicos().getEstadoCivil().isEmpty()) {
                        Chunk estadoCivil = new Chunk(getResources().getString(R.string.tvEstadoCivil) + " " +
                                curriculo.getDadosBasicos().getEstadoCivil(), fontNormal);

                        paragraph = new Paragraph();
                        paragraph.add(estadoCivil);
                        paragraph.setAlignment(Element.ALIGN_CENTER);
                        document.add(paragraph);
                    }

                    String endereco = getResources().getString(R.string.msgEndereco) + " ";

                    if (curriculo.getDadosBasicos().getEndereco() != null && !curriculo.getDadosBasicos().getEndereco().isEmpty()) {
                        endereco += curriculo.getDadosBasicos().getEndereco().replaceAll("\\s+$", "") + ", ";
                    }

                    endereco += curriculo.getDadosBasicos().getCidade().replaceAll("\\s+$", "") + ", " +
                            curriculo.getDadosBasicos().getEstado().replaceAll("\\s+$", "");

                    if (curriculo.getDadosBasicos().getCEP() != null && !curriculo.getDadosBasicos().getCEP().isEmpty()) {
                        endereco += ", " + curriculo.getDadosBasicos().getCEP().replaceAll("\\s+$", "");
                    }

                    Chunk chunkEndereco = new Chunk(endereco, fontNormal);

                    paragraph = new Paragraph();
                    paragraph.add(chunkEndereco);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    document.add(paragraph);

                    String contato = getResources().getString(R.string.msgEmail) + " " + curriculo.getDadosBasicos().getEmail().replaceAll("\\s+$", "")
                            + " | " + getResources().getString(R.string.msgTelefone) + " " + curriculo.getDadosBasicos().getTelefone().replaceAll("\\s+$", "");

                    Chunk chunkContato = new Chunk(contato, fontContato);

                    paragraph = new Paragraph();
                    paragraph.add(chunkContato);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    document.add(paragraph);
                }
            }
            else {
                Chunk nome = new Chunk(curriculo.getDadosBasicos().getNome().toUpperCase().replaceAll("\\s+$", "") + " ", fontNome);
                Chunk sobrenome = new Chunk(curriculo.getDadosBasicos().getSobrenome().toUpperCase().replaceAll("\\s+$", ""), fontSobrenome);

                paragraph = new Paragraph();
                paragraph.add(nome);
                paragraph.add(sobrenome);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);

                Chunk dataNascimento = new Chunk(getResources().getString(R.string.msgDataNascimento) + " " +
                        curriculo.getDadosBasicos().getDataNascimento().replaceAll("\\s+$", ""), fontNormal);

                paragraph = new Paragraph();
                paragraph.add(dataNascimento);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);

                // Adicionar o estado civil.
                if (curriculo.getDadosBasicos().getEstadoCivil() != null && !curriculo.getDadosBasicos().getEstadoCivil().isEmpty()) {
                    Chunk estadoCivil = new Chunk(getResources().getString(R.string.tvEstadoCivil) + " " +
                            curriculo.getDadosBasicos().getEstadoCivil(), fontNormal);

                    paragraph = new Paragraph();
                    paragraph.add(estadoCivil);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    document.add(paragraph);
                }

                String endereco = getResources().getString(R.string.msgEndereco) + " ";

                if (curriculo.getDadosBasicos().getEndereco() != null && !curriculo.getDadosBasicos().getEndereco().isEmpty()) {
                    endereco += curriculo.getDadosBasicos().getEndereco().replaceAll("\\s+$", "") + ", ";
                }

                endereco += curriculo.getDadosBasicos().getCidade().replaceAll("\\s+$", "") + ", " +
                        curriculo.getDadosBasicos().getEstado().replaceAll("\\s+$", "");

                if (curriculo.getDadosBasicos().getCEP() != null && !curriculo.getDadosBasicos().getCEP().isEmpty()) {
                    endereco += ", " + curriculo.getDadosBasicos().getCEP().replaceAll("\\s+$", "");
                }

                Chunk chunkEndereco = new Chunk(endereco, fontNormal);

                paragraph = new Paragraph();
                paragraph.add(chunkEndereco);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);

                String contato = getResources().getString(R.string.msgEmail) + " " + curriculo.getDadosBasicos().getEmail().replaceAll("\\s+$", "")
                        + " | " + getResources().getString(R.string.msgTelefone) + " " + curriculo.getDadosBasicos().getTelefone().replaceAll("\\s+$", "");

                Chunk chunkContato = new Chunk(contato, fontContato);

                paragraph = new Paragraph();
                paragraph.add(chunkContato);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);
            }

            document.add(new Paragraph(Chunk.NEWLINE));

            lineSeparator = new LineSeparator();
            lineSeparator.setPercentage(100);
            lineSeparator.setLineColor(new BaseColor(55, 71, 79));
            document.add(lineSeparator);

            document.add(new Paragraph(Chunk.NEWLINE));

            // -------------------------------------------------------------------------------------

            trechoErro = "Objetivo.";

            // Objetivo.
            if (!curriculo.getDadosBasicos().getObjetivo().isEmpty()) {
                document.add(new Paragraph(new Chunk(getResources().getString(R.string.msgObjetivo) + Chunk.NEWLINE, fontCabecalho)));
                document.add(new Paragraph());

                Chunk chunkObjetivo = new Chunk(curriculo.getDadosBasicos().getObjetivo().replaceAll("\\s+$", ""), fontNormal);

                paragraph = new Paragraph();
                paragraph.add(chunkObjetivo);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);

                document.add(paragraph);
            }

            // -------------------------------------------------------------------------------------

            trechoErro = "Habilidades e Competências.";

            // Habilidades e Competências.
            if ((curriculo.getDadosBasicos().getHabilidadesCompetencias().isHabilidadeGerencia() && !curriculo.getDadosBasicos().getHabilidadesCompetencias().getGerencia().isEmpty())
                    || (curriculo.getDadosBasicos().getHabilidadesCompetencias().isHabilidadeLideranca() && !curriculo.getDadosBasicos().getHabilidadesCompetencias().getLideranca().isEmpty())
                    || (curriculo.getDadosBasicos().getHabilidadesCompetencias().isHabilidadeVendas() && !curriculo.getDadosBasicos().getHabilidadesCompetencias().getVendas().isEmpty())
                    || (curriculo.getDadosBasicos().getHabilidadesCompetencias().isHabilidadeComunicacao() && !curriculo.getDadosBasicos().getHabilidadesCompetencias().getComunicacao().isEmpty())) {
                document.add(new Paragraph(new Chunk(Chunk.NEWLINE +getResources().getString(R.string.msgHabilidadesCompetencias) + Chunk.NEWLINE, fontCabecalho)));
                document.add(new Paragraph());

                if (curriculo.getDadosBasicos().getHabilidadesCompetencias().isHabilidadeGerencia()) {
                    if (!curriculo.getDadosBasicos().getHabilidadesCompetencias().getGerencia().isEmpty()) {
                        document.add(new Paragraph(new Chunk(getResources().getString(R.string.cbGerencia), fontHabilidade)));
                        document.add(new Paragraph(new Chunk(curriculo.getDadosBasicos().getHabilidadesCompetencias().getGerencia().replaceAll("\\s+$", "") + Chunk.NEWLINE, fontNormal)));
                    }
                }
                if (curriculo.getDadosBasicos().getHabilidadesCompetencias().isHabilidadeLideranca()) {
                    if (!curriculo.getDadosBasicos().getHabilidadesCompetencias().getLideranca().isEmpty()) {
                        document.add(new Paragraph(new Chunk(getResources().getString(R.string.cbLideranca), fontHabilidade)));
                        document.add(new Paragraph(new Chunk(curriculo.getDadosBasicos().getHabilidadesCompetencias().getLideranca().replaceAll("\\s+$", "") + Chunk.NEWLINE, fontNormal)));
                    }
                }
                if (curriculo.getDadosBasicos().getHabilidadesCompetencias().isHabilidadeVendas()) {
                    if (!curriculo.getDadosBasicos().getHabilidadesCompetencias().getVendas().isEmpty()) {
                        document.add(new Paragraph(new Chunk(getResources().getString(R.string.cbVendas), fontHabilidade)));
                        document.add(new Paragraph(new Chunk(curriculo.getDadosBasicos().getHabilidadesCompetencias().getVendas().replaceAll("\\s+$", "") + Chunk.NEWLINE, fontNormal)));
                    }
                }
                if (curriculo.getDadosBasicos().getHabilidadesCompetencias().isHabilidadeComunicacao()) {
                    if (!curriculo.getDadosBasicos().getHabilidadesCompetencias().getComunicacao().isEmpty()) {
                        document.add(new Paragraph(new Chunk(getResources().getString(R.string.cbComunicacao), fontHabilidade)));
                        document.add(new Paragraph(new Chunk(curriculo.getDadosBasicos().getHabilidadesCompetencias().getComunicacao().replaceAll("\\s+$", ""), fontNormal)));
                    }
                }
            }

            // -------------------------------------------------------------------------------------

            trechoErro = "Experiência Profissional.";

            // Histórico de Trabalho.
            if (verificarHistoricoTrabalhoCompleto(0) || verificarHistoricoTrabalhoCompleto(1)) {
                document.add(new Paragraph(new Chunk(Chunk.NEWLINE + getResources().getString(R.string.msgHistoricoTrabalho) + Chunk.NEWLINE, fontCabecalho)));
                document.add(new Paragraph());

                if (verificarHistoricoTrabalhoCompleto(0)) { // Trabalho 1.
                    Chunk chunkData;

                    if (curriculo.getHistoricosTrabalho().get(0).getDataTermino() != null && !curriculo.getHistoricosTrabalho().get(0).getDataTermino().isEmpty()) {
                        chunkData = new Chunk(curriculo.getHistoricosTrabalho().get(0).getDataInicio().replaceAll("\\s+$", "")
                                + " - " + curriculo.getHistoricosTrabalho().get(0).getDataTermino().replaceAll("\\s+$", ""), fontData);
                    }
                    else {
                        chunkData = new Chunk(curriculo.getHistoricosTrabalho().get(0).getDataInicio().replaceAll("\\s+$", "")
                                + " - " + getResources().getString(R.string.msgTrabalhoAtual), fontData);
                    }

                    paragraph = new Paragraph();
                    paragraph.add(chunkData);
                    document.add(paragraph);

                    Chunk chunkCargo = new Chunk(curriculo.getHistoricosTrabalho().get(0).getCargo().replaceAll("\\s+$", "").toUpperCase() + ", ", fontCargoTitulo);
                    Chunk chunkEmpresaDados = new Chunk(curriculo.getHistoricosTrabalho().get(0).getEmpresa().replaceAll("\\s+$", ""), fontEmpresaInstituicao);

                    paragraph = new Paragraph();
                    paragraph.add(chunkCargo);
                    paragraph.add(chunkEmpresaDados);
                    document.add(paragraph);

                    if (!curriculo.getHistoricosTrabalho().get(0).getResponsabilidades().isEmpty()) {
                        Chunk chunkResponsabilidades = new Chunk(curriculo.getHistoricosTrabalho().get(0).getResponsabilidades(), fontNormal);

                        paragraph = new Paragraph();
                        paragraph.add(chunkResponsabilidades);
                        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                        document.add(paragraph);
                    }
                }

                if (verificarHistoricoTrabalhoCompleto(1)) { // Trabalho 2.
                    document.add(new Paragraph(Chunk.NEWLINE));
                    Chunk chunkData;

                    if (curriculo.getHistoricosTrabalho().get(1).getDataTermino() != null && !curriculo.getHistoricosTrabalho().get(1).getDataTermino().isEmpty()) {
                        chunkData = new Chunk(curriculo.getHistoricosTrabalho().get(1).getDataInicio().replaceAll("\\s+$", "")
                                + " - " + curriculo.getHistoricosTrabalho().get(1).getDataTermino().replaceAll("\\s+$", ""), fontData);
                    }
                    else {
                        chunkData = new Chunk(curriculo.getHistoricosTrabalho().get(1).getDataInicio().replaceAll("\\s+$", "")
                                + " - " + getResources().getString(R.string.msgTrabalhoAtual), fontData);
                    }

                    paragraph = new Paragraph();
                    paragraph.add(chunkData);
                    document.add(paragraph);

                    Chunk chunkCargo = new Chunk(curriculo.getHistoricosTrabalho().get(1).getCargo().replaceAll("\\s+$", "").toUpperCase() + ", ", fontCargoTitulo);
                    Chunk chunkEmpresaDados = new Chunk(curriculo.getHistoricosTrabalho().get(1).getEmpresa().replaceAll("\\s+$", ""), fontEmpresaInstituicao);

                    paragraph = new Paragraph();
                    paragraph.add(chunkCargo);
                    paragraph.add(chunkEmpresaDados);
                    document.add(paragraph);

                    if (!curriculo.getHistoricosTrabalho().get(1).getResponsabilidades().isEmpty()) {
                        Chunk chunkResponsabilidades = new Chunk(curriculo.getHistoricosTrabalho().get(1).getResponsabilidades(), fontNormal);

                        paragraph = new Paragraph();
                        paragraph.add(chunkResponsabilidades);
                        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                        document.add(paragraph);
                    }
                }

                if (aplicativoComprado) {
                    if (indexExists(curriculo.getHistoricosTrabalho(), 2)) {
                        if (verificarHistoricoTrabalhoCompleto(2)) { // Trabalho 3.
                            document.add(new Paragraph(Chunk.NEWLINE));
                            Chunk chunkData;

                            if (curriculo.getHistoricosTrabalho().get(2).getDataTermino() != null && !curriculo.getHistoricosTrabalho().get(2).getDataTermino().isEmpty()) {
                                chunkData = new Chunk(curriculo.getHistoricosTrabalho().get(2).getDataInicio().replaceAll("\\s+$", "")
                                        + " - " + curriculo.getHistoricosTrabalho().get(2).getDataTermino().replaceAll("\\s+$", ""), fontData);
                            }
                            else {
                                chunkData = new Chunk(curriculo.getHistoricosTrabalho().get(2).getDataInicio().replaceAll("\\s+$", "")
                                        + " - " + getResources().getString(R.string.msgTrabalhoAtual), fontData);
                            }

                            paragraph = new Paragraph();
                            paragraph.add(chunkData);
                            document.add(paragraph);

                            Chunk chunkCargo = new Chunk(curriculo.getHistoricosTrabalho().get(2).getCargo().replaceAll("\\s+$", "").toUpperCase() + ", ", fontCargoTitulo);
                            Chunk chunkEmpresaDados = new Chunk(curriculo.getHistoricosTrabalho().get(2).getEmpresa().replaceAll("\\s+$", ""), fontEmpresaInstituicao);

                            paragraph = new Paragraph();
                            paragraph.add(chunkCargo);
                            paragraph.add(chunkEmpresaDados);
                            document.add(paragraph);

                            if (!curriculo.getHistoricosTrabalho().get(2).getResponsabilidades().isEmpty()) {
                                Chunk chunkResponsabilidades = new Chunk(curriculo.getHistoricosTrabalho().get(2).getResponsabilidades(), fontNormal);

                                paragraph = new Paragraph();
                                paragraph.add(chunkResponsabilidades);
                                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                                document.add(paragraph);
                            }
                        }
                    }

                    if (indexExists(curriculo.getHistoricosTrabalho(), 3)) {
                        if (verificarHistoricoTrabalhoCompleto(3)) { // Trabalho 4.
                            document.add(new Paragraph(Chunk.NEWLINE));
                            Chunk chunkData;

                            if (curriculo.getHistoricosTrabalho().get(3).getDataTermino() != null && !curriculo.getHistoricosTrabalho().get(3).getDataTermino().isEmpty()) {
                                chunkData = new Chunk(curriculo.getHistoricosTrabalho().get(3).getDataInicio().replaceAll("\\s+$", "")
                                        + " - " + curriculo.getHistoricosTrabalho().get(3).getDataTermino().replaceAll("\\s+$", ""), fontData);
                            }
                            else {
                                chunkData = new Chunk(curriculo.getHistoricosTrabalho().get(3).getDataInicio().replaceAll("\\s+$", "")
                                        + " - " + getResources().getString(R.string.msgTrabalhoAtual), fontData);
                            }

                            paragraph = new Paragraph();
                            paragraph.add(chunkData);
                            document.add(paragraph);

                            Chunk chunkCargo = new Chunk(curriculo.getHistoricosTrabalho().get(3).getCargo().replaceAll("\\s+$", "").toUpperCase() + ", ", fontCargoTitulo);
                            Chunk chunkEmpresaDados = new Chunk(curriculo.getHistoricosTrabalho().get(3).getEmpresa().replaceAll("\\s+$", ""), fontEmpresaInstituicao);

                            paragraph = new Paragraph();
                            paragraph.add(chunkCargo);
                            paragraph.add(chunkEmpresaDados);
                            document.add(paragraph);

                            if (!curriculo.getHistoricosTrabalho().get(3).getResponsabilidades().isEmpty()) {
                                Chunk chunkResponsabilidades = new Chunk(curriculo.getHistoricosTrabalho().get(3).getResponsabilidades(), fontNormal);

                                paragraph = new Paragraph();
                                paragraph.add(chunkResponsabilidades);
                                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                                document.add(paragraph);
                            }
                        }
                    }
                }
            }

            // -------------------------------------------------------------------------------------

            trechoErro = "Educação.";

            // Formação.
            if (verificarFormacaoCompleta(0) || verificarFormacaoCompleta(1)) {
                document.add(new Paragraph(new Chunk(Chunk.NEWLINE + getResources().getString(R.string.msgFormacao) + Chunk.NEWLINE, fontCabecalho)));
                document.add(new Paragraph());

                if (verificarFormacaoCompleta(0)) { // Formação 1.
                    Chunk chunkCursandoData;

                    if (!curriculo.getFormacoes().get(0).isCursando()) {
                        chunkCursandoData = new Chunk(curriculo.getFormacoes().get(0).getAno().replaceAll("\\s+$", ""), fontData);
                    }
                    else {
                        chunkCursandoData = new Chunk(getResources().getString(R.string.cbCursando).replaceAll("\\s+$", ""), fontData);
                    }

                    paragraph = new Paragraph();
                    paragraph.add(chunkCursandoData);
                    document.add(paragraph);

                    Chunk chunkTitulo = new Chunk(curriculo.getFormacoes().get(0).getGrau().replaceAll("\\s+$", "").toUpperCase() + ", ", fontCargoTitulo);
                    Chunk chunkCidadeInstituicao = new Chunk(curriculo.getFormacoes().get(0).getNomeInstituicao().replaceAll("\\s+$", "")
                            + ", " + curriculo.getFormacoes().get(0).getCidade().replaceAll("\\s+$", ""), fontEmpresaInstituicao);

                    paragraph = new Paragraph();
                    paragraph.add(chunkTitulo);
                    paragraph.add(chunkCidadeInstituicao);
                    document.add(paragraph);

                    if (!curriculo.getFormacoes().get(0).getInformacoesRelevantes().isEmpty()) {
                        Chunk chunkInformacoesRelevantes = new Chunk(curriculo.getFormacoes().get(0).getInformacoesRelevantes().replaceAll("\\s+$", ""), fontNormal);

                        paragraph = new Paragraph();
                        paragraph.add(chunkInformacoesRelevantes);
                        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                        document.add(paragraph);
                    }
                }

                if (verificarFormacaoCompleta(1)) { // Formação 2.
                    Chunk chunkCursandoData;

                    document.add(new Paragraph(Chunk.NEWLINE));

                    if (!curriculo.getFormacoes().get(1).isCursando()) {
                        chunkCursandoData = new Chunk(curriculo.getFormacoes().get(1).getAno().replaceAll("\\s+$", ""), fontData);
                    }
                    else {
                        chunkCursandoData = new Chunk(getResources().getString(R.string.cbCursando).replaceAll("\\s+$", ""), fontData);
                    }

                    paragraph = new Paragraph();
                    paragraph.add(chunkCursandoData);
                    document.add(paragraph);

                    Chunk chunkTitulo = new Chunk(curriculo.getFormacoes().get(1).getGrau().replaceAll("\\s+$", "").toUpperCase() + ", ", fontCargoTitulo);
                    Chunk chunkCidadeInstituicao = new Chunk(curriculo.getFormacoes().get(1).getNomeInstituicao().replaceAll("\\s+$", "")
                            + ", " + curriculo.getFormacoes().get(1).getCidade().replaceAll("\\s+$", ""), fontEmpresaInstituicao);

                    paragraph = new Paragraph();
                    paragraph.add(chunkTitulo);
                    paragraph.add(chunkCidadeInstituicao);
                    document.add(paragraph);

                    if (!curriculo.getFormacoes().get(1).getInformacoesRelevantes().isEmpty()) {
                        Chunk chunkInformacoesRelevantes = new Chunk(curriculo.getFormacoes().get(1).getInformacoesRelevantes().replaceAll("\\s+$", ""), fontNormal);

                        paragraph = new Paragraph();
                        paragraph.add(chunkInformacoesRelevantes);
                        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                        document.add(paragraph);
                    }
                }

                if (aplicativoComprado) {
                    if (indexExists(curriculo.getFormacoes(), 2)) {
                        if (verificarFormacaoCompleta(2)) { // Formação 3.
                            Chunk chunkCursandoData;

                            document.add(new Paragraph(Chunk.NEWLINE));

                            if (!curriculo.getFormacoes().get(2).isCursando()) {
                                chunkCursandoData = new Chunk(curriculo.getFormacoes().get(2).getAno().replaceAll("\\s+$", ""), fontData);
                            }
                            else {
                                chunkCursandoData = new Chunk(getResources().getString(R.string.cbCursando).replaceAll("\\s+$", ""), fontData);
                            }

                            paragraph = new Paragraph();
                            paragraph.add(chunkCursandoData);
                            document.add(paragraph);

                            Chunk chunkTitulo = new Chunk(curriculo.getFormacoes().get(2).getGrau().replaceAll("\\s+$", "").toUpperCase() + ", ", fontCargoTitulo);
                            Chunk chunkCidadeInstituicao = new Chunk(curriculo.getFormacoes().get(2).getNomeInstituicao().replaceAll("\\s+$", "")
                                    + ", " + curriculo.getFormacoes().get(2).getCidade().replaceAll("\\s+$", ""), fontEmpresaInstituicao);

                            paragraph = new Paragraph();
                            paragraph.add(chunkTitulo);
                            paragraph.add(chunkCidadeInstituicao);
                            document.add(paragraph);

                            if (!curriculo.getFormacoes().get(2).getInformacoesRelevantes().isEmpty()) {
                                Chunk chunkInformacoesRelevantes = new Chunk(curriculo.getFormacoes().get(2).getInformacoesRelevantes().replaceAll("\\s+$", ""), fontNormal);

                                paragraph = new Paragraph();
                                paragraph.add(chunkInformacoesRelevantes);
                                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                                document.add(paragraph);
                            }
                        }
                    }

                    if (indexExists(curriculo.getFormacoes(), 3)) {
                        if (verificarFormacaoCompleta(3)) { // Formação 4.
                            Chunk chunkCursandoData;

                            document.add(new Paragraph(Chunk.NEWLINE));

                            if (!curriculo.getFormacoes().get(3).isCursando()) {
                                chunkCursandoData = new Chunk(curriculo.getFormacoes().get(3).getAno().replaceAll("\\s+$", ""), fontData);
                            }
                            else {
                                chunkCursandoData = new Chunk(getResources().getString(R.string.cbCursando).replaceAll("\\s+$", ""), fontData);
                            }

                            paragraph = new Paragraph();
                            paragraph.add(chunkCursandoData);
                            document.add(paragraph);

                            Chunk chunkTitulo = new Chunk(curriculo.getFormacoes().get(3).getGrau().replaceAll("\\s+$", "").toUpperCase() + ", ", fontCargoTitulo);
                            Chunk chunkCidadeInstituicao = new Chunk(curriculo.getFormacoes().get(3).getNomeInstituicao().replaceAll("\\s+$", "")
                                    + ", " + curriculo.getFormacoes().get(3).getCidade().replaceAll("\\s+$", ""), fontEmpresaInstituicao);

                            paragraph = new Paragraph();
                            paragraph.add(chunkTitulo);
                            paragraph.add(chunkCidadeInstituicao);
                            document.add(paragraph);

                            if (!curriculo.getFormacoes().get(3).getInformacoesRelevantes().isEmpty()) {
                                Chunk chunkInformacoesRelevantes = new Chunk(curriculo.getFormacoes().get(3).getInformacoesRelevantes().replaceAll("\\s+$", ""), fontNormal);

                                paragraph = new Paragraph();
                                paragraph.add(chunkInformacoesRelevantes);
                                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                                document.add(paragraph);
                            }
                        }
                    }
                }
            }

            // -------------------------------------------------------------------------------------

            trechoErro = "Outras Informações.";

            // Outras Informações.
            if (!curriculo.getOutrasInformacoes().getHabilidades().isEmpty() || !curriculo.getOutrasInformacoes().getLinguas().isEmpty()
                    || !curriculo.getOutrasInformacoes().getInteresses().isEmpty() || !curriculo.getOutrasInformacoes().getOutros().isEmpty()) {
                document.add(new Paragraph(new Chunk(Chunk.NEWLINE + getResources().getString(R.string.msgOutrasInformacoes) + Chunk.NEWLINE, fontCabecalho)));
                document.add(new Paragraph());

                PdfPTable table;
                PdfPCell cell;
                Phrase phrase;
                Chunk chunk;
                List<String> outrasInformacoes = new ArrayList<>();

                if (!curriculo.getOutrasInformacoes().getHabilidades().isEmpty()) {
                    outrasInformacoes.add("Habilidades");
                }
                if (!curriculo.getOutrasInformacoes().getLinguas().isEmpty()) {
                    outrasInformacoes.add("Linguas");
                }
                if (!curriculo.getOutrasInformacoes().getInteresses().isEmpty()) {
                    outrasInformacoes.add("Interesses");
                }
                if (!curriculo.getOutrasInformacoes().getOutros().isEmpty()) {
                    outrasInformacoes.add("Outros");
                }

                if (outrasInformacoes.size() < 2) {
                    table = new PdfPTable(1);
                }
                else {
                    table = new PdfPTable(2);
                }

                table.setWidthPercentage(100);
                table.getDefaultCell().setBorder(0);

                int qtdeOutrasInformacoes = 1;

                for (String oi : outrasInformacoes) {
                    if (oi.equals("Habilidades")) {
                        phrase = new Phrase();

                        chunk = new Chunk("• " + getResources().getString(R.string.msgHabilidades), fontHabilidade);
                        phrase.add(chunk);
                        chunk = new Chunk(Chunk.NEWLINE + curriculo.getOutrasInformacoes().getHabilidades(), fontNormal);
                        phrase.add(chunk);

                        cell = new PdfPCell(phrase);
                        cell.setBorder(Rectangle.NO_BORDER);

                        table.addCell(cell);

                        qtdeOutrasInformacoes++;
                    }
                    else if (oi.equals("Linguas")) {
                        phrase = new Phrase();

                        chunk = new Chunk("• " + getResources().getString(R.string.msgLinguas), fontHabilidade);
                        phrase.add(chunk);
                        chunk = new Chunk(Chunk.NEWLINE + curriculo.getOutrasInformacoes().getLinguas(), fontNormal);
                        phrase.add(chunk);

                        cell = new PdfPCell(phrase);
                        cell.setBorder(Rectangle.NO_BORDER);

                        table.addCell(cell);

                        qtdeOutrasInformacoes++;
                    }
                    else if (oi.equals("Interesses")) {
                        phrase = new Phrase();

                        if (qtdeOutrasInformacoes >= 3) {
                            phrase.add(new Chunk("\n"));
                        }

                        chunk = new Chunk("• " + getResources().getString(R.string.msgInteresses), fontHabilidade);
                        phrase.add(chunk);
                        chunk = new Chunk(Chunk.NEWLINE + curriculo.getOutrasInformacoes().getInteresses(), fontNormal);
                        phrase.add(chunk);

                        cell = new PdfPCell(phrase);
                        cell.setBorder(Rectangle.NO_BORDER);

                        table.addCell(cell);

                        qtdeOutrasInformacoes++;
                    }
                    else if (oi.equals("Outros")) {
                        phrase = new Phrase();

                        if (qtdeOutrasInformacoes >= 3) {
                            phrase.add(new Chunk("\n"));
                        }

                        chunk = new Chunk("• " + getResources().getString(R.string.msgOutros), fontHabilidade);
                        phrase.add(chunk);
                        chunk = new Chunk(Chunk.NEWLINE + curriculo.getOutrasInformacoes().getOutros(), fontNormal);
                        phrase.add(chunk);

                        cell = new PdfPCell(phrase);
                        cell.setBorder(Rectangle.NO_BORDER);

                        table.addCell(cell);
                    }
                }

                // Adicionar a tabela.
                table.completeRow();
                document.add(table);
            }

            document.close();
            fileOut.close();

            trechoErro = "Gerar Arquivo.";

            if (aplicativoComprado) {
                Intent intent = new Intent(ActivityHomeScreen.this, ActivityVisualizarCurriculo.class);
                intent.putExtra("PathArquivoCurriculo", file.getAbsolutePath());
                intent.putExtra("Path", file.getParent());
                startActivityForResult(intent,1);
            }
            else { // todo error.
                if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                    dialog = new Dialog(ActivityHomeScreen.this, android.R.style.Theme_DeviceDefault_NoActionBar_TranslucentDecor);
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
                    Intent intent = new Intent(ActivityHomeScreen.this, ActivityVisualizarCurriculo.class);
                    intent.putExtra("PathArquivoCurriculo", file.getAbsolutePath());
                    intent.putExtra("Path", file.getParent());
                    startActivityForResult(intent,1);
                }
            }
        }
        catch (final Exception e) {
            final String finalTrechoErro = trechoErro;

            AlertDialog builder = new AlertDialog.Builder(ActivityHomeScreen.this).create();

            builder.setTitle(getResources().getString(R.string.app_name));
            builder.setMessage(getResources().getString(R.string.msgErroCriarPDFCurriculo));

            builder.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.msgSim),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Enviar o e-mail.
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "email@email.com"));
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Error Description");
                            emailIntent.putExtra(Intent.EXTRA_TEXT, "Error: " + e.getMessage() + ". " + e.getStackTrace().toString() +
                                    "\n\nExcerpt from error: " + finalTrechoErro + "\n\nAndroid Version: " + String.valueOf(android.os.Build.VERSION.SDK_INT));

                            startActivity(Intent.createChooser(emailIntent, "Send e-mail..."));
                        }
                    });

            builder.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.msgNao),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { dialog.dismiss(); }
                    });

            builder.show();
        }
    }

    private String obterDataNascimento(String dataNascimento) {
        String dataNascimentoFormatada;

        try {
            Date date = new Date(dataNascimento);

            dataNascimentoFormatada = date.toString();

            return dataNascimentoFormatada;
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Erro: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return dataNascimento;
        }
    }

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    private boolean verificarHistoricoTrabalhoCompleto(int posicao) {
        boolean completo = false;

        if (!curriculo.getHistoricosTrabalho().get(posicao).getCargo().isEmpty() && !curriculo.getHistoricosTrabalho().get(posicao).getEmpresa().isEmpty()
                && !curriculo.getHistoricosTrabalho().get(posicao).getDataInicio().isEmpty()) {
            completo = true;
        }

        return completo;
    }

    private boolean verificarFormacaoCompleta(int posicao) {
        boolean completa = false;

        if (!curriculo.getFormacoes().get(posicao).getGrau().isEmpty() && !curriculo.getFormacoes().get(posicao).getNomeInstituicao().isEmpty()
                && !curriculo.getFormacoes().get(posicao).getCidade().isEmpty()) {
            completa = true;
        }

        return completa;
    }

    public static void salvarDadosBasicos(Context context) {
        // Salvar os dados.
        try {
            // Salvar os Dados Básicos.
            preferenciasEditor.putString(dadosBasicos, new Gson().toJson(curriculo.getDadosBasicos()));

            preferenciasEditor.commit();
        }
        catch (Exception ex) {
            Toast.makeText(context, "Error: " + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static void salvarDadosExperienciaProfissional(Context context) {
        // Salvar os dados.
        try {
            // Salvar o Histórico de Trabalho.
            preferenciasEditor.putString(dadosExperienciaProfissional, new Gson().toJson(curriculo.getHistoricosTrabalho()));

            preferenciasEditor.commit();
        }
        catch (Exception ex) {
            Toast.makeText(context, "Error: " + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static void salvarDadosEducacao(Context context) {
        // Salvar os dados.
        try {
            // Salvar a Formação.
            preferenciasEditor.putString(dadosEducacao, new Gson().toJson(curriculo.getFormacoes()));

            preferenciasEditor.commit();
        }
        catch (Exception ex) {
            Toast.makeText(context, "Error: " + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static void salvarDadosOutrasInformacoes(Context context) {
        // Salvar os dados.
        try {
            // Salvar as Outras Informações.
            preferenciasEditor.putString(dadosOutrasInformacoes, new Gson().toJson(curriculo.getOutrasInformacoes()));

            preferenciasEditor.commit();
        }
        catch (Exception ex) {
            Toast.makeText(context, "Error: " + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean consultarDados() {
        boolean status = false;
        String jsonDadosBasicos;
        String jsonExperienciaProfissional;
        String jsonEducacao;
        String jsonOutrasInformacoes;

        try {
            // Consultar preferência da moeda.
            jsonDadosBasicos = preferenciasConsultarDados.getString(dadosBasicos, null);
            jsonExperienciaProfissional = preferenciasConsultarDados.getString(dadosExperienciaProfissional, null);
            jsonEducacao = preferenciasConsultarDados.getString(dadosEducacao, null);
            jsonOutrasInformacoes = preferenciasConsultarDados.getString(dadosOutrasInformacoes, null);

            if (jsonDadosBasicos != null && !jsonDadosBasicos.isEmpty()) {
                DadosBasicos dadosBasicos = new Gson().fromJson(jsonDadosBasicos, DadosBasicos.class);

                if (dadosBasicos != null) {
                    curriculo.setDadosBasicos(dadosBasicos);
                }
            }

            if (jsonExperienciaProfissional != null && !jsonExperienciaProfissional.isEmpty()) {
                ArrayList<ExperienciaProfissional> historicosTrabalho =
                        new Gson().fromJson(jsonExperienciaProfissional, new TypeToken<ArrayList<ExperienciaProfissional>>(){}.getType());
                curriculo.setHistoricosTrabalho(historicosTrabalho);
            }

            if (jsonEducacao != null && !jsonEducacao.isEmpty()) {
                ArrayList<Educacao> formacoes = new Gson().fromJson(jsonEducacao, new TypeToken<ArrayList<Educacao>>() {
                }.getType());
                curriculo.setFormacoes(formacoes);
            }

            if (jsonOutrasInformacoes != null && !jsonOutrasInformacoes.isEmpty()) {
                OutrasInformacoes outrasInformacoes = new Gson().fromJson(jsonOutrasInformacoes, OutrasInformacoes.class);

                if (outrasInformacoes != null) {
                    curriculo.setOutrasInformacoes(outrasInformacoes);
                }
            }

            status = true;
        }
        catch (Exception ex) {

            status = false;
        }

        return status;
    }

    public boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
    }

    private void limparDadosEComponentes() {
        // Limpar os dados.
        SharedPreferences.Editor preferencias = getSharedPreferences(PREFS_CURRICULO_SIMPLES, MODE_PRIVATE).edit();

        try {
            // Limpar os dados.
            // Limpar os Dados Básicos.
            preferencias.putString(dadosBasicos, "");

            // Limpar o Histórico de Trabalho.
            preferencias.putString(dadosExperienciaProfissional, "");

            // Limpar a Formação.
            preferencias.putString(dadosEducacao, "");

            // Limpar as Outras Informações.
            preferencias.putString(dadosOutrasInformacoes, "");

            preferencias.commit();

            // Limpar os componentes dos Steps.
            stepperLayout.setCurrentStepPosition(5); // Ir para o último Step para limpar os componentes.

            if (stepOutrasInformacoes != null) {
                stepOutrasInformacoes.limparComponentes();
                stepperLayout.onBackClicked();
            }
            if (stepEducacao != null) {
                stepEducacao.limparComponentes();
                stepperLayout.onBackClicked();
            }
            if (stepExperienciaProfissional != null) {
                stepExperienciaProfissional.limparComponentes();
                stepperLayout.onBackClicked();
            }
            if (stepHabilidadesCompetencias != null) {
                stepHabilidadesCompetencias.limparComponentes();
                stepperLayout.onBackClicked();
            }
            if (stepObjetivos != null) {
                stepObjetivos.limparComponentes();
                stepperLayout.onBackClicked();
            }
            if (stepDadosPessoais != null) {
                stepDadosPessoais.limparComponentes();
            }
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error: " + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void verificarAplicativoComprado() {
        // Verificar se o aplicativo foi comprado e alterar a Navigation Drawer.
        preferencias = getSharedPreferences(PREFS_CURRICULO_SIMPLES, MODE_PRIVATE);
        aplicativoComprado = preferencias.getBoolean("proversion", false);

        //aplicativoComprado = true; // ------------------------------------------------------------

        if (aplicativoComprado) {
            invalidateOptionsMenu();

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
                    public void onAdLoaded() { }
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
                        if (file != null) {
                            Intent intent = new Intent(ActivityHomeScreen.this, ActivityVisualizarCurriculo.class);
                            intent.putExtra("PathArquivoCurriculo", file.getAbsolutePath());
                            intent.putExtra("Path", file.getParent());
                            startActivityForResult(intent,1);
                        }

                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }
                });
            }
            catch (Exception ex) { }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (aplicativoComprado) {
            menu.findItem(R.id.action_versao_pro).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_versao_pro) {
            intent = new Intent(ActivityHomeScreen.this, ActivityComprarVersaoPRO.class);
            startActivityForResult(intent, 0);
            return true;
        }
        else if (id == R.id.action_sobre) {
            intent = new Intent(ActivityHomeScreen.this, ActivitySobre.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_apagar_dados) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle(getResources().getString(R.string.app_name));
            alert.setMessage(getResources().getString(R.string.msgApagarDados));
            alert.setPositiveButton(getResources().getString(R.string.msgSim), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    limparDadosEComponentes();

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.msgDadosApagados), Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton(getResources().getString(R.string.msgNao), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            });

            alert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String [] permissions=new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
            };
            List<String> listPermissionsNeeded = new ArrayList<>();
            for (String permission:permissions) {
                if (ContextCompat.checkSelfPermission(this,permission )!= PackageManager.PERMISSION_GRANTED){
                    listPermissionsNeeded.add(permission);
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCESS_WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_WRITE_EXTERNAL_STORAGE : {
                // If request is cancelled, the result arrays are empty.
                if (gerarArquivoCurriculo) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // Permissão Concedida.
                        try {
                            exportarPDF();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else { // Permissão Não Concedida.
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.msgNecessarioConcederPermissao),
                                Toast.LENGTH_SHORT).show();
                    }

                    gerarArquivoCurriculo = false;
                }

                return;
            }
        }
    }

    /**
     * Activity on result para atualizar os dados da lista.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) { // Atualizar as listas de compras.
            if (resultCode == -1) { // Compra feita.
                verificarAplicativoComprado();
            }

            if (!aplicativoComprado) {
                //interstitialAd.loadAd(adRequest);
            }
        }
        if (requestCode == 1) { }
    }
}
