package ru.netology.sql7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.sql7.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {

    // Поиск города в Базе данных
    City findByName(String name);
    default City readByName(String name) throws Exception {
        City probablyCity = findByName(name);
        if (probablyCity != null) {
            return probablyCity;
        } else throw new Exception();
    }

    // Создание и сохранение города в Базе данных
    default City createCity(String name) throws Exception {
        City probablyCity = findByName(name);
        if (probablyCity == null) {
            City city = save(City.builder().name(name).build());
            return city;
        } else throw new Exception();
    }

}
