package com.card.management.controller;

import com.card.management.configuration.exception.APIResponse;
import com.card.management.configuration.exception.BadRequestException;
import com.card.management.dto.CardDTO;
import com.card.management.service.CardServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/card")
public class CardController {

    private final CardServiceInterface cardServiceInterface;

    public CardController(CardServiceInterface cardServiceInterface) {
        this.cardServiceInterface = cardServiceInterface;
    }

    @PostMapping("/create")
    public APIResponse<CardDTO> create(@RequestBody @Valid CardDTO cardDTO) throws Exception{
        return cardServiceInterface.createCard(cardDTO);
    }

    @PutMapping("/update")
    public APIResponse<CardDTO> update(@RequestBody CardDTO cardDTO) throws BadRequestException {
        return cardServiceInterface.updateCard(cardDTO);
    }

    @GetMapping("/fetch-all")
    public APIResponse<Page<CardDTO>> fetchAll(@RequestParam(required = false) Long userId, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "createdOn") String sortBy,@RequestParam("searchBy") String searchBy){
        return cardServiceInterface.findCardByPagenation(userId,pageNo,pageSize,sortBy,searchBy);
    }

    @GetMapping("/fetch-by-id/{id}")
    public APIResponse<CardDTO> fetchById(@PathVariable("id") Long id) throws Exception{
        return cardServiceInterface.findCardById(id);
    }

    @DeleteMapping("delete-card-by-id/{id}")
    public APIResponse deleteCardById(@PathVariable("id") Long id) throws BadRequestException{
        return cardServiceInterface.deleteCardById(id);
    }

}
