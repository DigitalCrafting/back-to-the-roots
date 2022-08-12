package org.digitalcrafting.eregold.api.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginControllerService service;

    @PostMapping
    @ResponseBody
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return service.login(request);
    }
}
