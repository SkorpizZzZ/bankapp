package org.company.transfer.service;

import org.company.transfer.dto.TransferDto;
import org.company.transfer.dto.TransferExchangeDto;

public interface TransferService {

    TransferExchangeDto transfer(String login, TransferDto transferDto);
}
