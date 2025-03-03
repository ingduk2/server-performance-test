package com.duck.external.api;

import com.duck.common.api.ApiResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiController {

    @GetMapping("/api/external")
    public ApiResponse external(
            @RequestParam Long sleepMillis
    ) {
        sleep(sleepMillis);

        return ApiResponse.success("sleep %sms".formatted(sleepMillis));
    }

    @SneakyThrows
    private void sleep(long sleepMillis) {
        Thread.sleep(sleepMillis);
    }
}
