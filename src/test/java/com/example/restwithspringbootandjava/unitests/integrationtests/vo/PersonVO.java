package com.example.restwithspringbootandjava.unitests.integrationtests.vo;

public class PersonVO {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;

    public PersonVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonVO personVO)) return false;

        if (getFirstName() != null ? !getFirstName().equals(personVO.getFirstName()) : personVO.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(personVO.getLastName()) : personVO.getLastName() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(personVO.getAddress()) : personVO.getAddress() != null)
            return false;
        return getGender() != null ? getGender().equals(personVO.getGender()) : personVO.getGender() == null;
    }

    @Override
    public int hashCode() {
        int result = getFirstName() != null ? getFirstName().hashCode() : 0;
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
        return result;
    }
}