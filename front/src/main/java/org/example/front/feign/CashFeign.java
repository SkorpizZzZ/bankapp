package org.example.front.feign;

import org.example.front.dto.CashDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "cash", url = "${app.feign.cash-service.url}")
public interface CashFeign {

    @PutMapping("/user/withdraw/{login}")
    ResponseEntity<Void> withdraw(@PathVariable("login") String login, @RequestBody CashDto cash);

    @PutMapping("/user/deposit/{login}")
    ResponseEntity<Void> deposit(@PathVariable("login") String login, @RequestBody CashDto cash);
}
