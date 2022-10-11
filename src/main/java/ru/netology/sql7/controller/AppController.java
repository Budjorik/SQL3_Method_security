package ru.netology.sql7.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.netology.sql7.entity.City;
import ru.netology.sql7.entity.Person;
import ru.netology.sql7.service.AppService;
import ru.netology.sql7.security.SecurityConfiguration;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    // Запрос: localhost:8080/persons/city/create?name=...
    @RolesAllowed({"ROLE_WRITE"})
    @GetMapping("${endpoint-city-create}")
    public City createCity(@RequestParam("name") String cityName) {
        try {
            return appService.createCity(cityName);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "City is already saved in the DataBase!");
        }
    }

    // Запрос: localhost:8080/persons/city/read?name=...
    @Secured({"ROLE_READ"})
    @GetMapping("${endpoint-city-read}")
    public City readByName(@RequestParam("name") String cityName) {
        try {
            return appService.readCityByName(cityName);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found in the DataBase!");
        }
    }

    // Запрос: localhost:8080/persons/city/read/all
    @Secured({"ROLE_READ"})
    @GetMapping("${endpoint-city-read-all}")
    public List<City> readAllCityThanOrderByName() {
        return appService.readAllCityThanOrderByName();
    }

    // Запрос: localhost:8080/persons/person/read/by-city?cityName=...
    @Secured({"ROLE_READ"})
    @GetMapping("${endpoint-person-read-by-city}")
    public List<Person> readPersonByCityName(@RequestParam("cityName") String cityName) {
        try {
            return appService.readPersonByCityName(cityName);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found in the DataBase!");
        }
    }

    // Запрос: localhost:8080/persons/person/read/by-age/less?age=...
    @Secured({"ROLE_READ"})
    @GetMapping("${endpoint-person-read-by-age-less}")
    public List<Person> readPersonByAgeLessThanOrderByAge(@RequestParam("age") int age) {
        try {
            return appService.readPersonByAgeLessThanOrderByAge(age);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found in the DataBase!");
        }
    }

    // Запрос: localhost:8080/persons/person/read/by-name-and-surname?name=...&surname=...
    @Secured({"ROLE_READ"})
    @GetMapping("${endpoint-person-read-by-name-and-surname}")
    public List<Person> readPersonByNameAndSurname(
            @RequestParam("name") String name, @RequestParam("surname") String surname) {
        try {
            return appService.readPersonByNameAndSurname(name, surname);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found in the DataBase!");
        }
    }

    // Запрос: localhost:8080/persons/person/create?name=...&surname=...&age=...&gender=...&phoneNumber=...&city=...
    @RolesAllowed({"ROLE_WRITE"})
    @GetMapping("${endpoint-person-create}")
    public Person createPerson(
            @RequestParam("name") String name, @RequestParam("surname") String surname,
            @RequestParam("age") int age, @RequestParam("gender") String gender,
            @RequestParam("phoneNumber") String phoneNumber, @RequestParam("city") String city) {
        try {
            return appService.createPerson(name, surname, age, gender, phoneNumber, city);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Incorrect parameters specified!");
        }
    }

    // Запрос: localhost:8080/persons/person/read/all
    @Secured({"ROLE_READ"})
    @GetMapping("${endpoint-person-read-all}")
    public List<Person> readAllPersonThanOrderByAge() {
        return appService.readAllPersonThanOrderByAge();
    }

    // Запрос: localhost:8080/persons/person/delete?name=...&surname=...&age=...&userName=
    @GetMapping("${endpoint-person-delete}")
    public Person deletePerson(
            @RequestParam("name") String name, @RequestParam("surname") String surname,
            @RequestParam("age") int age, @RequestParam("userName") String userName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        if (userName.equals(currentPrincipalName)) {
            try {
                return appService.deletePerson(name, surname, age);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found in the DataBase!");
            }
        } else throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Incorrect parameters specified!");
    }

    // Запрос: localhost:8080/persons/person/update-phone?name=...&surname=...&age=...&phoneNumber=...
    @PostAuthorize("hasAnyRole('WRITE', 'DELETE')")
    @GetMapping("${endpoint-person-update-phone}")
    public Person updatePersonsPhone(
            @RequestParam("name") String name, @RequestParam("surname") String surname,
            @RequestParam("age") int age, @RequestParam("phoneNumber") String phoneNumber) {
        try {
            return appService.updatePersonsPhone(name, surname, age, phoneNumber);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found in the DataBase!");
        }
    }

}
