package dev.vaem.legalservices.user;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import dev.vaem.legalservices.user.account.UserAccount;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    
    User accountToUser(UserAccount userAccount);

}
