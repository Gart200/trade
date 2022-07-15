package com.trade.controller;

import com.trade.dto.UserDto;
import com.trade.entity.AdEntity;
import com.trade.entity.UserEntity;
import com.trade.exception.NoAdFoundException;
import com.trade.exception.NoUserFoundException;
import com.trade.exception.NotEnoughRights;
import com.trade.service.AdServiceImpl;
import com.trade.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trade/user")
public class UserController {

    private final UserServiceImpl userService;
    private final AdServiceImpl adService;

    public UserController(UserServiceImpl userService, AdServiceImpl adService) {
        this.userService = userService;
        this.adService = adService;
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

    @GetMapping("/user/{id}")
    public ResponseEntity getUser(@PathVariable Long id){
        try {
            UserDto userDto = UserDto.fromUserEntity(userService.getUserById(id));
            return ResponseEntity.ok(userDto);
        } catch (NoUserFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @GetMapping("/ad/{id}")
    public ResponseEntity getAd(@PathVariable Long id){
        try {
            return ResponseEntity.ok(adService.getAdById(id));
        } catch (NoAdFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @PostMapping("/ad")
    public ResponseEntity addAd(@RequestParam Long user, @RequestBody AdEntity ad){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return ResponseEntity.ok(adService.addAd(user, ad, auth));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return ResponseEntity.ok(userService.deleteUser(id, auth));
        }
        catch (NotEnoughRights e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @DeleteMapping("/ad/{id}")
    public ResponseEntity deleteAd(@PathVariable Long id){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return ResponseEntity.ok(adService.deleteAd(id, auth));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @PutMapping("/user")
    public ResponseEntity updateUser(@RequestBody UserEntity user){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return ResponseEntity.ok(userService.updateUser(user, auth));
        } catch (NoUserFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @PutMapping("/ad")
    public ResponseEntity updateAd(@RequestBody AdEntity ad){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return ResponseEntity.ok(adService.updateAd(ad, auth));
        } catch (NoAdFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }
}
