package org.company.transfer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "blocker", url = "${app.feign.blocker-service.url}")
public interface BlockerFeign {

    @GetMapping
    Boolean isSuspicious();
}
