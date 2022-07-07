package com.trade.service;

import com.trade.entity.UserEntity;

import com.trade.exception.NoUserFoundException;
import com.trade.exception.NoUsersFoundException;
import com.trade.exception.UserAlreadyExistsException;
import com.trade.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserEntity> getAllUsers() throws NoUsersFoundException {
        List<UserEntity> users = userRepo.findAll();
        if (users.isEmpty()) throw new NoUsersFoundException();

        return users;
    }

    public UserEntity getUserById(Long id) throws NoUserFoundException {
        UserEntity user = userRepo.findById(id).orElse(null);
        if (user == null) throw new NoUserFoundException(id);

        return user;
    }

    public UserEntity addUser(UserEntity newUser) throws UserAlreadyExistsException{
        UserEntity oldUser = userRepo.findByEmail(newUser.getEmail());
        if (oldUser != null) throw new UserAlreadyExistsException(oldUser.getEmail());

        return userRepo.save(newUser);
    }

    public Long deleteUser (Long id){
        userRepo.deleteById(id);
        return id;
    }

    public UserEntity updateUser(UserEntity newUser) throws NoUserFoundException {
        UserEntity oldUser = userRepo.findById(newUser.getId()).orElse(null);
        if (oldUser == null) throw new NoUserFoundException(newUser.getId());

        userRepo.save(newUser);
        return newUser;
    }

}
