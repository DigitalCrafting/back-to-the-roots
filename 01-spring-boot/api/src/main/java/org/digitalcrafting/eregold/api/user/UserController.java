package org.digitalcrafting.eregold.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {
    @GetMapping
    public String getContext() {
        // TODO change to getContext, authentication takes place in the Spring Security filter
        return "SUCCESS";
    }
}
