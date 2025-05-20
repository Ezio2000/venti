package org.venti.guarantee.entity.BO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Guarantee {

    private String beneficiary;

    private String guaranteedParty;

    private String projectName;

    private double guaranteeAmount;

    private LocalDateTime guaranteeDeadline;

    private String guarantor;

}
