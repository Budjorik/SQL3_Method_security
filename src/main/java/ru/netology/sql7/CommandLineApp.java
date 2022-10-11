package ru.netology.sql7;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.netology.sql7.entity.City;
import ru.netology.sql7.entity.Person;
import ru.netology.sql7.entity.PersonalData;
import ru.netology.sql7.entity.enums.Gender;
import ru.netology.sql7.repository.CityRepository;
import ru.netology.sql7.repository.PersonRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class CommandLineApp implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {

        // Создаем города
        var cities = Stream.of("Moscow", "Novosibirsk", "Tyumen", "Kazan", "Yekaterinburg")
                .map(n -> City.builder()
                        .name(n)
                        .build()).toList();

        // Сохраняем созданные города в Базу данных
        cityRepository.saveAll(cities);

        // Создаем мужские имена
        var maleNames = List.of("Ivan", "Petr", "Oleg");

        // Создаем мужские фамилии
        var maleSurnames = List.of("Sidorov", "Dasaev", "Nechaev");

        // Создаем женские имена
        var femaleNames = List.of("Olga", "Elena", "Tatyana");

        // Создаем женские фамилии
        var femaleSurnames = List.of("Petrova", "Ivanova", "Vyalbe");

        var random = new Random();

        // Создаем и сохраняем сущность человека, полученную на основе мужчин
        IntStream.range(0, 10)
                .forEach(i -> {
                    var malePerson = Person.builder()
                            .personalData(PersonalData.builder()
                                    .name(maleNames.get(random.nextInt(maleNames.size())))
                                    .surname(maleSurnames.get(random.nextInt(maleNames.size())))
                                    .age(random.nextInt(65))
                                    .build())
                            .gender(Gender.MALE)
                            .phoneNumber(String.valueOf(random.nextLong(999_999_999)))
                            .city(cities.get(random.nextInt(cities.size())))
                            .build();

                    personRepository.save(malePerson);
                });

        // Создаем и сохраняем сущность человека, полученную на основе женщин
        IntStream.range(0, 10)
                .forEach(i -> {
                    var femalePerson = Person.builder()
                            .personalData(PersonalData.builder()
                                    .name(femaleNames.get(random.nextInt(femaleNames.size())))
                                    .surname(femaleSurnames.get(random.nextInt(femaleNames.size())))
                                    .age(random.nextInt(60))
                                    .build())
                            .gender(Gender.FEMALE)
                            .phoneNumber(String.valueOf(random.nextLong(999_999_999)))
                            .city(cities.get(random.nextInt(cities.size())))
                            .build();

                    personRepository.save(femalePerson);
                });

    }
}
