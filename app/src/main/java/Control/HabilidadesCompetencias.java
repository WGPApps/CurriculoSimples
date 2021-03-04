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

public class HabilidadesCompetencias {
    private boolean habilidadeGerencia;
    private boolean habilidadeLideranca;
    private boolean habilidadeVendas;
    private boolean habilidadeComunicacao;
    private String gerencia;
    private String lideranca;
    private String vendas;
    private String comunicacao;

    public boolean isHabilidadeGerencia() {
        return habilidadeGerencia;
    }

    public void setHabilidadeGerencia(boolean habilidadeGerencia) {
        this.habilidadeGerencia = habilidadeGerencia;
    }

    public boolean isHabilidadeLideranca() {
        return habilidadeLideranca;
    }

    public void setHabilidadeLideranca(boolean habilidadeLideranca) {
        this.habilidadeLideranca = habilidadeLideranca;
    }

    public boolean isHabilidadeVendas() {
        return habilidadeVendas;
    }

    public void setHabilidadeVendas(boolean habilidadeVendas) {
        this.habilidadeVendas = habilidadeVendas;
    }

    public boolean isHabilidadeComunicacao() {
        return habilidadeComunicacao;
    }

    public void setHabilidadeComunicacao(boolean habilidadeComunicacao) {
        this.habilidadeComunicacao = habilidadeComunicacao;
    }

    public String getGerencia() {
        return gerencia;
    }

    public void setGerencia(String gerencia) {
        this.gerencia = gerencia;
    }

    public String getLideranca() {
        return lideranca;
    }

    public void setLideranca(String lideranca) {
        this.lideranca = lideranca;
    }

    public String getVendas() {
        return vendas;
    }

    public void setVendas(String vendas) {
        this.vendas = vendas;
    }

    public String getComunicacao() {
        return comunicacao;
    }

    public void setComunicacao(String comunicacao) {
        this.comunicacao = comunicacao;
    }

    public HabilidadesCompetencias() {
        this.habilidadeGerencia = false;
        this.habilidadeLideranca = false;
        this.habilidadeVendas = false;
        this.habilidadeComunicacao = false;
        this.gerencia = "";
        this.lideranca = "";
        this.vendas = "";
        this.comunicacao = "";
    }

    public HabilidadesCompetencias(boolean habilidadeGerencia, boolean habilidadeLideranca,
                                   boolean habilidadeVendas, boolean habilidadeComunicacao,
                                   String gerencia, String lideranca, String vendas, String comunicacao) {
        this.habilidadeGerencia = false;
        this.habilidadeLideranca = false;
        this.habilidadeVendas = false;
        this.habilidadeComunicacao = false;
        this.gerencia = gerencia;
        this.lideranca = lideranca;
        this.vendas = vendas;
        this.comunicacao = comunicacao;
    }
}
