package com.card.management.service;

import com.card.management.configuration.exception.APIResponse;
import com.card.management.configuration.exception.BadRequestException;
import com.card.management.configuration.mapper.CardMapper;
import com.card.management.dto.CardDTO;
import com.card.management.dto.CurrentUserDTO;
import com.card.management.entity.Card;
import com.card.management.enumeration.ResponseStatus;
import com.card.management.enumeration.UserType;
import com.card.management.repository.CardRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public APIResponse<CardDTO> createCard(final CardDTO cardDTO) throws BadRequestException{
        validation(cardDTO.getColor(),cardDTO.getName());

        final Card card = cardMapper.cardDTOToCard(cardDTO);
        card.setUserId(getUserType() == UserType.ADMIN.getCode() ? card.getUserId() : getUserId());
        card.createdOn(1L);

       final Card saveCard = cardRepository.save(card);

        return new APIResponse<>
                (ResponseStatus.SUCESSS.getStatus(),ResponseStatus.SUCESSS.getStatusCode(),cardMapper.cardToCardDTO(saveCard));
    }

    @Override
    public APIResponse<CardDTO> updateCard(final CardDTO cardDTO) throws BadRequestException {
       final Card card = cardRepository.findCardById(cardDTO.getId())
               .orElseThrow(() -> new BadRequestException("Card with provide id not found"));

        validation(cardDTO.getColor(),cardDTO.getName());

        card.setUserId(getUserType() == UserType.ADMIN.getCode() ? card.getUserId() : getUserId());
        card.setName(cardDTO.getName());
        card.setColor(cardDTO.getColor());
        card.setDescription(cardDTO.getDescription());
        card.updatedOn(1L);

        cardRepository.save(card);

        return new APIResponse<>
                (ResponseStatus.SUCESSS.getStatus(),ResponseStatus.SUCESSS.getStatusCode(),cardMapper.cardToCardDTO(card));
    }

    @Override
    public APIResponse<CardDTO> findCardById(Long id) throws BadRequestException{
        final Card card = cardRepository.findCardById(id)
                .orElseThrow(() -> new BadRequestException("Card with provide id not found"));

        fetchValidation(card.getUserId());

        return new APIResponse<>
                (ResponseStatus.SUCESSS.getStatus(),ResponseStatus.SUCESSS.getStatusCode(),cardMapper.cardToCardDTO(card));
    }

    @Override
    public APIResponse<Page<CardDTO>> findCardByPagenation(Long userId, Integer pageNo, Integer pageSize, String sortBy,String searchBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable paging = PageRequest.of(pageNo, pageSize, sort);

        final Long queryUserId = getUserType() == UserType.ADMIN.getCode() ? userId : getUserId();

        return !StringUtils.isEmpty(searchBy) ? filterBySearchString(queryUserId,paging,searchBy) :
                withoutFilter(queryUserId,paging);
    }

    private APIResponse<Page<CardDTO>> filterBySearchString(Long queryUserId , Pageable paging,String searchBy){
        Page<CardDTO> cardDTOPage = null;

        if(getUserType() != UserType.ADMIN.getCode())
            cardDTOPage = cardRepository.findCardByNameContainsAndUserId(searchBy,queryUserId,paging).map(cardMapper::cardToCardDTO);
        else
            cardDTOPage = cardRepository.findCardByNameContains(searchBy,paging).map(cardMapper::cardToCardDTO);


        return new APIResponse<>
                (ResponseStatus.SUCESSS.getStatus(),ResponseStatus.SUCESSS.getStatusCode(),cardDTOPage);
    }

    private APIResponse<Page<CardDTO>> withoutFilter(Long queryUserId , Pageable paging){
        Page<CardDTO> cardDTOPage = null;

        if(getUserType() != UserType.ADMIN.getCode())
            cardDTOPage = cardRepository.findCardByUserId(queryUserId,paging).map(cardMapper::cardToCardDTO);
        else
            cardDTOPage = cardRepository.findCardBy(paging).map(cardMapper::cardToCardDTO);

        return new APIResponse<>
                (ResponseStatus.SUCESSS.getStatus(),ResponseStatus.SUCESSS.getStatusCode(),cardDTOPage);
    }

    @Override
    public APIResponse deleteCardById(final Long id) throws BadRequestException{
        final Card card = cardRepository.findCardById(id)
                .orElseThrow(() -> new BadRequestException("Card with provide id not found"));

        fetchValidation(card.getUserId());

        cardRepository.delete(card);

        return new APIResponse<>
                (ResponseStatus.SUCESSS.getStatus(),ResponseStatus.SUCESSS.getStatusCode(),true);
    }

    private void fetchValidation(final Long userId) throws BadRequestException{
        if(getUserType() == UserType.ADMIN.getCode())
            return;

        if (getUserId().equals(userId))
            throw new BadRequestException("Card with provide id not found");
    }

    private void validation(final String color,final String name) throws BadRequestException{
        if(StringUtils.isEmpty(name))
            throw new BadRequestException("Card name is required");

        if(StringUtils.isEmpty(color))
            return;

        final String colorCode = color.replace("#","");

        if(!color.startsWith("#") || colorCode.length() != 6 || !StringUtils.isAlphanumeric(colorCode))
            throw new BadRequestException("Invalid color");
    }

    private int getUserType(){
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getDetails();

      return  ((CurrentUserDTO) principal).getUserType();
    }

    private Long getUserId(){
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getDetails();

        return ((CurrentUserDTO) principal).getId();
    }
}
