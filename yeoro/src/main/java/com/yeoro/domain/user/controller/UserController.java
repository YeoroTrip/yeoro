package com.yeoro.domain.user.controller;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yeoro.domain.user.model.dto.UserDto;
import com.yeoro.domain.user.model.service.UserService;
import com.yeoro.global.config.security.jwt.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "회원 인증 컨트롤러", description = "로그인 로그아웃, 토큰처리등 회원의 인증관련 처리하는 클래스.")
public class UserController {
	private final UserService userService;
	private final JWTUtil jwtUtil;

	public UserController(UserService userService, JWTUtil jwtUtil) {
		super();
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	@Operation(summary="회원 가입", description = "회원 정보를 입력받아 user 테이블에 저장한다.")
	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> register(@RequestBody UserDto userDto) {
		Map<String, Object> resultMap = new HashMap<>();
	    HttpStatus status;
	    try {
	        boolean result = userService.addUser(userDto);
			if(result) {
				resultMap.put("message", "회원 가입 성공");
				status = HttpStatus.CREATED;
			} else {
				resultMap.put("message", "회원 가입에 실패했습니다.");
				status = HttpStatus.BAD_REQUEST;
			}
	    } catch (Exception e) {
	        log.error("회원 가입 에러 발생 : {}", e);
	        resultMap.put("message", e.getMessage());
	        status = HttpStatus.INTERNAL_SERVER_ERROR;
	    }
	    return new ResponseEntity<>(resultMap, status);
	}
	
	@Operation(summary="회원 탈퇴", description = "회원 아이디를 받아 user테이블에서 회원 정보를 삭제한다.")
	@DeleteMapping("/unregister")
	public ResponseEntity<Map<String, Object>> unregister(@RequestBody String userid) {
	    Map<String, Object> resultMap = new HashMap<>();
	    HttpStatus status;
	    try {
	        boolean result = userService.deleteUser(userid);
			if(result) {
				resultMap.put("message", "회원 탈퇴 성공");
				status = HttpStatus.NO_CONTENT;
			} else {
				resultMap.put("message", "회원 탈퇴에 실패했습니다.");
				status = HttpStatus.BAD_REQUEST;
			}
	    } catch (Exception e) {
	        log.error("회원 탈퇴 에러 발생 : {}", e);
	        resultMap.put("message", e.getMessage());
	        status = HttpStatus.INTERNAL_SERVER_ERROR;
	    }
	    return new ResponseEntity<>(resultMap, status);
	}

	@Operation(summary="내 정보 수정", description = "회원 아이디와 토큰 정보로 인증 후, 입력받은 사용자 정보로 갱신한다.")
	@PatchMapping("/my")
	public ResponseEntity<Map<String, Object>> modifyUser(@RequestBody UserDto userDto,
														  HttpServletRequest request) {
	    Map<String, Object> resultMap = new HashMap<>();
	    HttpStatus status;
	    try {

			if(jwtUtil.checkToken(request.getHeader("Authorization"))) {}
	    	boolean result = userService.updateUser(userDto);
	        if(result) {
	            resultMap.put("message", "사용자 정보가 성공적으로 수정되었습니다.");
	            status = HttpStatus.OK;
	        } else {
	            resultMap.put("message", "사용자 정보 수정에 실패했습니다.");
	            status = HttpStatus.BAD_REQUEST;
	        }
	    } catch (Exception e) {
	        log.error("회원 정보 수정 에러 발생: {}", e);
	        resultMap.put("message", "회원 정보 수정 중 서버 에러가 발생했습니다.");
	        status = HttpStatus.INTERNAL_SERVER_ERROR;
	    }
	    return new ResponseEntity<>(resultMap, status);
	}

	@Operation(summary="내 정보 조회", description = "회원 아이디와 토큰 정보로 인증 후, 사용자 정보를 DTO로 반환한다. ")
	@GetMapping("/my")
	public ResponseEntity<Map<String, Object>> getUser(@RequestParam String userid, HttpServletRequest request) {
	    Map<String, Object> resultMap = new HashMap<>();
	    HttpStatus status;

		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			try {
				UserDto userDto = userService.userInfo(userid);
				if(userDto != null) {
					resultMap.put("user", userDto);
					status = HttpStatus.OK;
				} else {
					resultMap.put("message", "사용자를 찾을 수 없습니다.");
					status = HttpStatus.NOT_FOUND;
				}
			} catch (Exception e) {
				log.error("회원 조회 에러 발생 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰입니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
	    return new ResponseEntity<>(resultMap, status);
	}

	@Operation(summary = "로그인", description = "아이디와 패스워드로 로그인을 시도합니다.")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody UserDto userDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;

		try {
			UserDto loginUser = userService.login(userDTO);
			if(loginUser != null) {

				String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());

				log.debug("refresh token : {}", refreshToken);
				log.debug("access token : {}", accessToken);

				userService.saveRefreshToken(loginUser.getUserId(), refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				status = HttpStatus.CREATED;
			} else {
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			log.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);		
	}

	@Operation(summary = "로그아웃", description = "회원 정보를 담은 Token을 제거한다.")
	@GetMapping("/logout/{userId}")
	@Hidden
	public ResponseEntity<?> removeToken(@PathVariable ("userId") String userId,
										 HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			try {
				userService.deleteRefreshToken(userId);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("로그아웃 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰입니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@Operation(summary = "회원인증", description = "회원 정보를 담은 Token을 반환한다.")
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") String userId,
			HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			try {
				UserDto userDTO = userService.userInfo(userId);
				resultMap.put("userInfo", userDTO);
				status = HttpStatus.OK;
			} catch (Exception e) {
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰입니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@Operation(summary = "Access Token 재발급", description = "만료된 access token 을 재발급 받는다.")
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDTO, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		String token = request.getHeader("refreshToken");
		log.debug("token : {}, memberDto : {}", token, userDTO);

		if (jwtUtil.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(userDTO.getUserId()))) {
				String accessToken = jwtUtil.createAccessToken(userDTO.getUserId());
				log.debug("token : {}", accessToken);
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			resultMap.put("message", "토큰 갱신에 실패했습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
}
