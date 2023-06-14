package com.example.restwithspringbootandjava.unitests.service;

import com.example.restwithspringbootandjava.exeptions.RequiredObjectIsNullException;
import com.example.restwithspringbootandjava.repositories.BookRepository;
import com.example.restwithspringbootandjava.service.BookService;
import com.example.restwithspringbootandjava.unitests.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    MockBook input;
    @InjectMocks
    private BookService service;
    @Mock
    private BookRepository repository;

    @BeforeEach
    void setUpMocks() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById()  {
        var entity = input.mockEntity();
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Author name0", result.getAuthor());
        assertEquals(new Date(0), result.getLaunchDate());
        assertEquals(BigDecimal.ZERO, result.getPrice());
        assertEquals("Book title0", result.getTitle());
    }

//    @Test
//    void findAll() {
//        var list = input.mockEntityList();
//
//        when(repository.findAll()).thenReturn(list);
//
//        var books = service.findAll();
//        assertNotNull(books);
//        assertEquals(14, books.size());
//
//        var bookOne = books.get(1);
//
//        assertTrue(bookOne.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
//        assertEquals("Author name1", bookOne.getAuthor());
//        assertEquals(new Date(1), bookOne.getLaunchDate());
//        assertEquals(BigDecimal.ONE, bookOne.getPrice());
//        assertEquals("Book title1", bookOne.getTitle());
//
//        var bookEight = books.get(8);
//
//        assertTrue(bookEight.toString().contains("links: [</api/books/v1/8>;rel=\"self\"]"));
//        assertEquals("Author name8", bookEight.getAuthor());
//        assertEquals(new Date(8), bookEight.getLaunchDate());
//        assertEquals(BigDecimal.valueOf(8), bookEight.getPrice());
//        assertEquals("Book title8", bookEight.getTitle());
//    }

    @Test
    void testCreate() {
        var entity = input.mockEntity();
        var persisted = entity;

        persisted.setId(1L);

        var vo = input.mockVO();
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        System.out.println(result);
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Author name0", result.getAuthor());
        assertEquals(new Date(0), result.getLaunchDate());
        assertEquals(BigDecimal.ZERO, result.getPrice());
        assertEquals("Book title0", result.getTitle());
    }

    @Test
    void testCreateIfNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "Object null is not allowed";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdate() {
        var entity = input.mockEntity();
        var persisted = entity;

        persisted.setId(1L);

        var vo = input.mockVO();
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        System.out.println(result);
        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Author name0", result.getAuthor());
        assertEquals(new Date(0), result.getLaunchDate());
        assertEquals(BigDecimal.ZERO, result.getPrice());
        assertEquals("Book title0", result.getTitle());
    }

    @Test
    void testUpdateIfNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });

        String expectedMessage = "Object null is not allowed";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        var entity = input.mockEntity();
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.delete(1L);
    }
}
