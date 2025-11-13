package com.zhyrgal.docburger.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String city;
    private String phone;
    private String apartment;
    private String intercom;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

}
