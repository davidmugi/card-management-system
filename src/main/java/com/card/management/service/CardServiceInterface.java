package com.card.management.service;

import com.card.management.configuration.exception.APIResponse;
import com.card.management.configuration.exception.BadRequestException;
import com.card.management.dto.CardDTO;
import org.springframework.data.domain.Page;

public interface CardServiceInterface
{
    APIResponse<CardDTO> createCard(final CardDTO cardDTO)throws BadRequestException;

    APIResponse<CardDTO> updateCard(final CardDTO cardDTO) throws BadRequestException;

    APIResponse<CardDTO> findCardById(final Long id) throws BadRequestException;

    APIResponse<Page<CardDTO>>  findCardByPagenation(Long userId, Integer pageNo, Integer pageSize, String sortBy,String searchBy);

    APIResponse deleteCardById(final Long id) throws BadRequestException;
}
