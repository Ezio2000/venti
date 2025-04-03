package org.venti.mybatis.meta;

import lombok.Builder;
import lombok.Data;
import org.venti.common.struc.Tuple;
import org.venti.mybatis.anno.CryptData;

import java.lang.reflect.Method;
import java.util.Collection;

@Data
@Builder
public class MapperMethodMeta {

    private String id;

    private Class<?> clazz;

    private Method method;

    private CryptData[] cryptDatas;

    private Collection<Tuple<String, String>> fieldTupleCollection;

}
