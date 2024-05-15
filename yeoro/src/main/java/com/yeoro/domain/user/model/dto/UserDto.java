package com.yeoro.domain.user.model.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class UserDto {
    //@Schema(description = "아이디")
    private String id;
    //@Schema(description = "비밀번호")
    private String password;
    //@Schema(description = "닉네임")
    private String nickname;
    //@Schema(description = "유저 사진")
    private String picture_url;
    //@Schema(description = "oauth 서비스명")
    private String provider_type;
    //@Schema(description = "refresh Token")
    private String refresh_token;
    //@Schema(description = "생성 일시")
    private LocalDateTime created_at = LocalDateTime.now();
    //@Schema(description = "수정 일시")
    private LocalDateTime updated_at = LocalDateTime.now();

}
