package org.company.transfer.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.transfer.dto.TransferDto;
import org.company.transfer.dto.TransferExchangeDto;
import org.company.transfer.exception.TransferException;
import org.company.transfer.feign.AccountFeign;
import org.company.transfer.feign.ExchangeFeign;
import org.company.transfer.mapper.TransferMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final AccountFeign accountFeign;
    private final ExchangeFeign exchangeFeign;

    private final NotificationOutboxService notificationOutboxService;

    private final TransferMapper transferMapper;

    @Override
    public TransferExchangeDto transfer(String login, TransferDto transferDto) {
        BigDecimal convertedValue = tryConvertValueOrElseThrow(transferDto);
        TransferExchangeDto transferExchangeDto = transferMapper.transferDtoToTransferExchangeDto(transferDto, convertedValue);
        TransferExchangeDto result = tryTransferOrElseThrow(login, transferExchangeDto);
        log.info("Трансфер пользователю {} от пользователя {} состоялся", transferDto.toLogin(), login);
        return result;
    }

    private TransferExchangeDto tryTransferOrElseThrow(String login, TransferExchangeDto transferExchangeDto) {
        try {
            TransferExchangeDto transfer = accountFeign.transfer(login, transferExchangeDto);
            notificationOutboxService.createMessage(login, format("Трансфер пользователю %s состоялся", transferExchangeDto.toLogin()));
            return transfer;
        } catch (FeignException.ServiceUnavailable e) {
            log.warn("Аккаунт сервис не доступен");
            throw new TransferException("Аккаунт сервис не доступен", HttpStatus.INTERNAL_SERVER_ERROR.value());
        } catch (FeignException e) {
            log.error(e.contentUTF8());
            throw new TransferException(e.contentUTF8(), e.status());
        }
    }

    private BigDecimal tryConvertValueOrElseThrow(TransferDto transferDto) {
        BigDecimal convertedValue;
        try {
            convertedValue = exchangeFeign.exchange(transferDto.fromCurrency(), transferDto.toCurrency(), transferDto.value());
        } catch (FeignException.ServiceUnavailable e) {
            log.warn("Сервис валют не доступен");
            throw new TransferException("Сервис валют не доступен", HttpStatus.INTERNAL_SERVER_ERROR.value());
        } catch (FeignException e) {
            log.error(e.contentUTF8());
            throw new TransferException(e.contentUTF8(), e.status());
        }
        return convertedValue;
    }
}
