package org.company.account.mapper;

import org.apache.commons.collections4.CollectionUtils;
import org.company.account.domain.Account;
import org.company.account.dto.AccountDto;
import org.company.account.dto.CurrencyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "currency", source = "currency.name")
    @Mapping(target = "user", ignore = true)
    Account accountDtoToEntity(AccountDto accountDto);

    default List<AccountDto> toAccountDtoList(List<Account> accounts, List<CurrencyDto> currencies) {
        if (CollectionUtils.isEmpty(accounts) || CollectionUtils.isEmpty(currencies)) {
            return Collections.emptyList();
        }
        List<AccountDto> accountDtoList = new ArrayList<>(accounts.size());
        Map<String, List<CurrencyDto>> currenciesMap = currencies.stream()
                .collect(Collectors.groupingBy(CurrencyDto::name));
        for (Account account : accounts) {
            accountDtoList.add(
                    new AccountDto(
                            account.getAccountId(),
                            currenciesMap.get(account.getCurrency()).getFirst(),
                            account.getBalance(),
                            true
                    )
            );
        }
        return accountDtoList;
    }
}
