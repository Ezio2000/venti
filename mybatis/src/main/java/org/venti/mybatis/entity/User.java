package org.venti.mybatis.entity;

import lombok.Data;
import org.venti.mybatis.anno.CryptData;

/**
 * @author Xieningjun
 * @date 2025/3/21 15:36
 * @description
 */
@Data
public class User {

    private int id;

    @CryptData(cryptField = "cryptName")
    private String name;

    private int age;

    private String cryptName;

}
