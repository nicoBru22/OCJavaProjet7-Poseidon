package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface repository pour la gestion des opérations CRUD sur l'entité Trade.
 * Elle hérite de JpaRepository qui fournit des méthodes prédéfinies pour l'accès aux données.
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
