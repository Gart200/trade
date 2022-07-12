package com.trade.repository;

import com.trade.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmailOrPhone(String email,String phone);
    UserEntity findByEmail(String email);
}
