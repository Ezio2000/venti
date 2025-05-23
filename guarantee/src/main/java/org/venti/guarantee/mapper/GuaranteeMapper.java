package org.venti.guarantee.mapper;

import org.venti.guarantee.entity.DO.GuaranteeDO;
import org.venti.jdbc.anno.VentiMapper;
import org.venti.jdbc.anno.Param;
import org.venti.jdbc.anno.Sql;
import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.plugin.transaction.TransactionMapper;
import org.venti.jdbc.typehandler.DateTimeHandler;
import org.venti.jdbc.typehandler.DoubleHandler;

import java.time.LocalDateTime;
import java.util.List;

@VentiMapper
public interface GuaranteeMapper extends TransactionMapper {

    @Sql(value = "SELECT * FROM guarantee WHERE guarantee_number = ? limit 1;", sqlType = SqlType.QUERY, resultType = GuaranteeDO.class)
    GuaranteeDO getGuaranteeByNumber(@Param String guaranteeNumber);

    @Sql("INSERT INTO guarantee " +
            "(guarantee_number, beneficiary, guaranteed_party, project_name, guarantee_amount, guarantee_deadline, guarantor) " +
            "VALUES " +
            "(?, ?, ?, ?, ?, ?, ?);")
    int addGuarantee(
            @Param String guaranteeNumber,
            @Param String beneficiary,
            @Param String guaranteedParty,
            @Param String projectName,
            @Param(typeHandler = DoubleHandler.class) double guaranteeAmount,
            @Param(typeHandler = DateTimeHandler.class) LocalDateTime guaranteeDeadline,
            @Param String guarantor
    );

    @Sql(value = "SELECT * FROM guarantee;", sqlType = SqlType.QUERY, resultType = GuaranteeDO.class)
    List<GuaranteeDO> getAllGuarantees();

}
