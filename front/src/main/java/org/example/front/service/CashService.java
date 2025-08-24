package org.example.front.service;

import org.example.front.dto.CashDto;

public interface CashService {

    void processCash(String login, CashDto cash);
}
