package com.cryptonita.app.core.controllers;

import com.cryptonita.app.core.controllers.services.IAdminService;
import com.cryptonita.app.core.controllers.utils.RestResponse;
import com.cryptonita.app.data.entities.enums.UserRole;
import com.cryptonita.app.data.entities.enums.UserType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
@CrossOrigin("*")
@Tag(name = "Admin")
@Validated
public class AdminController {

    private final IAdminService adminService;

    @PostMapping("/assets/create")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Creates a certain coin to be tracked")
    public RestResponse createCoin(@NotBlank String coinID,
                                   @NotBlank String name,
                                   @NotBlank String symbol) {
        return RestResponse.encapsulate(adminService.createCoin(coinID, name, symbol));
    }

    @DeleteMapping("/assets/delete")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Removes a coin to be tracked")
    public RestResponse deleteCoin(@NotBlank String name) {
        return RestResponse.encapsulate(adminService.deleteCoin(name));
    }

    @PostMapping("/users/createUser")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create new user")
    public RestResponse createUser(@NotBlank String mail,
                                   @NotBlank String username,
                                   @NotBlank UserRole userRole,
                                   @NotBlank UserType userType) {
        return RestResponse.encapsulate(adminService.createUser(mail, username, userRole, userType));
    }

    @PutMapping("/users/updateUser")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create new user")
    public RestResponse updateUserType(@NotBlank String mail,
                                       @NotBlank UserType userType) {
        return RestResponse.encapsulate(adminService.changeUserType(mail, userType));
    }

    @PostMapping("/users/ban")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Bans a user")
    public RestResponse banUser(@NotBlank String mail) {
        return RestResponse.encapsulate(adminService.banUser(mail));
    }

    @PostMapping("/users/unBan")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "UnBans a user")
    public RestResponse unBanUser(@NotBlank String mail) {
        return RestResponse.encapsulate(adminService.unBanUser(mail));
    }

    @GetMapping("/users/get{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets the information of a user by its id")
    public RestResponse getUserById(@Positive long id) {
        return RestResponse.encapsulate(adminService.getUserById(id));
    }
}
