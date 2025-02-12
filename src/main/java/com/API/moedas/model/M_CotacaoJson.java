package com.API.moedas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class M_CotacaoJson {
//    private String codMoeda;
    @JsonProperty("code")
    private String code;

//    private Double maxima;
    @JsonProperty("high")
    private String high;

//    private Double minima;
    @JsonProperty("low")
    private String low;

//    private Double variacaoCotacao;
    @JsonProperty("varBid")
    private String varBid;

//    private Double percentualTrocas;
    @JsonProperty("pctChange")
    private String pctChange;

//    private Double cotacao;
    @JsonProperty("bid")
    private String bid;

//    private LocalDateTime dataCotacao;
    @JsonProperty("create_date")
    private String create_date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getVarBid() {
        return varBid;
    }

    public void setVarBid(String varBid) {
        this.varBid = varBid;
    }

    public String getPctChange() {
        return pctChange;
    }

    public void setPctChange(String pctChange) {
        this.pctChange = pctChange;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
