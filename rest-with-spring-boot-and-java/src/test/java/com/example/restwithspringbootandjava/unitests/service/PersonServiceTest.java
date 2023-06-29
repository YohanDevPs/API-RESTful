package com.example.restwithspringbootandjava.unitests.service;

import com.example.restwithspringbootandjava.exeptions.RequiredObjectIsNullException;
import com.example.restwithspringbootandjava.model.Person;
import com.example.restwithspringbootandjava.repositories.PersonRepository;
import com.example.restwithspringbootandjava.service.PersonService;
import com.example.restwithspringbootandjava.unitests.mocks.MockPerson;
import com.example.restwithspringbootandjava.vo.PersonVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;
    @InjectMocks
    private PersonService service;
    @Mock
    private PersonRepository repository;

    @BeforeEach
    void setUpMocks() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById()  {
        Person entity = input.mockEntity();
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name Test0", result.getFirstName());
        assertEquals("Last Name Test0", result.getLastName());
        assertEquals("Address Test0", result.getAddress());
        assertEquals("Male", result.getGender());
    }

//    @Test
//    void findAll() {
//        var list = input.mockEntityList();
//
//        when(repository.findAll()).thenReturn(list);
//
//        var people = service.findAll();
//        assertNotNull(people);
//        assertEquals(14, people.size());
//
//        var personOne = people.get(1);
//
//        assertTrue(personOne.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
//        assertEquals("First Name Test1", personOne.getFirstName());
//        assertEquals("Last Name Test1", personOne.getLastName());
//        assertEquals("Address Test1", personOne.getAddress());
//        assertEquals("Female", personOne.getGender());
//
//        var personEight = people.get(8);
//
//        assertTrue(personEight.toString().contains("links: [</api/person/v1/8>;rel=\"self\"]"));
//        assertEquals("First Name Test8", personEight.getFirstName());
//        assertEquals("Last Name Test8", personEight.getLastName());
//        assertEquals("Address Test8", personEight.getAddress());
//        assertEquals("Male", personEight.getGender());
//    }

    @Test
    void testCreate() {
        var entity = input.mockEntity();
        Person persisted = entity;

        persisted.setId(1L);

        PersonVO vo = input.mockVO();
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name Test0", result.getFirstName());
        assertEquals("Last Name Test0", result.getLastName());
        assertEquals("Address Test0", result.getAddress());
        assertEquals("Male", result.getGender());
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
        Person persisted = entity;

        persisted.setId(1L);

        PersonVO vo = input.mockVO();
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name Test0", result.getFirstName());
        assertEquals("Last Name Test0", result.getLastName());
        assertEquals("Address Test0", result.getAddress());
        assertEquals("Male", result.getGender());
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
        Person entity = input.mockEntity();
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.delete(1L);
    }
}