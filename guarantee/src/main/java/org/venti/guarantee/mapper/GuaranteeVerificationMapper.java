package org.venti.guarantee.mapper;

import org.venti.guarantee.entity.DO.GuaranteeVerificationDO;
import org.venti.common.constant.ValidStatus;
import org.venti.jdbc.anno.VentiMapper;
import org.venti.jdbc.anno.Param;
import org.venti.jdbc.anno.Sql;
import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.plugin.transaction.TransactionMapper;
import org.venti.jdbc.typehandler.ValidStatusHandler;

import java.util.List;

@VentiMapper
public interface GuaranteeVerificationMapper extends TransactionMapper {

    @Sql("INSERT INTO guarantee_verification " +
            "(guarantee_number, security_code, status) " +
            "VALUES " +
            "(?, ?, ?);")
    int addGuaranteeVerification(
            @Param String guaranteeNumber,
            @Param String securityCode,
            @Param(typeHandler = ValidStatusHandler.class) ValidStatus validStatus
    );

    @Sql("DELETE FROM guarantee_verification WHERE security_code = ?;")
    int deleteGuaranteeVerificationByCode(@Param String securityCode);

    @Sql(value = "SELECT * FROM guarantee_verification WHERE guarantee_number = ? limit 1;", sqlType = SqlType.QUERY, resultType = GuaranteeVerificationDO.class)
    GuaranteeVerificationDO getGuaranteeVerificationByNumber(@Param String guaranteeNumber);

    @Sql(value = "SELECT * FROM guarantee_verification WHERE security_code = ? limit 1;", sqlType = SqlType.QUERY, resultType = GuaranteeVerificationDO.class)
    GuaranteeVerificationDO getGuaranteeVerificationByCode(@Param String securityCode);

    @Sql(value = "SELECT * FROM guarantee_verification;", sqlType = SqlType.QUERY, resultType = GuaranteeVerificationDO.class)
    List<GuaranteeVerificationDO> getAllGuaranteeVerifications();

}
