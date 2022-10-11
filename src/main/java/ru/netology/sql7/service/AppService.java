package ru.netology.sql7.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.netology.sql7.entity.City;
import ru.netology.sql7.entity.Person;
import ru.netology.sql7.entity.enums.Gender;
import ru.netology.sql7.repository.CityRepository;
import ru.netology.sql7.repository.PersonRepository;

import java.util.List;

@Service
public class AppService {
    private final CityRepository cityRepository;
    private final PersonRepository personRepository;

    public AppService(CityRepository cityRepository, PersonRepository personRepository) {
        this.cityRepository = cityRepository;
        this.personRepository = personRepository;
    }

    public City readCityByName(String name) throws Exception {
        return cityRepository.readByName(name);
    }

    public List<City> readAllCityThanOrderByName() {
        return cityRepository.findAll(Sort.by("name").ascending());
    }

    public City createCity(String name) throws Exception {
        return cityRepository.createCity(name);
    }

    public List<Person> readPersonByCityName(String cityName) throws Exception {
        return personRepository.readPersonByCityName(cityName);
    }

    public List<Person> readPersonByAgeLessThanOrderByAge(int age) throws Exception {
        return personRepository.readPersonByAgeLessThanOrderByAge(age);
    }

    public List<Person> readPersonByNameAndSurname(String name, String surname) throws Exception {
        return personRepository.readPersonByNameAndSurname(name, surname);
    }

    public Person createPerson(String name, String surname, int age, String gender, String phoneNumber, String city)
            throws Exception {
        Gender probablyGender = Gender.getGender(gender);
        City probablyCity = cityRepository.readByName(city);
        return personRepository.createPerson(name, surname, age, probablyGender, phoneNumber, probablyCity);
    }

    public List<Person> readAllPersonThanOrderByAge() {
        return personRepository.findAll(Sort.by("personalData.age").ascending());
    }

    public Person deletePerson(String name, String surname, int age) throws Exception {
        return personRepository.deletePerson(name, surname, age);
    }

    public Person updatePersonsPhone(String name, String surname, int age, String phoneNumber) throws Exception {
        return personRepository.updatePersonsPhone(name, surname, age, phoneNumber);
    }

}
