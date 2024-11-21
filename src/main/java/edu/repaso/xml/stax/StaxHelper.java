package edu.repaso.xml.stax;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import edu.repaso.xml.HotelXMLHelper;
import edu.repaso.xml.modelo.Direccion;
import edu.repaso.xml.modelo.Hotel;

public class StaxHelper {

    /**
     * Escribe en un fichero pathDestino la lista de hoteles de entrada con Stax
     * @param pathDestino ruta del fichero de destino
     * @param hoteles lista de hoteles
     * @throws XMLStreamException en caso de que se genere internamente durante el proceso
     * @throws TransformerException en caso de que se genere internamente durante el proceso
     * @throws IOException en caso de que se genere internamente durante el proceso
     */
    public static void writeXmlIterator(String pathDestino, ArrayList<Hotel> hoteles)
            throws XMLStreamException, TransformerException, IOException {

        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writeToXML(out, hoteles);

        String xml = new String(out.toByteArray(), StandardCharsets.UTF_8);

        // System.out.println(formatXML(xml));
        String prettyPrintXML = formatXML(xml);

        Files.writeString(Paths.get(pathDestino),
                prettyPrintXML, StandardCharsets.UTF_8);

    }

    private static void writeToXML(OutputStream out,
            ArrayList<Hotel> hoteles) throws XMLStreamException {
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();

        // StAX Iterator API
        XMLEventWriter writer = output.createXMLEventWriter(out);

        XMLEvent event = eventFactory.createStartDocument();

        writer.add(event);

        writer.add(eventFactory.createStartElement("", "", HotelXMLHelper.HOTELES_TAG));
        for (Hotel hotel : hoteles) {

            // Vamos creando los elementos en orden:

            writer.add(eventFactory.createStartElement("", "", HotelXMLHelper.HOTEL_TAG));
            // Añadimos atributo
            writer.add(eventFactory.createAttribute(HotelXMLHelper.HOTEL_ATT_ID, String.valueOf(hotel.getId())));

            addSimpleElement(HotelXMLHelper.HOTEL_NOMBRE_TAG, hotel.getNombre(), writer, eventFactory);

            addSimpleElement(HotelXMLHelper.HOTEL_TEL_TAG, hotel.getTelefono(), writer, eventFactory);

            writer.add(eventFactory.createStartElement("", "", HotelXMLHelper.DIRECCION_TAG));

            Direccion direccion = hotel.getDireccion();
            addSimpleElement(HotelXMLHelper.DIRECCION_DETALLES_TAG, direccion.getDetalles(), writer, eventFactory);
            addSimpleElement(HotelXMLHelper.DIRECCION_LAT_TAG, String.valueOf(direccion.getLatitud()), writer,
                    eventFactory);
            addSimpleElement(HotelXMLHelper.DIRECCION_LONG_TAG, String.valueOf(direccion.getLongitud()), writer,
                    eventFactory);

            writer.add(eventFactory.createEndElement("", "", HotelXMLHelper.DIRECCION_TAG));

            writer.add(eventFactory.createEndElement("", "", HotelXMLHelper.HOTEL_TAG));
        }

        writer.add(eventFactory.createEndDocument());

        writer.flush();

        writer.close();
    }

    private static void addSimpleElement(String tag, String value, XMLEventWriter writer, XMLEventFactory eventFactory)
            throws XMLStreamException {
        writer.add(eventFactory.createStartElement("", "", tag));
        writer.add(eventFactory.createCharacters(value));
        writer.add(eventFactory.createEndElement("", "", tag));

    }

    private static String formatXML(String xml) throws TransformerException {

        // write data to xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print by indention
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // add standalone="yes", add line break before the root element
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

        StreamSource source = new StreamSource(new StringReader(xml));
        StringWriter output = new StringWriter();
        transformer.transform(source, new StreamResult(output));

        return output.toString();

    }
}
