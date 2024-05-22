package com.yeoro.domain.user.model.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

import java.text.SimpleDateFormat;

import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yeoro.domain.user.model.dto.UserDto;
import com.yeoro.domain.user.model.mapper.UserMapper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Service
class UserServiceImpl implements UserService {
	@Value("${file.upload-dir}")
	private String UPLOAD_PATH;

	@Autowired
	private ServletContext servletContext;

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
	public boolean updateUser(UserDto userDto, MultipartFile file) throws Exception {
		try {
			if (file != null) {
				String saveFolder = UPLOAD_PATH + File.separator + "profile";
				File folder = new File(saveFolder);

				if (!folder.exists())
					folder.mkdirs();

				String fileName = userDto.getUserId();
				int index = file.getOriginalFilename().lastIndexOf('.');
				if (index > 0 && index < file.getOriginalFilename().length() - 1) {
					fileName += "." + file.getOriginalFilename().substring(index + 1);
				}

				Path filePath = Paths.get(saveFolder + "/" + fileName);

				// 기존 프로필 삭제
				if(Files.exists(filePath)) {
					Files.delete(filePath);
					userMapper.deletePicture(userDto.getUserId());
				}
				log.debug("filname : {}", fileName);
				Files.copy(file.getInputStream(), filePath);
				userDto.setPictureUrl(fileName);
			}
		} catch (Exception e) {
			log.error("파일 처리 도중 문제가 생겼습니다. : {}", e);
		}

		return userMapper.updateUser(userDto) > 0;
	}

	@Override
	public boolean deleteUser(String userId) throws Exception {
		return userMapper.deleteUser(userId) > 0;
	}

	@Override
	public boolean deletePicture(String userId) throws Exception {
		return userMapper.deletePicture(userId) > 0;
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