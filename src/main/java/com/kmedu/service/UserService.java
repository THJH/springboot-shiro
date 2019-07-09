package com.kmedu.service;

import com.kmedu.domain.User;

public interface UserService {
    User findByName(String name);
    User findById(Integer id);
}
