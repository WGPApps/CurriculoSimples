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

public class Educacao {
    private String grau;
    private boolean cursando;
    private String nomeInstituicao;
    private String cidade;
    private String ano;
    private String informacoesRelevantes;

    public String getGrau() {
        return grau;
    }

    public void setGrau(String grau) {
        this.grau = grau;
    }

    public boolean isCursando() {
        return cursando;
    }

    public void setCursando(boolean cursando) {
        this.cursando = cursando;
    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getInformacoesRelevantes() {
        return informacoesRelevantes;
    }

    public void setInformacoesRelevantes(String informacoesRelevantes) {
        this.informacoesRelevantes = informacoesRelevantes;
    }

    public Educacao() {
        this.grau = "";
        this.cursando = false;
        this.nomeInstituicao = "";
        this.cidade = "";
        this.ano = "";
        this.informacoesRelevantes = "";
    }

    public Educacao(String grau, boolean cursando, String nomeInstituicao, String cidade, String ano, String informacoesRelevantes) {
        this.grau = grau;
        this.cursando = cursando;
        this.nomeInstituicao = nomeInstituicao;
        this.cidade = cidade;
        this.ano = ano;
        this.informacoesRelevantes = informacoesRelevantes;
    }
}
