package com.trade.controller;

import com.trade.entity.AdEntity;
import com.trade.exception.NoAdFoundException;
import com.trade.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;

    @Autowired
    public AdController(AdService adService) {
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

    @GetMapping("/{id}")
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

    @PostMapping
    public ResponseEntity addAd(@RequestParam Long user, @RequestBody AdEntity ad){
        try {
            return ResponseEntity.ok(adService.addAd(user, ad));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @PutMapping
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
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAd(@PathVariable Long id){
        try {
            return ResponseEntity.ok(adService.deleteAd(id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

}
