package org.venti.agileform.agile.mapper;

import org.venti.agileform.agile.entity.DO.GuaranteeDO;
import org.venti.jdbc.anno.Mapper;
import org.venti.jdbc.anno.Param;
import org.venti.jdbc.anno.Sql;
import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.plugin.transaction.TransactionMapper;

import java.time.LocalDateTime;

@Mapper
public interface GuaranteeMapper extends TransactionMapper {

    @Sql(value = "SELECT * FROM guarantee WHERE guarantee_number = ? limit 1;", sqlType = SqlType.QUERY, resultType = GuaranteeDO.class)
    GuaranteeDO getGuaranteeByNumber(@Param String guaranteeNumber);

    @Sql("INSERT INTO guarantee " +
            "(guarantee_number, beneficiary, guaranteed_party, project_name, guarantee_amount, guarantee_deadline, guarantor) " +
            "VALUES " +
            "(?, ?, ?, ?, ?, ?, ?);")
    int addGuarantee(String guaranteeNumber, String beneficiary, String guaranteedParty, String projectName, double guaranteeAmount, LocalDateTime guaranteeDeadline, String guarantor);

}
