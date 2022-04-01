package academy.mindswap.flight.controllers;


import academy.mindswap.flight.commands.LoginRequest;
import academy.mindswap.flight.persistence.models.User;
import academy.mindswap.flight.security.CookieFilter;
import academy.mindswap.flight.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        User user = authService.login(loginRequest);
        if (Objects.isNull(user)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ResponseCookie cookie = ResponseCookie
                .from(CookieFilter.AUTH_COOKIE,loginRequest.getEmail())
                .secure(false)
                .httpOnly(true)
                .path("/")
                .maxAge(60 * 60 * 24)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(user);

    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        //authService.logout(authCookie);

        ResponseCookie cookie = ResponseCookie.from(CookieFilter.AUTH_COOKIE,"")
                .secure(false)
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .build();
    }
}
