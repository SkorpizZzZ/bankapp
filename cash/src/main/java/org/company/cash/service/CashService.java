package org.company.cash.service;

import org.company.cash.dto.CashDto;

public interface CashService {

    void withdraw(String login, CashDto cash);
    void deposit(String login, CashDto cash);

}
