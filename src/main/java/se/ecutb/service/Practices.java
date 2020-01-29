package se.ecutb.service;

import se.ecutb.data.PersonDAO;
import se.ecutb.model.Gender;
import se.ecutb.model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class Practices {





    //Create a method that fetch one person async by id. ReturnType Optional<Person>

    /*
    Create a method that fetch many people async by lastName.
    Then apply a conversion to String of all objects. List<Person> -> List<String>
     */

    /*
    Create a method that fetch one Person async by id. Then apply extract from optional and handle exceptionally
    Optional<Person> -> Person OR Exception
     */

    /*
    Create a method that fetch async All Mikael, Mats, Sofia, and Anna. Compose all CompletableFutures to one list.
     */

    /*
    Create a method that fetch async all female, all female avarage age, and.. to be defined...
     */



    private PersonDAO dao;

    public Practices(){
        dao = PersonDAO.INSTANCE;
    }

    public List<Person> exampleMethod(String name1, String name2) throws ExecutionException, InterruptedException {
        ExecutorService service = null;
        try{
            service = Executors.newCachedThreadPool();
            CompletableFuture<List<Person>> nameFuture = CompletableFuture.supplyAsync(() -> dao.findByFirstName(name1), service);

            return CompletableFuture.supplyAsync(() -> dao.findByFirstName(name2), service)
                    .thenCombineAsync(nameFuture, this::combine, service)
                    .exceptionally(this::handle)
                    .get();
        }finally {
            if(service != null){
                service.shutdown();
            }
        }
    }

    //Create a method that fetch many people async of one gender.
    public List<Person> findAsyncByGender(Gender gender){
        List<Person> people = new ArrayList<>();
        ExecutorService service = null;
        Callable<List<Person>> find = () -> dao.findByGender(gender);
        try{
            service = Executors.newSingleThreadExecutor();
            Future<List<Person>> future = service.submit(find);
            people = future.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            if(service != null){
                service.shutdown();
            }
        }
        return people;
    }

    //Create a method that fetch one person async by id. ReturnType Optional<Person>

    public Optional<Person> findAsyncById (int personId) {
        ExecutorService service = null;
        Optional<Person> optional = Optional.ofNullable(null);
        try {
            service = Executors.newSingleThreadExecutor();
            Future<Optional<Person>> future = service.submit(() -> dao.findByPersonId(personId));
            optional = future.get(1000, TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            {
                if (service != null) {
                    service.shutdown();
                }
            }
            return optional;
        }
    }

   /*
    Create a method that fetch one Person async by id. Then apply extract from optional and handle exceptionally
    Optional<Person> -> Person OR Exception
     */

      public Person findPersonById (int personId) throws ExecutionException, InterruptedException {
          return CompletableFuture.supplyAsync(() -> dao.findByPersonId(personId)).thenApply(Optional::get)
                  .exceptionally(this::handleOn)
          .get();
      }

       /*
    Create a method that fetch async All Mikael, Mats, Sofia, and Anna. Compose all CompletableFutures to one list.
     */

       public List<Person> findByManyNames () throws ExecutionException, InterruptedException {
           CompletableFuture <List<Person>> mikael = CompletableFuture.supplyAsync(() -> dao.findByFirstName("Mikael"));
           CompletableFuture <List<Person>> mats = CompletableFuture.supplyAsync(() -> dao.findByFirstName("Mats"));
           CompletableFuture <List<Person>> sofa = CompletableFuture.supplyAsync(() -> dao.findByFirstName("Sofia"));
           return CompletableFuture.supplyAsync(() -> dao.findByFirstName("Anna"))
                   .thenCombineAsync(mikael, (list1, list2) ->combine(list1, list2))
                   .thenCombineAsync(mats, this::combine)
                   .thenCombineAsync(sofa, this::combine)
                   .exceptionally(this::handle)
                   .get();

       }


    /*
    Create a method that fetch many people async by lastName.
    Then apply a conversion to String of all objects. List<Person> -> List<String>
     */

    public List<String> findStringLastName (String findLastName) throws ExecutionException, InterruptedException {
        return  CompletableFuture.supplyAsync(() -> dao.findByFirstName(findLastName)).thenApplyAsync(list -> convert(list, Person::toString)).get();
    }

       /*
    Create a method that fetch async all female, all female average age, and.. to be defined...
     */


    private <T,R> List<R> convert(List<T> people, Function<T,R> converter){
        return people.stream()
                .map(converter)
                .collect(Collectors.toList());
    }

    private <T> List<T> combine(List<T> list1, List<T> list2){
        return Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());
    }

    private <T> List<T> handle(Throwable exception){
        System.err.println(exception);
        return new ArrayList<>();
    }

    private  <T> T handleOn(Throwable throwable) {
        System.err.println(throwable);
        return null;
    }







}
