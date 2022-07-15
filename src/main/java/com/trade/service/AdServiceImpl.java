package com.trade.service;

import com.trade.entity.AdEntity;
import com.trade.entity.UserEntity;
import com.trade.exception.NoAdFoundException;
import com.trade.exception.NoUserFoundException;
import com.trade.exception.NotEnoughRights;
import com.trade.repository.AdRepo;
import com.trade.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdServiceImpl {

    private final AdRepo adRepo;
    private final UserRepo userRepo;

    @Autowired
    public AdServiceImpl(AdRepo adRepo, UserRepo userRepo) {
        this.adRepo = adRepo;
        this.userRepo = userRepo;
    }

    public List<AdEntity> getAllAds() {
        return adRepo.findAll();
    }

    public AdEntity getAdById(Long id) throws NoAdFoundException {
        AdEntity ad = adRepo.findById(id).orElse(null);
        if (ad == null) throw new NoAdFoundException(id);

        return ad;
    }

    public AdEntity addAd(Long id, AdEntity newAd, Authentication authentication) throws NoUserFoundException, NotEnoughRights {
        UserEntity user = userRepo.findById(id).orElse(null);
        if (user == null) throw new NoUserFoundException(id);

        //UserEntity sender = userRepo.findByEmail(((UserDetails) authentication.getPrincipal()).getUsername());
        //if (sender.getId() != id) throw new NotEnoughRights();

        newAd.setUser(user);
        return adRepo.save(newAd);
    }

    public Long deleteAd (Long id){

        adRepo.deleteById(id);
        return id;
    }

    public Long deleteAd (Long id, Authentication authentication) throws NotEnoughRights {

        AdEntity ad = adRepo.findById(id).orElse(null);

        UserEntity sender = userRepo.findByEmail(((UserDetails) authentication.getPrincipal()).getUsername());
        if (sender.getId() != ad.getUser().getId()) throw new NotEnoughRights();

        adRepo.deleteById(id);
        return id;
    }
    public AdEntity updateAd(AdEntity newAd) throws NoAdFoundException {
        AdEntity oldAd = adRepo.findById(newAd.getId()).orElse(null);
        if (oldAd == null) throw new NoAdFoundException(newAd.getId());

        newAd.setUser(oldAd.getUser());
        adRepo.save(newAd);
        return newAd;
    }
    public AdEntity updateAd(AdEntity newAd, Authentication authentication) throws NoAdFoundException, NotEnoughRights {
        AdEntity oldAd = adRepo.findById(newAd.getId()).orElse(null);
        if (oldAd == null) throw new NoAdFoundException(newAd.getId());

        UserEntity sender = userRepo.findByEmail(((UserDetails) authentication.getPrincipal()).getUsername());
        if (sender.getId() != oldAd.getUser().getId()) throw new NotEnoughRights();

        newAd.setUser(oldAd.getUser());
        adRepo.save(newAd);
        return newAd;
    }

}
