package org.company.cash.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.cash.dto.CashDto;
import org.company.cash.exception.CashException;
import org.company.cash.feign.AccountFeign;
import org.company.cash.feign.BlockerFeign;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CashServiceImpl implements CashService {

    private final AccountFeign accountFeign;
    private final BlockerFeign blockerFeign;

    private final NotificationOutboxService notificationOutboxService;


    @Override
    public void withdraw(String login, CashDto cash) {
        log.info("Процесс снятия наличных со счета пользователя {}", login);
        if (isSuspicious()) {
            log.warn("Подозрительная операция снятия наличных");
            notificationOutboxService.createMessage(login, "Подозрительная операция снятия наличных");
            return;
        }
        tryWithdraw(login, cash);
        notificationOutboxService.createMessage(login, "Процесс снятия наличных");
    }

    private boolean isSuspicious() {
        log.debug("Процесс проверки операции");
        try {
            return blockerFeign.isSuspicious();
        } catch (FeignException.ServiceUnavailable e) {
            log.warn("Блокер сервис не доступен");
            return true;
        } catch (FeignException e) {
            log.warn(e.getMessage());
            return true;
        }
    }

    private void tryWithdraw(String login, CashDto cash) {
        try {
            accountFeign.withdraw(login, cash);
            log.info("Успешное снятия наличных со счета {}", login);
        } catch (FeignException.ServiceUnavailable e) {
            log.warn("Аккаунт сервис не доступен");
            throw new CashException("Аккаунт сервис не доступен", e.status());
        } catch (FeignException e) {
            log.warn(e.getMessage());
            throw new CashException(e.contentUTF8(), e.status());
        }
    }

    @Override
    public void deposit(String login, CashDto cash) {
        log.info("Процесс пополнения счета пользователя {}", login);
        if (isSuspicious()) {
            log.warn("Подозрительная операция пополнения счета");
            notificationOutboxService.createMessage(login, "Подозрительная операция пополнения счета");
            return;
        }
        tryDeposit(login, cash);
        notificationOutboxService.createMessage(login, "Процесс пополнения счета");
    }

    private void tryDeposit(String login, CashDto cash) {
        try {
            accountFeign.deposit(login, cash);
            log.info("Успешное пополнение счета {}", login);
        } catch (FeignException.ServiceUnavailable e) {
            log.warn("Аккаунт сервис не доступен");
            throw new CashException("Аккаунт сервис не доступен", e.status());
        } catch (FeignException e) {
            log.warn(e.getMessage());
            throw new CashException(e.contentUTF8(), e.status());
        }
    }
}
