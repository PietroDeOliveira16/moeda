package com.API.moedas.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cotacao", schema = "money")
public class M_Cotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codMoeda;
    private Double maxima;
    private Double minima;
    private Double variacaoCotacao;
    private Double percentualTrocas;
    private Double cotacao;
    private LocalDateTime dataCotacao;
    private LocalDateTime dataCriacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodMoeda() {
        return codMoeda;
    }

    public void setCodMoeda(String codMoeda) {
        this.codMoeda = codMoeda;
    }

    public Double getMaxima() {
        return maxima;
    }

    public void setMaxima(Double maxima) {
        this.maxima = maxima;
    }

    public Double getMinima() {
        return minima;
    }

    public void setMinima(Double minima) {
        this.minima = minima;
    }

    public Double getVariacaoCotacao() {
        return variacaoCotacao;
    }

    public void setVariacaoCotacao(Double variacaoCotacao) {
        this.variacaoCotacao = variacaoCotacao;
    }

    public Double getPercentualTrocas() {
        return percentualTrocas;
    }

    public void setPercentualTrocas(Double percentualTrocas) {
        this.percentualTrocas = percentualTrocas;
    }

    public Double getCotacao() {
        return cotacao;
    }

    public void setCotacao(Double cotacao) {
        this.cotacao = cotacao;
    }

    public LocalDateTime getDataCotacao() {
        return dataCotacao;
    }

    public void setDataCotacao(LocalDateTime dataCotacao) {
        this.dataCotacao = dataCotacao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
