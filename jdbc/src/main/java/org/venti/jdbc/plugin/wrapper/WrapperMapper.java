package org.venti.jdbc.plugin.wrapper;

import com.fasterxml.jackson.core.type.TypeReference;

public interface WrapperMapper {

    <T> T query(Wrapper wrapper, TypeReference<T> typeRef);

    int update(Wrapper wrapper);

}
