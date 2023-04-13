package com.example.restwithspringbootandjava.repositories;

import com.example.restwithspringbootandjava.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
