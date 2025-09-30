package com.bughunters.testclient.test;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="service-client", url="localhost:8000/service-client")
public interface TestFeignClient {
    @GetMapping("/mountain/mountainall")
    String feigntest();
}
