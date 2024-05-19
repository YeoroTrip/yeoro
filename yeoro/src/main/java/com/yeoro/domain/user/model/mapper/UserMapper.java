package com.yeoro.domain.user.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yeoro.domain.user.model.dto.UserDto;

@Mapper
public interface UserMapper {

	int insertUser(UserDto userDto) throws SQLException;
	int updateUser(UserDto userDto) throws SQLException;
	int deleteUser(String userId) throws SQLException;
	int deletePicture(String userId) throws SQLException;

	UserDto login(UserDto userDTO)  throws SQLException;
	UserDto userInfo(String userId) throws SQLException;

	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;

}
