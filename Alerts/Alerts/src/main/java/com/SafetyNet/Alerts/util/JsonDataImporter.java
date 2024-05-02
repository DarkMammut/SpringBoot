package com.SafetyNet.Alerts.util;

import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.model.PersonList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.JsonIterator;
import org.springframework.asm.TypeReference;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class JsonDataImporter {

    private static JsonNode readData(String pathFile) {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        try (InputStream inputStream = TypeReference.class.getResourceAsStream(pathFile)) {
            jsonNode = mapper.readValue(inputStream, JsonNode.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Json", e);
        }
        return jsonNode;

    }

    public static List<Person> persons() {
        String filePath = "/data/persons.json";
        JsonNode jsonNode = readData(filePath);
        PersonList persons = JsonIterator.deserialize(jsonNode.toString(), PersonList.class);
        return persons.getPersons();
    }
}