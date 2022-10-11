package ru.netology.sql7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.sql7.entity.City;
import ru.netology.sql7.entity.Person;
import ru.netology.sql7.entity.PersonalData;
import ru.netology.sql7.entity.enums.Gender;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, PersonalData> {

    // Возврат списка 'Person' по названию города
    List<Person> findByCityName(String cityName);

    default List<Person> readPersonByCityName(String cityName) throws Exception {
        List<Person> persons = findByCityName(cityName);
        if (!persons.isEmpty()) {
            return persons;
        } else throw new Exception();
    }

    // Возврат списка 'Person', отсортированного по возрастанию по возрасту, младше указанного возраста
    List<Person> findByPersonalDataAgeLessThanOrderByPersonalDataAge(int age);

    default List<Person> readPersonByAgeLessThanOrderByAge(int age) throws Exception {
        List<Person> persons = findByPersonalDataAgeLessThanOrderByPersonalDataAge(age);
        if (!persons.isEmpty()) {
            return persons;
        } else throw new Exception();
    }

    // Возврат списка 'Person' по имени и фамилии
    List<Person> findByPersonalDataNameAndPersonalDataSurname(String name, String surname);

    default List<Person> readPersonByNameAndSurname(String name, String surname) throws Exception {
        List<Person> persons = findByPersonalDataNameAndPersonalDataSurname(name, surname);
        if (!persons.isEmpty()) {
            return persons;
        } else throw new Exception();
    }

    // Поиск 'Person' в Базе данных по имени, фамилии и возрасту
    Person findByPersonalData_NameAndPersonalData_SurnameAndPersonalData_Age(String name, String surname, int age);

    // Создание и сохранение 'Person' в Базе данных
    default Person createPerson(String name, String surname, int age, Gender gender, String phoneNumber, City city)
            throws Exception {
        return save(new Person(new PersonalData(name, surname, age), gender, phoneNumber, city));
    }

    // Удаление 'Person' из Базы данных
    default Person deletePerson(String name, String surname, int age) throws Exception {
        Person person = findByPersonalData_NameAndPersonalData_SurnameAndPersonalData_Age(name, surname, age);
        if (person != null) {
            delete(person);
            return person;
        } else throw new Exception();
    }

    default Person updatePersonsPhone(String name, String surname, int age, String phoneNumber) throws Exception {
        Person person = findByPersonalData_NameAndPersonalData_SurnameAndPersonalData_Age(name, surname, age);
        Person updatePerson = person;
        delete(person);
        updatePerson.setPhoneNumber(phoneNumber);
        save(updatePerson);
        return updatePerson;
    }

}