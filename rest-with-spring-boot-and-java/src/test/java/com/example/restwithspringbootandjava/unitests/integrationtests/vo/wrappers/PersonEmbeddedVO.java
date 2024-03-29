package com.example.restwithspringbootandjava.unitests.integrationtests.vo.wrappers;

import com.example.restwithspringbootandjava.unitests.integrationtests.vo.PersonVO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PersonEmbeddedVO {

    @JsonProperty("personVOList")
    private List<PersonVO> persons;

    public PersonEmbeddedVO() {
    }

    public List<PersonVO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonVO> persons) {
        this.persons = persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonEmbeddedVO that)) return false;

        return getPersons() != null ? getPersons().equals(that.getPersons()) : that.getPersons() == null;
    }

    @Override
    public int hashCode() {
        return getPersons() != null ? getPersons().hashCode() : 0;
    }
}
