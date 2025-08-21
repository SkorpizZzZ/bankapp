package org.company.transfer.service;

import lombok.RequiredArgsConstructor;
import org.company.transfer.dto.TransferDto;
import org.company.transfer.dto.TransferExchangeDto;
import org.company.transfer.feign.AccountFeign;
import org.company.transfer.feign.ExchangeFeign;
import org.company.transfer.mapper.TransferMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final AccountFeign accountFeign;
    private final ExchangeFeign exchangeFeign;

    private final TransferMapper transferMapper;

    @Override
    public TransferExchangeDto transfer(String login, TransferDto transferDto) {
        BigDecimal convertedValue = exchangeFeign.exchange(transferDto.fromCurrency(), transferDto.toCurrency(), transferDto.value());
        return accountFeign.transfer(login, transferMapper.transferDtoToTransferExchangeDto(transferDto, convertedValue));
    }
}
