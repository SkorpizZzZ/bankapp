package org.company.transfer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "gateway", contextId = "blocker")
public interface BlockerFeign {

    @GetMapping("/blocker")
    Boolean isSuspicious();
}
