package com.trade.service;


import com.trade.entity.UserEntity;
import com.trade.exception.NoUserFoundException;
import com.trade.exception.NoUsersFoundException;
import com.trade.exception.NotEnoughRights;
import com.trade.exception.UserAlreadyExistsException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {


    public List<UserEntity> getAllUsers() throws NoUsersFoundException;
    public UserEntity getUserById(Long id) throws NoUserFoundException;
    public UserEntity getUserByEmail(String email);
    public UserEntity addUser(UserEntity newUser)throws UserAlreadyExistsException;
    public Long deleteUser (Long id, Authentication authentication) throws NotEnoughRights;
    public Long deleteUser (Long id);
    public UserEntity updateUser(UserEntity newUser)throws NoUserFoundException;
    public UserEntity updateUser(UserEntity newUser, Authentication authentication)throws NoUserFoundException, NotEnoughRights;
}
