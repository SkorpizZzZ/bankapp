package org.company.transfer.mapper;

import org.company.transfer.dto.TransferDto;
import org.company.transfer.dto.TransferExchangeDto;
import org.mapstruct.Mapper;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface TransferMapper {

    TransferExchangeDto transferDtoToTransferExchangeDto(TransferDto transferDto, BigDecimal convertedValue);
}
