package com.project.hss.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String email;

    private String password;
}
