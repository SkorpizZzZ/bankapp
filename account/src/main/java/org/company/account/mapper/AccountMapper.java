package org.company.account.mapper;

import org.company.account.domain.Account;
import org.company.account.dto.AccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto entityToDto(Account account);
}
