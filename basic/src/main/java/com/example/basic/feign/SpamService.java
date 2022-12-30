package com.example.basic.feign;

import com.example.objects.common.SpamMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "spam-service", url = "https://localhost:8080")
public interface SpamService {

    String URL_POST_SEND_SPAM = "/api/spam/";

    @PostMapping(URL_POST_SEND_SPAM)
    void sendSpamMessage(@RequestBody SpamMessage spamMessage);

}
