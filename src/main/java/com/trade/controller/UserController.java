package com.trade.controller;

import com.trade.entity.UserEntity;
import com.trade.exception.NoUserFoundException;
import com.trade.exception.NoUsersFoundException;
import com.trade.exception.UserAlreadyExistsException;
import com.trade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
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

    @PostMapping
    public ResponseEntity addUser(@RequestBody UserEntity user){
        try {
            return ResponseEntity.ok(userService.addUser(user));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @PutMapping
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
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }


}
