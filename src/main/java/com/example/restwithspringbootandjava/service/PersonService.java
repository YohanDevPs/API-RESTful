package com.example.restwithspringbootandjava.service;

import com.example.restwithspringbootandjava.controllers.PersonController;
import com.example.restwithspringbootandjava.exeptions.RequiredObjectIsNullException;
import com.example.restwithspringbootandjava.exeptions.ResourceNotFoundException;
import com.example.restwithspringbootandjava.model.Person;
import com.example.restwithspringbootandjava.repositories.PersonRepository;
import com.example.restwithspringbootandjava.vo.PersonVO;
import jakarta.transaction.Transactional;
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
public class PersonService {

    public static final String NO_RECORDS_MSG_ERROR = "No records found for this id";
    @Autowired
    private PersonRepository repository;
    @Autowired
    private PagedResourcesAssembler<PersonVO> assembler;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public PersonVO findById(Long id)  {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NO_RECORDS_MSG_ERROR));
        var vo = parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        logger.info("find one PersonVO");
        return vo;
    }

    @Transactional
    public PersonVO disabledPerson(Long id)  {
        repository.disabledPerson(id);

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NO_RECORDS_MSG_ERROR));

        var vo = parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        logger.info("find one PersonVO");
        return vo;
    }

    public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {
        var personPage = repository.findAll(pageable);

        var personVOsPage = personPage
                .map(p -> parseObject(p, PersonVO.class));

        personVOsPage.map(p -> p.add(linkTo(methodOn(PersonController.class)
                        .findById(p.getKey())).
                        withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc"))
                .withSelfRel();

        logger.info("finding all people!");
        return assembler.toModel(personVOsPage, link);
    }

    public PersonVO create(PersonVO person) {
        if(person == null) throw new RequiredObjectIsNullException();
        var entity = parseObject(person, Person.class);
        var vo = parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        logger.info("Creating one PersonVO");
        return vo;
    }

    public PersonVO update(PersonVO person) {
        if(person == null) throw new RequiredObjectIsNullException();

        var entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException(NO_RECORDS_MSG_ERROR));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        logger.info("Update one PersonVO");
        return vo;
    }

    public void delete(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NO_RECORDS_MSG_ERROR));
        repository.delete(entity);
    }
}
