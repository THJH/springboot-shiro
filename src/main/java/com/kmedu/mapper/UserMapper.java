package com.kmedu.mapper;

import com.kmedu.domain.User;

public interface UserMapper {
    User findByName(String name);

    User findById(Integer id);
}
