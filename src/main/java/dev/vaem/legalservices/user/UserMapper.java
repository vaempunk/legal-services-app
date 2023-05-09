package dev.vaem.legalservices.user;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    
    User accountToUser(UserAccount userAccount);

    UserAccount userToAccount(User user);

}
