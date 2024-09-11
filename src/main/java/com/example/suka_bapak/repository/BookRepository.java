package com.example.suka_bapak.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.suka_bapak.entity.BookEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Page<BookEntity> findAll(Pageable page);
    boolean existsByIsbn(String isbn);
    @Query("SELECT b FROM BookEntity b WHERE " +
            "(LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')) OR :title IS NULL) AND " +
            "(LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%')) OR :author IS NULL)")
    List<BookEntity> findByTitleOrAuthor(@Param("title") String title, @Param("author") String author);
}
