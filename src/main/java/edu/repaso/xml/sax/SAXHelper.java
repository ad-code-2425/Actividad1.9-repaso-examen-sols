package edu.repaso.xml.sax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import edu.repaso.xml.modelo.Hotel;

public class SAXHelper {
   

    public static ArrayList<Hotel> leerHotelesSAX(String filePath) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        //saxParserFactory.setNamespaceAware(true);
        SAXParser saxParser = saxParserFactory.newSAXParser();
        File file = new File(filePath);
        HotelesHandler handler = new HotelesHandler();
        saxParser.parse(file, handler);

        return handler.getHoteles();

    }

    

}