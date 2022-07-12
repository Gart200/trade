package com.trade.repository;

import com.trade.entity.AdEntity;
import com.trade.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepo extends JpaRepository<AdEntity,Long> {

    UserEntity findByUser(Long Id);

}
