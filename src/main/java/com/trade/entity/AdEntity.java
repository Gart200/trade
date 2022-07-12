package com.trade.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "ads")
public class AdEntity extends BaseEntity {

    private String title;
    private String category;
    private String subcategory;
    private int price;
    @JsonIgnore
    @ManyToOne
    private UserEntity user;


}
