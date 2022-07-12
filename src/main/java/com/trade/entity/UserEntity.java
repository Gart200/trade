package com.trade.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    @JsonIgnore
    private List<Role> roles;

}
