package org.venti.mybatis.meta;

import lombok.Builder;
import lombok.Data;
import org.venti.common.struc.Tuple;
import org.venti.mybatis.anno.CryptData;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

@Data
@Builder
public class MapperMethodMeta {

    private String id;

    private Class<?> clazz;

    private Method method;

    private MapperMethodType mapperMethodType;

    private Collection<CryptData> cryptDataCollection;

    private Collection<Tuple<String, String>> paramTupleCollection;

    private Collection<Tuple<Field, Field>> fieldTupleCollection;

}
