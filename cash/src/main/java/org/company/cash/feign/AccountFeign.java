package org.company.cash.feign;

import org.company.cash.dto.CashDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "account", url = "${app.feign.account-service.url}")
public interface AccountFeign {

    @PutMapping("/users/withdraw/{login}")
    void withdraw(@PathVariable("login") String login, @RequestBody CashDto cash);

    @PutMapping("/users/deposit/{login}")
    void deposit(@PathVariable("login") String login, @RequestBody CashDto cash);
}
