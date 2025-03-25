package org.venti.mybatis.dao;

import org.venti.mybatis.entity.User;

/**
 * @author Xieningjun
 * @date 2025/3/21 15:36
 * @description
 */
public interface UserMapper {
    User selectUser(int id);
    int insertUser(User user);
}
