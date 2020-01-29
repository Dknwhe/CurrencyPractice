package se.ecutb.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import se.ecutb.model.Person;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    //SINGLETON CODE
    private static final JsonReader INSTANCE;

    static {
        INSTANCE = new JsonReader();
    }

    public static JsonReader getInstance(){
        return INSTANCE;
    }

    //SINGLETON CODE END

    private ObjectMapper objectMapper;
    private File file;

    private JsonReader(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        file = new File("json/people.json");
    }

    public List<Person> read(){
        List<Person> people = new ArrayList<>();
        try {
            people = objectMapper.readValue(file, new TypeReference<List<Person>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return people;
    }

}
