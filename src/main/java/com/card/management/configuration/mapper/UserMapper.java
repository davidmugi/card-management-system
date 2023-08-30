package com.card.management.configuration.mapper;

import com.card.management.dto.UserDTO;
import com.card.management.entity.User;
import com.card.management.enumeration.CardStatus;
import com.card.management.enumeration.UserType;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User userDTOToUser(UserDTO userDTO);

    @Mapping(source = "userType",target = "userType",qualifiedByName = "userTypeDesc")
    UserDTO userToUserDTO(User user);

    @Named(value = "userTypeDesc")
    default String userTypeDesc(int code){
        return UserType.toEnum(code).toString();
    }
}
