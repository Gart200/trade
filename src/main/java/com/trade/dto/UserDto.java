package com.trade.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trade.entity.AdEntity;
import com.trade.entity.UserEntity;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;
    private String email;
    private String phone;
    private List<AdEntity> ads;

    public UserEntity toUserEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setEmail(email);
        userEntity.setAdEntities(ads);
        return userEntity;
    }

    public static UserDto fromUserEntity(UserEntity user){
        UserDto userToUserDto = new UserDto();
        userToUserDto.setId(user.getId());
        userToUserDto.setEmail(user.getEmail());
        userToUserDto.setPhone(user.getPhone());
        userToUserDto.setAds(user.getAdEntities());
        return userToUserDto;
    }
}
