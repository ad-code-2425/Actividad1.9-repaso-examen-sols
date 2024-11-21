package edu.repaso.xml;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.repaso.json.JacksonHelper;
import edu.repaso.xml.dom.DomHelper;
import edu.repaso.xml.modelo.Hotel;

public class Main2 {
    public static void main(String[] args) {

        String pathOrigen = Paths.get("src", "main", "resources", "hoteles.xml").toString();
        String pathDestino = Paths.get("src", "main", "resources", "hoteles.json").toString();
        ArrayList<Hotel> hoteles = new ArrayList<>();

        try {
            hoteles = DomHelper.leer(pathOrigen);

            for (Hotel hotel : hoteles) {
                System.out.println(hotel);
            }

            JacksonHelper.crearJSONFile(hoteles, pathDestino);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}