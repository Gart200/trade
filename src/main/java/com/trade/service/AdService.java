package com.trade.service;

import com.trade.entity.AdEntity;
import com.trade.entity.UserEntity;
import com.trade.exception.NoAdFoundException;
import com.trade.exception.NoUserFoundException;
import com.trade.repository.AdRepo;
import com.trade.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdService {

    @Autowired
    AdRepo adRepo;

    @Autowired
    UserRepo userRepo;

    public List<AdEntity> getAllAds() {
        return adRepo.findAll();
    }

    public AdEntity getAdById(Long id) throws NoAdFoundException {
        AdEntity ad = adRepo.findById(id).orElse(null);
        if (ad == null) throw new NoAdFoundException(id);

        return ad;
    }

    public AdEntity addAd(Long id, AdEntity newAd) throws NoUserFoundException {
        UserEntity user = userRepo.findById(id).orElse(null);
        if (user == null) throw new NoUserFoundException(id);

        newAd.setUser(user);
        return adRepo.save(newAd);
    }

    public Long deleteAd (Long id){
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

}
