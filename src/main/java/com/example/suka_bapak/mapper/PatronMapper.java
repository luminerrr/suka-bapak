package com.example.suka_bapak.mapper;

import com.example.suka_bapak.dto.response.patrons.GetPatronDto;
import com.example.suka_bapak.entity.PatronEntity;
import org.springframework.stereotype.Component;

@Component
public class PatronMapper {
    public GetPatronDto toDto(PatronEntity entity) {
        if (entity == null) {
            return null;
        }

        GetPatronDto dto = new GetPatronDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setMembership_type(entity.getMembership_type());
        dto.setCreated_at(entity.getCreated_at());
        dto.setUpdated_at(entity.getUpdated_at());

        return dto;
    }

    public PatronEntity toEntity(GetPatronDto dto) {
        if (dto == null) {
            return null;
        }

        PatronEntity entity = new PatronEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setMembership_type(dto.getMembership_type());
        entity.setCreated_at(dto.getCreated_at());
        entity.setUpdated_at(dto.getUpdated_at());

        return entity;
    }
}
