package org.example.front.service;

import lombok.RequiredArgsConstructor;
import org.example.front.constant.ActionMethod;
import org.example.front.dto.CashDto;
import org.example.front.feign.CashFeign;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashServiceImpl implements CashService {

    private final CashFeign cashFeign;

    @Override
    public void processCash(String login, CashDto cash) {
        if (isWithdraw(cash)) {
            cashFeign.withdraw(login, cash);
        } else {
            cashFeign.deposit(login, cash);
        }
    }

    private static boolean isWithdraw(CashDto cash) {
        return cash.action().equalsIgnoreCase(ActionMethod.GET.name());
    }
}
