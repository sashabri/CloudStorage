package com.example.cloudstorage.controller;

import com.example.cloudstorage.controller.entities.LoginBody;
import com.example.cloudstorage.controller.entities.LoginResponse;
import com.example.cloudstorage.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import static com.example.cloudstorage.controller.ParamsChecker.checkShouldBeNotEmptyStr;
import static com.example.cloudstorage.controller.ParamsChecker.checkShouldBeNotEmptyLoginBody;

@RestController
@RequestMapping("/")
public class SecurityController {
    AuthenticationService authenticationService;
    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    public SecurityController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginBody body) throws Exception {
        log.debug("login: LoginBody - " + body.login);
        log.debug("checkShouldBeNotEmptyLoginBody: LoginBody - " + body.login);
        checkShouldBeNotEmptyLoginBody(body);
        log.debug("checkShouldBeNotEmptyStr: input param - login - " + body.login);
        checkShouldBeNotEmptyStr(body.login);
        log.debug("checkShouldBeNotEmptyStr: input param - password");
        checkShouldBeNotEmptyStr(body.password);

        return ResponseEntity.ok()
                .body(new LoginResponse(authenticationService.login(body.login, body.password)));
    }

    @PostMapping("logout")
    public ResponseEntity<Object> logout(@RequestHeader(value = "auth-token") String authToken) {
        log.debug("logout");
        authenticationService.logout(authToken);
        return ResponseEntity.ok().body(null);
    }
}
