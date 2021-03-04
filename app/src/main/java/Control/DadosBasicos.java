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

public class DadosBasicos {
    private String nome;
    private String sobrenome;
    private String estadoCivil;
    private String dataNascimento;
    private String endereco;
    private String cidade;
    private String estado;
    private String CEP;
    private String email;
    private String telefone;
    private String foto;

    private String objetivo;

    private HabilidadesCompetencias habilidadesCompetencias;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public HabilidadesCompetencias getHabilidadesCompetencias() {
        return habilidadesCompetencias;
    }

    public void setHabilidadesCompetencias(HabilidadesCompetencias habilidadesCompetencias) {
        this.habilidadesCompetencias = habilidadesCompetencias;
    }

    public DadosBasicos() {
        this.habilidadesCompetencias = new HabilidadesCompetencias();
    }

    public DadosBasicos(String nome, String sobrenome, String estadoCivil, String dataNascimento, String endereco, String cidade, String estado,
                        String CEP, String email, String telefone, String foto, String objetivo, HabilidadesCompetencias habilidadesCompetencias) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.estadoCivil = estadoCivil;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        this.CEP = CEP;
        this.email = email;
        this.telefone = telefone;
        this.foto = foto;
        this.objetivo = objetivo;
        this.habilidadesCompetencias = habilidadesCompetencias;
    }
}
