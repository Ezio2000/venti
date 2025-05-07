package org.venti.agileform.agile.mapper;

import org.venti.agileform.agile.entity.DO.CellTemplateDO;
import org.venti.jdbc.anno.Mapper;
import org.venti.jdbc.anno.Param;
import org.venti.jdbc.anno.Sql;
import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.plugin.transaction.TransactionMapper;

import java.util.List;

@Mapper
public interface CellTemplateMapper extends TransactionMapper {

    @Sql(value = "select * from cell_template where name = ? limit 1", sqlType = SqlType.QUERY, resultType = CellTemplateDO.class)
    CellTemplateDO getCellTemplateByName(@Param String name);

    @Sql(value = "select * from cell_template", sqlType = SqlType.QUERY, resultType = CellTemplateDO.class)
    List<CellTemplateDO> getAllCellTemplates();

}
