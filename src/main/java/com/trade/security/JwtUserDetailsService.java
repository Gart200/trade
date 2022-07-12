package com.trade.security;


import com.trade.entity.UserEntity;
import com.trade.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserServiceImpl userService;

    @Autowired
    public JwtUserDetailsService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userService.getUserByEmail(email);

        if (user == null) throw new UsernameNotFoundException("Пользователь с email: " + email + " не найден");

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("In class JwtUserDetailsService - loadUserByUsername - user with email {} successfully loaded",user.getEmail());
        return jwtUser;
    }
}
