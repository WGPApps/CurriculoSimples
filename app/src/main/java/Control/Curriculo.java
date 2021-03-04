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

package Control;

import java.util.ArrayList;
import java.util.List;

public class Curriculo {
    // Dados básicos.
    private DadosBasicos dadosBasicos;

    // Experiência / Histórico de Trabalho.
    private List<ExperienciaProfissional> historicosTrabalho;

    // Educação / Formação.
    private List<Educacao> formacoes;

    private OutrasInformacoes outrasInformacoes;

    public DadosBasicos getDadosBasicos() {
        return dadosBasicos;
    }

    public void setDadosBasicos(DadosBasicos dadosBasicos) {
        this.dadosBasicos = dadosBasicos;
    }

    public List<ExperienciaProfissional> getHistoricosTrabalho() {
        return historicosTrabalho;
    }

    public void setHistoricosTrabalho(List<ExperienciaProfissional> historicosTrabalho) {
        this.historicosTrabalho = historicosTrabalho;
    }

    public List<Educacao> getFormacoes() {
        return formacoes;
    }

    public OutrasInformacoes getOutrasInformacoes() {
        return outrasInformacoes;
    }

    public void setOutrasInformacoes(OutrasInformacoes outrasInformacoes) {
        this.outrasInformacoes = outrasInformacoes;
    }

    public void setFormacoes(List<Educacao> formacoes) {
        this.formacoes = formacoes;
    }

    public Curriculo() {
        this.dadosBasicos = new DadosBasicos();
        this.outrasInformacoes = new OutrasInformacoes();

        this.historicosTrabalho = new ArrayList<>();
        this.formacoes = new ArrayList<>();

        this.historicosTrabalho.add(new ExperienciaProfissional());
        this.historicosTrabalho.add(new ExperienciaProfissional());
        this.historicosTrabalho.add(new ExperienciaProfissional());
        this.historicosTrabalho.add(new ExperienciaProfissional());

        this.formacoes.add(new Educacao());
        this.formacoes.add(new Educacao());
        this.formacoes.add(new Educacao());
        this.formacoes.add(new Educacao());
    }
}
