package com.trade.controller;

import com.trade.entity.AdEntity;
import com.trade.entity.UserEntity;
import com.trade.exception.NoAdFoundException;
import com.trade.exception.NoUserFoundException;
import com.trade.exception.NoUsersFoundException;
import com.trade.service.AdServiceImpl;
import com.trade.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trade/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final AdServiceImpl adService;

    @Autowired
    public AdminController(UserServiceImpl userService, AdServiceImpl adService) {
        this.userService = userService;
        this.adService = adService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (NoUserFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllUsers(){
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (NoUsersFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @DeleteMapping("/ad/{id}")
    public ResponseEntity deleteAd(@PathVariable Long id){
        try {
            return ResponseEntity.ok(adService.deleteAd(id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @PutMapping("/user")
    public ResponseEntity updateUser(@RequestBody UserEntity user){
        try {
            return ResponseEntity.ok(userService.updateUser(user));
        } catch (NoUserFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @PutMapping("/ad")
    public ResponseEntity updateAd(@RequestParam Long user, @RequestBody AdEntity ad){
        try {
            return ResponseEntity.ok(adService.updateAd(ad));
        } catch (NoAdFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }
}
