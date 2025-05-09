package org.venti.agileform.agile.mapper;

import org.venti.agileform.agile.entity.DO.GuaranteeVerificationDO;
import org.venti.common.constant.ValidStatus;
import org.venti.jdbc.anno.Mapper;
import org.venti.jdbc.anno.Param;
import org.venti.jdbc.anno.Sql;
import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.plugin.transaction.TransactionMapper;

@Mapper
public interface GuaranteeVerificationMapper extends TransactionMapper {

    @Sql(value = "SELECT * FROM guarantee_verification WHERE guarantee_number = ? limit 1;", sqlType = SqlType.QUERY, resultType = GuaranteeVerificationDO.class)
    GuaranteeVerificationDO getGuaranteeVerificationByNumber(@Param String guaranteeNumber);

    @Sql(value = "SELECT * FROM guarantee_verification WHERE security_code = SHA(?, 256);")
    GuaranteeVerificationDO getGuaranteeVerificationByCode(@Param String securityCode);

    @Sql("INSERT INTO guarantee " +
            "(guarantee_number, security_code, status) " +
            "VALUES " +
            "(?, SHA(?, 256), ?);")
    int addGuaranteeVerification(String guaranteeNumber, String securityCode, ValidStatus status);

}
