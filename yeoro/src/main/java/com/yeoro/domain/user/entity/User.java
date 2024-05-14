package com.yeoro.domain.user.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
@Getter
@NoArgsConstructor
public class User {
    private String id;
    private String password;
    private String nickname;
    private String picture_url;
    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime updated_at = LocalDateTime.now();;
    private String refreshToken;
}
