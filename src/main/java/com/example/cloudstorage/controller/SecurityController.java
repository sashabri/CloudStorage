package com.example.cloudstorage.controller;

import com.example.cloudstorage.controller.entities.LoginBody;
import com.example.cloudstorage.controller.entities.LoginResponse;
import com.example.cloudstorage.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.example.cloudstorage.controller.ParamsChecker.checkShouldBeNotEmptyStr;
import static com.example.cloudstorage.controller.ParamsChecker.checkShouldBeNotEmptyLoginBody;

@RestController
@RequestMapping("/")
public class SecurityController {
    AuthenticationService authenticationService;

    @Autowired
    public SecurityController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginBody body) throws Exception {
        checkShouldBeNotEmptyLoginBody(body);
        checkShouldBeNotEmptyStr(body.login);
        checkShouldBeNotEmptyStr(body.password);
        return ResponseEntity.ok()
                .body(new LoginResponse(authenticationService.login(body.login, body.password)));
    }

    @PostMapping("logout")
    public ResponseEntity<Object> logout(@RequestHeader(value = "auth-token") String authToken) {
        authenticationService.logout(authToken);
        return ResponseEntity.ok().body(null);
    }

}
