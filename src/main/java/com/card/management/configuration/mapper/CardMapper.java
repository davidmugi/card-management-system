package com.card.management.configuration.mapper;

import com.card.management.dto.CardDTO;
import com.card.management.entity.Card;
import com.card.management.enumeration.CardStatus;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardMapper {

    Card cardDTOToCard(CardDTO cardDTO);

    @Mapping(source = "status",target = "statusDesc",qualifiedByName = "cardStatusDesc")
    @Mapping(source = "user.lastname",target = "userLastName")
    @Mapping(source = "user.firstname",target = "userFirstName")
    CardDTO cardToCardDTO(Card card);

    @Named(value = "cardStatusDesc")
    default String cardStatusDesc(int code){
        return CardStatus.toEnum(code).toString();
    }
}
