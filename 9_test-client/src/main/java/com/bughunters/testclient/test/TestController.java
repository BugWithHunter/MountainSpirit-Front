package com.bughunters.testclient.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    TestFeignClient testFeignClient;

    public TestController(TestFeignClient testFeignClient) {
        this.testFeignClient = testFeignClient;
    }

    @GetMapping("/health")
    public String health() {
        return "I'm OK";
    }

    @GetMapping("/feign-client-con/{value}")
    public String feignClientCon(@PathVariable String value) {
        System.out.println("feign client 테스트 : " + value);
        return "";
    }

    @GetMapping("/tomainproject")
    public String tomainproject() {
        String test = testFeignClient.feigntest();
        System.out.println(test);
        return "from test client";
    }
}
