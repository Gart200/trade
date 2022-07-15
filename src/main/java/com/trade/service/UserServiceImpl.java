package com.trade.service;

import com.trade.entity.Role;
import com.trade.entity.UserEntity;

import com.trade.exception.NoUserFoundException;
import com.trade.exception.NoUsersFoundException;
import com.trade.exception.NotEnoughRights;
import com.trade.exception.UserAlreadyExistsException;
import com.trade.repository.RoleRepo;
import com.trade.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
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

    public UserEntity getUserByEmail(String email){
        UserEntity user = userRepo.findByEmail(email);

        return user;
    }

    public UserEntity addUser(UserEntity newUser) throws UserAlreadyExistsException{
        UserEntity oldUser = userRepo.findByEmailOrPhone(newUser.getEmail(), newUser.getPhone());
        if (oldUser != null) throw new UserAlreadyExistsException();

        Role role = roleRepo.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        newUser.setPassword(encoder.encode(newUser.getPassword()));
        newUser.setRoles(roles);

        return userRepo.save(newUser);

    }

    public Long deleteUser (Long id, Authentication authentication) throws NotEnoughRights {
        UserEntity sender = userRepo.findByEmail(((UserDetails) authentication.getPrincipal()).getUsername());

        if (sender.getId() != id) throw new NotEnoughRights();
        userRepo.deleteById(id);

        return id;
    }
    public Long deleteUser (Long id) {
        userRepo.deleteById(id);
        return id;
    }

    public UserEntity updateUser(UserEntity newUser, Authentication authentication) throws NoUserFoundException, NotEnoughRights {
        UserEntity oldUser = userRepo.findById(newUser.getId()).orElse(null);
        if (oldUser == null) throw new NoUserFoundException(newUser.getId());

        UserEntity sender = userRepo.findByEmail(((UserDetails) authentication.getPrincipal()).getUsername());
        if (sender.getId() != newUser.getId()) throw new NotEnoughRights();

        userRepo.save(newUser);
        return newUser;
    }

    public UserEntity updateUser(UserEntity newUser) throws NoUserFoundException {
        UserEntity oldUser = userRepo.findById(newUser.getId()).orElse(null);
        if (oldUser == null) throw new NoUserFoundException(newUser.getId());

        userRepo.save(newUser);
        return newUser;
    }

}
