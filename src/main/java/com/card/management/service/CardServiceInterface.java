package com.card.management.service;

import com.card.management.configuration.exception.APIResponse;
import com.card.management.dto.CardDTO;

public interface CardServiceInterface
{
    APIResponse createCard(final CardDTO cardDTO);

    APIResponse updateCard(final CardDTO cardDTO);

    APIResponse findCardById(final Long id);

    APIResponse findCardByPagenation(Long userId, Integer pageNo, Integer pageSize, String sortBy);
}
