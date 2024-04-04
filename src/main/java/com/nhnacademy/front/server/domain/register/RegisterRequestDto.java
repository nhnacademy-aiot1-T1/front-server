package com.nhnacademy.front.server.domain.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 폼에서 유저가 입력한 정보를 저장하기 위해 사용하는 domain객체 입니다!
 * @author AoiTuNa
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class RegisterRequestDto {
  private String id;
  private String password;
  private String passwordRetype;
}
