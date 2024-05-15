package com.yeoro.domain.user.model.service;

import com.yeoro.domain.user.model.dto.UserDto;

public interface UserService {
    UserDto login(UserDto userDTO) throws Exception;
    UserDto userInfo(String id) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleteRefreshToken(String userId) throws Exception;
}
