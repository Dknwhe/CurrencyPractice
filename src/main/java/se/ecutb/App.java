package se.ecutb;

import se.ecutb.model.Gender;
import se.ecutb.service.Practices;

import java.util.concurrent.ExecutionException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ExecutionException, InterruptedException {
        Practices practices = new Practices();
        //practices.findAsyncByGender(Gender.MALE).forEach(System.out::println);
        //System.out.println(practices.findAsyncById(2));
        //practices.findStringLastName("Mac").forEach(System.out::println);
        //System.out.println(practices.findPersonById(555));
        practices.findByManyNames().forEach(person -> System.out.println(person.getFirstName() + " " + person.getLastName()));

    }
}
