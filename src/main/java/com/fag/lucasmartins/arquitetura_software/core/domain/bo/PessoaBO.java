package com.fag.lucasmartins.arquitetura_software.core.domain.bo;

import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public class PessoaBO {

    private UUID id;
    private String nomeCompleto;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;

    public void validarIdade() {
        if (this.dataNascimento == null) {
            throw new DomainException("Erro: A data de nascimento é obrigatória.");
        }
        int idade = Period.between(this.dataNascimento, LocalDate.now()).getYears();
        if (idade < 18) {
            throw new DomainException("Erro: O cliente deve ter no mínimo 18 anos.");
        }
    }

    public void validarCpf() {
        if (this.cpf == null || this.cpf.length() != 11) {
            throw new DomainException("Erro: O CPF deve conter exatamente 11 caracteres.");
        }
    }

    public void validarEmail() {
        if (this.email == null || !this.email.contains("@")) {
            throw new DomainException("Erro: O e-mail é obrigatório e deve conter '@'.");
        }
    }

    public void validarTelefone() {
        if (this.telefone == null || this.telefone.length() != 11) {
            throw new DomainException("Erro: O telefone deve conter exatamente 11 caracteres.");
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
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
}
