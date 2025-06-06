package org.venti.jdbc.plugin.wrapper;

public interface WrapperMapper {

    <T> T wrap(Wrapper wrapper);

}
