package com.card.management.repository;

import com.card.management.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Long> {

    Optional<Card> findCardById(Long id);

    Page<Card> findCardByNameContainsAndUserId(String name,Long userId,Pageable pageable);

    Page<Card> findCardByNameContains(String name,Pageable pageable);

    Page<Card> findCardBy(Pageable pageable);

    Page<Card> findCardByUserId(Long userId,Pageable pageable);

}
