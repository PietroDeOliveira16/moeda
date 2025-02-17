package com.API.moedas.controller;

import com.API.moedas.model.M_Chart;
import com.API.moedas.model.M_Cotacao;
import com.API.moedas.service.S_Cotacao;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class C_Cotacao {
    private final S_Cotacao s_cotacao;

    public C_Cotacao(S_Cotacao s_cotacao) {
        this.s_cotacao = s_cotacao;
    }

    @PostMapping("/getChartData")
    @ResponseBody
    public List<M_Chart> getCotacaoMoeda(@RequestParam("moeda") String moeda){
        return s_cotacao.getChartMoeda(moeda);
    }
}
