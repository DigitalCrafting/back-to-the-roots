package org.digitalcrafting.eregold.api.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {
    private final RegistrationControllerService service;

    @PostMapping
    @ResponseBody
    public RegisterResponse register(@RequestBody @Valid RegisterRequest request) {
        return service.register(request);
    }
}
