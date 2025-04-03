package org.venti.mybatis.dao;

import org.apache.ibatis.annotations.Param;
import org.venti.mybatis.anno.CryptData;
import org.venti.mybatis.anno.CryptMapper;
import org.venti.mybatis.entity.User;

import java.util.List;

/**
 * @author Xieningjun
 * @date 2025/3/21 15:36
 * @description
 */
@CryptMapper(entityClazz = User.class)
public interface UserMapper {
    User selectUser(int id);
    int insertUser(User user);
    int updateUser(@Param("id") int id, @CryptData(cryptField = "cryptName") @Param("newName") String name,
                   @Param("newAge") int age, @Param("cryptName") String cryptName);
    List<Integer> selectUserIdsByNameAndAge(@Param("tarName") String name, @Param("minAge") int age);
    List<User> selectAllUsers();
    List<String> selectAllPhones();
    List<Integer> dangerSelect(String sql);
    List<User> selectUsersByEntity(User user);
    List<User> selectUsersByName(@Param("name") String name);
}
