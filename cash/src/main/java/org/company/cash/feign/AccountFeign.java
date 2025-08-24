package org.company.cash.feign;

import org.company.cash.dto.CashDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "gateway", contextId = "account")
public interface AccountFeign {


    @PutMapping("/account/users/withdraw/{login}")
    void withdraw(@PathVariable("login") String login, @RequestBody CashDto cash);

    @PutMapping("/account/users/deposit/{login}")
    void deposit(@PathVariable("login") String login, @RequestBody CashDto cash);
}
