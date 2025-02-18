package com.API.moedas.repository;

import com.API.moedas.model.M_Chart;
import com.API.moedas.model.M_Cotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface R_Cotacao extends JpaRepository<M_Cotacao, Long> {
    @Query(value = "select * from money.cotacao " +
            "where cod_moeda = :codMoeda " +
            "order by data_criacao asc;", nativeQuery = true)
    List<M_Cotacao> findCotacoesByMoeda(@Param("codMoeda") String codMoeda);

    @Query(value = "select EXTRACT(EPOCH FROM (data_criacao AT TIME ZONE 'America/Sao_Paulo')) * 1000 as data, " +
            "cotacao as valor " +
            "from money.cotacao " +
            "where cod_moeda = :codMoeda " +
            "order by data_criacao asc;", nativeQuery = true)
    List<M_Chart> findChartDataByMoeda(@Param("codMoeda") String codMoeda);

    @Query(value = "select EXTRACT(EPOCH FROM (data_criacao AT TIME ZONE 'America/Sao_Paulo')) * 1000 as data, " +
            "cotacao as valor " +
            "from money.cotacao " +
            "where cod_moeda = :codMoeda " +
            "order by data_criacao desc limit 1;", nativeQuery = true)
    M_Chart findNewestChartByMoeda(@Param("codMoeda") String moeda);
}
