package com.example.restwithspringbootandjava.service;

import com.example.restwithspringbootandjava.controllers.BookController;
import com.example.restwithspringbootandjava.controllers.PersonController;
import com.example.restwithspringbootandjava.exeptions.RequiredObjectIsNullException;
import com.example.restwithspringbootandjava.exeptions.ResourceNotFoundException;
import com.example.restwithspringbootandjava.model.Book;
import com.example.restwithspringbootandjava.repositories.BookRepository;
import com.example.restwithspringbootandjava.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static com.example.restwithspringbootandjava.mapper.UtilMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    public static final String NO_RECORDS_MSG_ERROR = "No records found for this id";

    @Autowired
    private BookRepository repository;
    @Autowired
    private PagedResourcesAssembler<BookVO> assembler;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public BookVO findById(Long id) {
        logger.info("find one BookVO");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NO_RECORDS_MSG_ERROR));
        var vo = parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {
        logger.info("finding pages of books!");
        var pageBook = repository.findAll(pageable);

        var bookVOsPage = pageBook.map(p -> parseObject(p, BookVO.class));

        bookVOsPage.map(p -> p.add(linkTo(methodOn(BookController.class)
                .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(BookController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc"))
                .withSelfRel();
        return assembler.toModel(bookVOsPage, link);
    }

    public BookVO create(BookVO book) {
        logger.info("Creating one PersonVO");
        if(book == null) throw new RequiredObjectIsNullException();
        var entity = parseObject(book, Book.class);
        var vo = parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book) {
        logger.info("Update one BookVO");
        if(book == null) throw new RequiredObjectIsNullException();

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException(NO_RECORDS_MSG_ERROR));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var vo = parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NO_RECORDS_MSG_ERROR));
        repository.delete(entity);
    }
}
