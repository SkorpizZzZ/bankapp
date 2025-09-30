package org.company.transfer.feign;

import org.company.transfer.dto.TransferExchangeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "account", url = "${app.feign.account-service.url}")
public interface AccountFeign {

    @PostMapping("/users/transfer/{login}")
    TransferExchangeDto transfer(@PathVariable("login") String login, @RequestBody TransferExchangeDto transferDto);
}
