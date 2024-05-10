package com.nhnacademy.front.server.service;

import com.nhnacademy.front.server.adapter.UserAdaptor;
import com.nhnacademy.front.server.dto.UserDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserAdaptor userAdaptor;

  public UserDetailDto getUserDetail(Long userId) {
    return userAdaptor.getUserDetail(userId).getData();
    }

}