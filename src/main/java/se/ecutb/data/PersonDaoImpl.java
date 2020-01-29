package se.ecutb.data;

import se.ecutb.model.Gender;
import se.ecutb.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersonDaoImpl implements PersonDAO{
    private static final PersonDaoImpl INSTANCE;

    static {
        INSTANCE = new PersonDaoImpl();
    }

    static PersonDaoImpl getInstance(){
        return INSTANCE;
    }

    private PersonDaoImpl(){
        personList = JsonReader
                .getInstance()
                .read();
    }

    private List<Person> personList;

    @Override
    public Optional<Person> findByPersonId(final int personId) {
       return personList.stream()
               .filter(p -> p.getPersonId() == personId)
               .findFirst();
    }

    @Override
    public List<Person> findByLastName(String lastName) {
        return personList.stream()
                .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findByFirstName(String firstName) {
        return personList.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findByBirthDate(LocalDate birthDate) {
       return personList.stream()
               .filter(p -> p.getDateOfBirth().equals(birthDate))
               .collect(Collectors.toList());
    }

    @Override
    public List<Person> findByBirthDateBefore(LocalDate endDate) {
        return personList.stream()
                .filter(p -> p.getDateOfBirth().isBefore(endDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findByBirthDateAfter(LocalDate startDate) {
        return personList.stream()
                .filter(p -> p.getDateOfBirth().isAfter(startDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findByBirthDateBetween(LocalDate start, LocalDate end) {
        return personList.stream()
                .filter(p -> p.getDateOfBirth().isAfter(start) && p.getDateOfBirth().isBefore(end))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findByGender(Gender gender) {
       return personList.stream()
               .filter(p -> p.getGender() == gender)
               .collect(Collectors.toList());
    }
}
