package com.card.management.service;

import com.card.management.configuration.exception.APIResponse;
import com.card.management.configuration.exception.BadRequestException;
import com.card.management.configuration.mapper.CardMapper;
import com.card.management.dto.CardDTO;
import com.card.management.entity.Card;
import com.card.management.enumeration.ResponseStatus;
import com.card.management.repository.CardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CardService implements CardServiceInterface{

    private final CardRepository cardRepository;

    private final CardMapper cardMapper;

    public CardService(final CardRepository cardRepository,final CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

    @Override
    public APIResponse<CardDTO> createCard(final CardDTO cardDTO) {
        final Card card = cardMapper.cardDTOToCard(cardDTO);
        card.createdOn(1L);

        cardRepository.save(card);

        return new APIResponse<>
                (ResponseStatus.SUCESSS.getStatus(),ResponseStatus.SUCESSS.getStatusCode(),cardMapper.cardToCardDTO(card));
    }

    @Override
    public APIResponse<CardDTO> updateCard(final CardDTO cardDTO) throws BadRequestException {
       final Card card = cardRepository.findCardById(cardDTO.getId())
               .orElseThrow(() -> new BadRequestException("Card with provide id not found"));

        card.setColor(card.getColor());
        card.setDescription(card.getDescription());
        card.updatedOn(1L);

        cardRepository.save(card);

        return new APIResponse<>
                (ResponseStatus.SUCESSS.getStatus(),ResponseStatus.SUCESSS.getStatusCode(),cardMapper.cardToCardDTO(card));
    }

    @Override
    public APIResponse<CardDTO> findCardById(Long id) throws BadRequestException{
        final Card card = cardRepository.findCardById(id)
                .orElseThrow(() -> new BadRequestException("Card with provide id not found"));


        return new APIResponse<>
                (ResponseStatus.SUCESSS.getStatus(),ResponseStatus.SUCESSS.getStatusCode(),cardMapper.cardToCardDTO(card));
    }

    @Override
    public APIResponse<Page<CardDTO>> findCardByPagenation(Long userId, Integer pageNo, Integer pageSize, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable paging = PageRequest.of(pageNo, pageSize, sort);

        Page<CardDTO> cardDTOPage = cardRepository.findCardByUserId(userId,paging).map(cardMapper::cardToCardDTO);

        return new APIResponse<>
                (ResponseStatus.SUCESSS.getStatus(),ResponseStatus.SUCESSS.getStatusCode(),cardDTOPage);
    }
}
