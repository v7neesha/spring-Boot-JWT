package com.example.springboottest.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "token")
    private String token;

    @Column(name = "expiry_date")
    private Instant expiry_date;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserInfo userInfo;
}

