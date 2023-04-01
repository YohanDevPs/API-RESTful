package com.example.restwithspringbootandjava.service;

import com.example.restwithspringbootandjava.exeptions.ResourceNotFoundException;
import com.example.restwithspringbootandjava.mapper.ModelMapperUtils;
import com.example.restwithspringbootandjava.mapper.custom.PersonMapper;
import com.example.restwithspringbootandjava.model.Person;
import com.example.restwithspringbootandjava.repositories.PersonRepository;
import com.example.restwithspringbootandjava.vo.v1.PersonVO;
import com.example.restwithspringbootandjava.vo.v2.PersonVOV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonMapper mapper;

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public PersonVO findById(Long id) {
        logger.info("find one PersonVO");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        return ModelMapperUtils.parseObject(entity, PersonVO.class);
    }

    public List<PersonVO> findAll() {
        logger.info("finding all people!");
        return ModelMapperUtils.parseListObjects(repository.findAll(), PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        logger.info("Creating one PersonVO");
        var entity = ModelMapperUtils.parseObject(person, Person.class);
        var vo = ModelMapperUtils.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }


    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating one PersonVO");
        var entity = ModelMapperUtils.parseObject(person, Person.class);
        var vo = ModelMapperUtils.parseObject(repository.save(entity), PersonVOV2.class);
        return vo;
    }

//    public PersonVOV2 createV2(PersonVOV2 person) {
//        logger.info("Creating one PersonVO");
//        var entity = mapper.convertVoToEntity(person);
//        var vo = mapper.convertEntityToVo(repository.save(entity));
//        return vo;
//    }

    public PersonVO update(PersonVO person) {
        logger.info("Update one PersonVO");

        var entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = ModelMapperUtils.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    public void delete(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        repository.delete(entity);
    }
}
