package com.trade.service;

import com.trade.entity.AdEntity;
import com.trade.entity.UserEntity;
import com.trade.exception.NoAdFoundException;
import com.trade.exception.NoUserFoundException;
import com.trade.exception.NotEnoughRights;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AdService {
    public List<AdEntity> getAllAds();
    public AdEntity getAdById(Long id) throws NoAdFoundException;
    public AdEntity addAd(Long id, AdEntity newAd, Authentication authentication) throws NoUserFoundException, NotEnoughRights;
    public Long deleteAd (Long id);
    public Long deleteAd (Long id, Authentication authentication);
    public AdEntity updateAd(AdEntity newAd);
    public AdEntity updateAd(AdEntity newAd, Authentication authentication) throws NoAdFoundException, NotEnoughRights;


}
