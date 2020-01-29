package se.ecutb.data;

import se.ecutb.model.Gender;
import se.ecutb.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PersonDAO {
    PersonDAO INSTANCE = PersonDaoImpl.getInstance();

    Optional<Person> findByPersonId(final int personId);
    List<Person> findByLastName(final String lastName);
    List<Person> findByFirstName(final String firstName);
    List<Person> findByBirthDate(final LocalDate birthDate);
    List<Person> findByBirthDateBefore(final LocalDate endDate);
    List<Person> findByBirthDateAfter(final LocalDate startDate);
    List<Person> findByBirthDateBetween(final LocalDate start, final LocalDate end);
    List<Person> findByGender(Gender gender);



}
