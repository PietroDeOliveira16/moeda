package com.API.moedas.service;

import com.API.moedas.model.M_CotacaoJson;
import com.API.moedas.repository.R_Cotacao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class S_Cotacao {
    @Autowired
    private R_Cotacao r_cotacao;
    private RestTemplate rt = new RestTemplate();

    @Scheduled(cron = "0 * 10-18 * * ?")
    public void getCotacoesAPI(){
        String respostaAPI = rt.getForObject("https://economia.awesomeapi.com.br/last/USD-BRL,EUR-BRL,BTC-BRL", String.class);

        ObjectMapper objectMapper = new ObjectMapper();
    }
}
