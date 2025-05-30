package org.venti.jdbc.plugin.wrapper;

import org.venti.jdbc.plugin.wrapper.spec.Wrapper;

public interface WrapperMapper {

    Object wrap(String sql, Wrapper wrapper);

}
