package com.example.restwithspringbootandjava.service;

import com.example.restwithspringbootandjava.exeptions.ResourceNotFoundException;
import com.example.restwithspringbootandjava.repositories.PersonRepository;
import com.example.restwithspringbootandjava.vo.v1.PersonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonVOService.class.getName());

    public PersonVO findById(Long id) {
        logger.info("find one PersonVO");

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
    }

    public List<PersonVO> findAll() {
        return repository.findAll();
    }

    public PersonVO create(PersonVO PersonVO) {
        logger.info("Creating one PersonVO");
        return repository.save(PersonVO);
    }

    public PersonVO update(PersonVO PersonVO) {
        logger.info("Update one PersonVO");

        var entity = repository.findById(PersonVO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        entity.setFirstName(PersonVO.getFirstName());
        entity.setLastName(PersonVO.getLastName());
        entity.setAddress(PersonVO.getAddress());
        entity.setGender(PersonVO.getGender());

        return repository.save(PersonVO);
    }

    public void delete(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        repository.delete(entity);
    }
}
