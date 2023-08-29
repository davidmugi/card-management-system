package com.card.management.configuration.mapper;

import com.card.management.dto.UserDTO;
import com.card.management.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User userDTOToUser(UserDTO userDTO);

    UserDTO userToUserDTO(User user);
}
