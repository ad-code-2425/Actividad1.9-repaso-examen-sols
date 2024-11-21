package edu.repaso.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.repaso.xml.modelo.Hotel;

public class JacksonHelper {

    public static void crearJSONFile(Object object, String outputFile) throws IOException {
        var mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        String jsonString = mapper.writeValueAsString(object);

        Files.writeString(Paths.get(outputFile), jsonString);

    }

    public static ArrayList<Hotel> leerJSON(String file) throws IOException {
        StringBuffer buffer = new StringBuffer();
        ArrayList<Hotel> hoteles = new ArrayList<>();

        String line = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(file)));) {
            while (((line = reader.readLine()) != null)) {
                buffer.append(line);
            }
            String file_json_string = buffer.toString();

            var mapper = new ObjectMapper();

            hoteles = (ArrayList<Hotel>) mapper.readValue(file_json_string, new TypeReference<List<Hotel>>() {
            });
        }
        return hoteles;

    }
}
