package org.venti.mybatis.dao;

import org.apache.ibatis.annotations.Param;
import org.venti.mybatis.entity.User;

import java.util.List;

/**
 * @author Xieningjun
 * @date 2025/3/21 15:36
 * @description
 */
public interface UserMapper {
    User selectUser(int id);
    int insertUser(User user);
    List<Integer> selectUserIdsByNameAndAge(@Param("tarName") String name, @Param("minAge") int age);
    List<User> selectAllUsers();
    List<String> selectAllPhones();
    List<Integer> dangerSelect(String sql);
}
