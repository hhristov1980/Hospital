package main.persons;

import main.hospital.Hospital;

import java.util.Objects;

public abstract class Person extends Thread{
    private PersonType type;
    protected String names;
    private String telephoneNumber;
    protected Hospital hospital;



    public Person(String names, String telephoneNumber, Hospital hospital){
        if(names.length()>0){
            this.names = names;
        }
        if(telephoneNumber.length()==10){
            this.telephoneNumber = telephoneNumber;
        }
        this.type = validateType();
        this.hospital = hospital;
    }

    protected abstract PersonType validateType();

    public enum PersonType{
        DOCTOR, NURSE, PATIENT
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return type == person.type && names.equals(person.names) && telephoneNumber.equals(person.telephoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, names, telephoneNumber);
    }

    public String getNames() {
        return names;
    }
}
