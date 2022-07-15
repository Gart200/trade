package com.trade.controller;

import com.trade.dto.AuthRequestDto;
import com.trade.entity.UserEntity;
import com.trade.exception.UserAlreadyExistsException;
import com.trade.security.JwtTokenProvider;
import com.trade.service.AdServiceImpl;
import com.trade.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trade/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;

    private final AdServiceImpl adService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserServiceImpl userService, AdServiceImpl adService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.adService = adService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserEntity user){
        try {
            return ResponseEntity.ok(userService.addUser(user));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequestDto requestDto){
        try {
            String email = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
            UserEntity user = userService.getUserByEmail(email);

                if (user == null){
                throw new UsernameNotFoundException("User with email: " + email + " not found");
            }
            String token = jwtTokenProvider.createToken(email,user.getRoles());
            Map<Object, Object> response = new HashMap<>();
            response.put("email",email);
            response.put("token",token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @GetMapping
    public ResponseEntity getAllAds(){
        try {
            return ResponseEntity.ok(adService.getAllAds());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }



}
