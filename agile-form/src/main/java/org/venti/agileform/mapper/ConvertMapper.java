package org.venti.agileform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConvertMapper {

    ConvertMapper INSTANCE = Mappers.getMapper(ConvertMapper.class);

//    AddGuaranteeDTO GuaranteeRO2DTO(AddGuaranteeRO RO);

}
