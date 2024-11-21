package edu.repaso.xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import edu.repaso.json.JacksonHelper;
import edu.repaso.xml.dom.DomHelper;
import edu.repaso.xml.modelo.Hotel;
import edu.repaso.xml.stax.StaxHelper;

public class Main3 {
    public static void main(String[] args) {

        String pathOrigen = Paths.get("src", "main", "resources", "hoteles.json").toString();
        String pathDestino = Paths.get("src", "main", "resources", "hoteles.stax.xml").toString();
        ArrayList<Hotel> hoteles = new ArrayList<>();

      
            try {
                hoteles = JacksonHelper.leerJSON(pathOrigen);
                for (Hotel hotel : hoteles) {
                    System.out.println(hotel);
                }
              
                StaxHelper.writeXmlIterator(pathDestino, hoteles);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XMLStreamException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (TransformerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

          

      

    }
}