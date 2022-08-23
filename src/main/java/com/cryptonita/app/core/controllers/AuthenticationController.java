package com.cryptonita.app.core.controllers;

import com.cryptonita.app.core.controllers.services.IAutentificationService;
import com.cryptonita.app.core.controllers.utils.RestResponse;
import com.cryptonita.app.data.entities.enums.UserRole;
import com.cryptonita.app.data.entities.enums.UserType;
import com.cryptonita.app.dto.data.request.UserRegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RestController
@AllArgsConstructor
@RequestMapping("/authentication")
@CrossOrigin("*")
@Tag(name = "Authentication")
@Validated
public class AuthenticationController {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    private final IAutentificationService authenticationService;

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Check if a user can be logged with that credentials")
    public RestResponse login(@NotBlank String username,
                              @NotBlank String password) {
        return RestResponse.encapsulate(Pair.of("result", authenticationService.login(username, password)));
    }

    @GetMapping("/loginv2")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Check if a user can be logged with that credentials")
    public RestResponse loginv2(@NotBlank @Email String mail,
                                @NotBlank String password) {
        return RestResponse.encapsulate(Pair.of("result", authenticationService.loginv2(mail, password)));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Registers a new user")
    public RestResponse register(@NotBlank @Email(regexp = EMAIL_PATTERN) String mail,
                                 @NotBlank String username,
                                 @Pattern(regexp = PASSWORD_PATTERN) String password) {
        return RestResponse.encapsulate(authenticationService.register(
                UserRegisterDTO.builder()
                        .username(username)
                        .mail(mail)
                        .password(password)
                        .role(UserRole.USER)
                        .type(UserType.FREE)
                        .build()

        ));
    }

    @PostMapping("/retrieve")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve new password")
    public RestResponse retrieve(String mail) {
        return RestResponse.encapsulate(authenticationService.retrieve(mail));
    }

}
