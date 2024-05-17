package com.yeoro.domain.user.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yeoro.domain.user.model.dto.UserDto;
import com.yeoro.domain.user.model.mapper.UserMapper;

@Service
class UserServiceImpl implements UserService {
	
   private UserMapper userMapper;
   
   public UserServiceImpl(UserMapper userMapper) {
       super();
       this.userMapper = userMapper;
   }

	@Override
	public boolean addUser(UserDto userDto) throws Exception {
		return userMapper.insertUser(userDto) > 0;
	}

	@Override
	public boolean updateUser(UserDto userDto) throws Exception {
		return userMapper.updateUser(userDto) > 0;
	}

	@Override
	public boolean deleteUser(String userId) throws Exception {
		return userMapper.deleteUser(userId) > 0;
	}
   
	@Override
	public UserDto login(UserDto userDTO) throws Exception {
		return userMapper.login(userDTO);
	}
	@Override
	public UserDto userInfo(String userId) throws Exception {
		return userMapper.userInfo(userId);
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		userMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return userMapper.getRefreshToken(userId);
	}

	@Override
	public void deleteRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		userMapper.deleteRefreshToken(map);	
	}


}