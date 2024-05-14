package com.microshop.stockmanagement.cartservice.feign.user;

import com.microshop.stockmanagement.cartservice.enums.Language;
import com.microshop.stockmanagement.cartservice.response.InternalApiResponse;
import com.microshop.stockmanagement.cartservice.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("${feign.user-service.name}")
public interface UserServiceFeignClient {

    @GetMapping(value = "/api/1.0/user/{language}/get/{userId}")
    InternalApiResponse<UserResponse> getUser(@PathVariable("language") Language language, @PathVariable("userId") Long userId);

}
