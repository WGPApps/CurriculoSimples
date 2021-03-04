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

public class ExperienciaProfissional {
    private String cargo;
    private String empresa;
    private String dataInicio;
    private String dataTermino;
    private String responsabilidades;

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getResponsabilidades() {
        return responsabilidades;
    }

    public void setResponsabilidades(String responsabilidades) {
        this.responsabilidades = responsabilidades;
    }

    public ExperienciaProfissional() {
        this.cargo = "";
        this.empresa = "";
        this.dataInicio = "";
        this.dataTermino = "";
        this.responsabilidades = "";
    }

    public ExperienciaProfissional(String cargo, String empresa, String cidade, String estado, String dataInicio,
                                   String dataTermino, String responsabilidades) {
        this.cargo = cargo;
        this.empresa = empresa;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.responsabilidades = responsabilidades;
    }
}
