package com.API.moedas.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class M_RespostaAPI {
    private Map<String, M_CotacaoJson> cotacoes = new HashMap<>();

    @JsonAnySetter
    public void setCotacoes(String key, M_CotacaoJson value){
        cotacoes.put(key, value);
    }

    public Map<String, M_CotacaoJson> getCotacoes(){
        return cotacoes;
    }
}
