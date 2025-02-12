package com.API.moedas.repository;

import com.API.moedas.model.M_Cotacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R_Cotacao extends JpaRepository<M_Cotacao, Long> {

}
