package com.example.restwithspringbootandjava.repositories;

import com.example.restwithspringbootandjava.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BookRepository extends JpaRepository<Book, Long> {
}
