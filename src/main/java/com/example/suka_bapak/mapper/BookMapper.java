package com.example.suka_bapak.mapper;

import org.springframework.stereotype.Component;

import com.example.suka_bapak.dto.response.books.GetBooksDto;
import com.example.suka_bapak.entity.BookEntity;

@Component
public class BookMapper {
    public GetBooksDto toDto(BookEntity entity) {
        if (entity == null) {
            return null;
        }

        GetBooksDto dto = new GetBooksDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setIsbn(entity.getIsbn());
        dto.setQuantity(entity.getQuantity());
        dto.setAvailable_copies(entity.getAvailable_copies());
        dto.setCreated_at(entity.getCreated_at());
        dto.setUpdated_at(entity.getUpdated_at());

        return dto;
    }

    public BookEntity toEntity(GetBooksDto dto) {
        if (dto == null) {
            return null;
        }

        BookEntity entity = new BookEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setIsbn(dto.getIsbn());
        entity.setQuantity(dto.getQuantity());
        entity.setAvailable_copies(dto.getAvailable_copies());
        entity.setCreated_at(dto.getCreated_at());
        entity.setUpdated_at(dto.getUpdated_at());

        return entity;
    }
}
