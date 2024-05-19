package com.yeoro.domain.user.model.service;

import com.yeoro.domain.user.model.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	
	boolean addUser(UserDto userDto) throws Exception;
	boolean updateUser(UserDto userDto, MultipartFile file) throws Exception;
	boolean deleteUser(String userId) throws Exception;
	boolean deletePicture(String userId) throws Exception;

	UserDto login(UserDto userDTO) throws Exception;
    UserDto userInfo(String id) throws Exception;

	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleteRefreshToken(String userId) throws Exception;
}
