package com.testtask.component;


import com.testtask.model.Book;
import com.testtask.repository.Repository;


import org.springframework.stereotype.Component;


@Component
public interface BookRepository extends Repository<Book, Long> {}
