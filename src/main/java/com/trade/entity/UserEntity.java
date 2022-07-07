package com.trade.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class UserEntity extends BaseEntity{

    private String name;
    @Column (unique = true)
    private String email;
    @Column (unique = true)
    private String phone;
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<AdEntity> adEntities;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

}
