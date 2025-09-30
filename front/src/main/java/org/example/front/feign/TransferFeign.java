package org.example.front.feign;

import org.example.front.dto.TransferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "transfer", url = "${app.feign.transfer-service.url}")
public interface TransferFeign {

    @PostMapping("/{login}")
    TransferDto transfer(@PathVariable("login") String login, @RequestBody TransferDto transferDto);
}
