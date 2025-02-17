package com.API.moedas.service;

import com.API.moedas.model.M_Chart;
import com.API.moedas.model.M_Cotacao;
import com.API.moedas.model.M_CotacaoJson;
import com.API.moedas.model.M_RespostaAPI;
import com.API.moedas.repository.R_Cotacao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class S_Cotacao {
    @Autowired
    private R_Cotacao r_cotacao;
    private RestTemplate rt = new RestTemplate();

    @Scheduled(cron = "0 * 10-18 * * ?")
    public void getCotacoesAPI() throws JsonProcessingException {
        String respostaAPI = rt.getForObject("https://economia.awesomeapi.com.br/last/USD-BRL,EUR-BRL,BTC-BRL", String.class);

        Map<String, M_CotacaoJson> mapCotacao = new ObjectMapper().readValue(respostaAPI, M_RespostaAPI.class).getCotacoes();

        M_CotacaoJson dolar = mapCotacao.get("USDBRL");
        M_CotacaoJson euro = mapCotacao.get("EURBRL");
        M_CotacaoJson bitcoin = mapCotacao.get("BTCBRL");

        List<M_Cotacao> listaCotacoes = new ArrayList<>();
        listaCotacoes.add(cotacaoParse(dolar));
        listaCotacoes.add(cotacaoParse(euro));
        listaCotacoes.add(cotacaoParse(bitcoin));

        r_cotacao.saveAll(listaCotacoes);
    }

    private M_Cotacao cotacaoParse(M_CotacaoJson cotacaoJson) {
        M_Cotacao cotacao = new M_Cotacao();
        cotacao.setCotacao(Double.parseDouble(cotacaoJson.getBid()));
        cotacao.setCodMoeda(cotacaoJson.getCode());
        cotacao.setMaxima(Double.parseDouble(cotacaoJson.getHigh()));
        cotacao.setMinima(Double.parseDouble(cotacaoJson.getLow()));
        cotacao.setVariacaoCotacao(Double.parseDouble(cotacaoJson.getVarBid()));
        cotacao.setPercentualTrocas(Double.parseDouble(cotacaoJson.getPctChange()));
        cotacao.setDataCotacao(LocalDateTime.parse(cotacaoJson.getCreate_date(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        cotacao.setDataCriacao(LocalDateTime.now());
        return cotacao;
    }

    public List<M_Chart> getChartMoeda(String codMoeda){
        return r_cotacao.findChartDataByMoeda(codMoeda);
    }

    public List<M_Cotacao> getCotacoesMoeda(String codMoeda){
        return r_cotacao.findCotacoesByMoeda(codMoeda);
    }
}
