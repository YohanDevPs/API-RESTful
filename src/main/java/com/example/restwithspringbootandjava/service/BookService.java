package com.example.restwithspringbootandjava.service;

import com.example.restwithspringbootandjava.controllers.BookController;
import com.example.restwithspringbootandjava.controllers.PersonController;
import com.example.restwithspringbootandjava.exeptions.RequiredObjectIsNullException;
import com.example.restwithspringbootandjava.exeptions.ResourceNotFoundException;
import com.example.restwithspringbootandjava.model.Book;
import com.example.restwithspringbootandjava.repositories.BookRepository;
import com.example.restwithspringbootandjava.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static com.example.restwithspringbootandjava.mapper.UtilMapper.parseListObjects;
import static com.example.restwithspringbootandjava.mapper.UtilMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    public static final String NO_RECORDS_MSG_ERROR = "No records found for this id";

    @Autowired
    private BookRepository repository;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public BookVO findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NO_RECORDS_MSG_ERROR));
        var vo = parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        logger.info("find one BookVO");
        return vo;
    }

    public List<BookVO> findAll() {
        logger.info("finding all people!");
        var voList = parseListObjects(repository.findAll(), BookVO.class);
        voList.stream().forEach(vo -> {
            vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        });
        return voList;
    }

    public BookVO create(BookVO book) {
        if(book == null) throw new RequiredObjectIsNullException();
        var entity = parseObject(book, Book.class);
        var vo = parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        logger.info("Creating one PersonVO");
        return vo;
    }

    public BookVO update(BookVO book) {
        if(book == null) throw new RequiredObjectIsNullException();

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException(NO_RECORDS_MSG_ERROR));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var vo = parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        logger.info("Update one BookVO");
        return vo;
    }

    public void delete(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NO_RECORDS_MSG_ERROR));
        repository.delete(entity);
    }
}
