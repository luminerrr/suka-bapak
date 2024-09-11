package com.example.suka_bapak.repository;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.suka_bapak.entity.BookEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
//    Page<BookEntity> findAll(Pageable page);
//    @Query(value = "SELECT b.* FROM books b " +
//            "JOIN authors a ON b.author_id = a.id " +
//            "JOIN genres g ON b.genre_id = g.id " +
//            "WHERE b.title LIKE %:title% " +
//            "AND a.name LIKE %:author% " +
//            "AND g.name LIKE %:genre%", nativeQuery = true)
//    List<BookEntity> findByTitleContainingOrAuthor_NameContainingOrGenre_NameContaining(@Param("title") String title,
//                                                                                        @Param("author") String author,
//                                                                                        @Param("genre") String genre);
    boolean existsByIsbn(String isbn);}
