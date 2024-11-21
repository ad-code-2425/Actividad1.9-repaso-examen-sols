package edu.repaso.xml.dom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.repaso.xml.HotelXMLHelper;
import edu.repaso.xml.modelo.Direccion;
import edu.repaso.xml.modelo.Hotel;

public class DomHelper {

    /**
     * Genera un objeto Document que representa en memoria el documento XML con los datos del parámetro hoteles
     * @param hoteles Lista de hoteles
     * @return objeto Document
     * @throws ParserConfigurationException en caso de error
     */
    public static Document generarDOM(ArrayList<Hotel> hoteles) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        DOMImplementation implementation = builder.getDOMImplementation();

        // desde el document creamos un nuevo elemento
        Document document = implementation.createDocument(null, HotelXMLHelper.HOTELES_TAG, null);
        // Obtenemos el elemento raíz
        Element root = document.getDocumentElement();

        for (Hotel hotel : hoteles) {

            // Creamos el elemento hotel
            Element eHotel = document.createElement(HotelXMLHelper.HOTEL_TAG);
            eHotel.setAttribute(HotelXMLHelper.HOTEL_ATT_ID, String.valueOf(hotel.getId()));

            addElementConTexto(document, eHotel, HotelXMLHelper.HOTEL_NOMBRE_TAG, hotel.getNombre());
            addElementConTexto(document, eHotel, HotelXMLHelper.HOTEL_TEL_TAG, String.valueOf(hotel.getTelefono()));

            // creamos el elemento direccion
            Element eDireccion = document.createElement(HotelXMLHelper.DIRECCION_TAG);

            Direccion direccion = hotel.getDireccion();

            addElementConTexto(document, eDireccion, HotelXMLHelper.DIRECCION_DETALLES_TAG,
                    direccion != null ? direccion.getDetalles() : "");
            addElementConTexto(document, eDireccion, HotelXMLHelper.DIRECCION_LAT_TAG,
                    direccion != null ? String.valueOf(direccion.getLatitud()) : "");
            addElementConTexto(document, eDireccion, HotelXMLHelper.DIRECCION_LONG_TAG,
                    direccion != null ? String.valueOf(direccion.getLongitud()) : "");

            // añadimos el documento al DOM
            root.appendChild(eHotel);
            eHotel.appendChild(eDireccion);
        }

        return document;

    }

    private static void addElementConTexto(Document document, Node padre, String tag, String text) {
        // Creamos un nuevo nodo de tipo elemento desde document
        Node node = document.createElement(tag);
        // Creamos un nuevo nodo de tipo texto también desde document
        Node nodeTexto = document.createTextNode(text);
        // añadimos a un nodo padre el nodo elemento
        padre.appendChild(node);
        // Añadimos al nodo elemento su nodo hijo de tipo texto
        node.appendChild(nodeTexto);
    }

    /**
     * Crea un fichero en la ubicación indicada pro fileDestino a partir de un objeto Document en memoria
     * @param document objeto que se transcibirá al fichero de fileDestino
     * @param fileDestino ruta al fichero de destino
     * @throws TransformerException en caso de que surja durante la operación
     */
    public static void escribirDOM(Document document, String fileDestino) throws TransformerException {
        // Para generar un documento XML con un objeto Document
        // Generar el tranformador para obtener el documento XML en un fichero
        TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
        // Espacios para indentar cada línea
        fabricaTransformador.setAttribute("indent-number", 4);
        Transformer transformador = fabricaTransformador.newTransformer();
        // Insertar saltos de línea al final de cada línea
        // https://docs.oracle.com/javase/8/docs/api/javax/xml/transform/OutputKeys.html
        transformador.setOutputProperty(OutputKeys.INDENT, "yes");

        // Si se quisiera añadir el <!DOCTYPE>:
        // transformador.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
        // docType.getSystemId());
        // El origen de la transformación es el document
        Source origen = new DOMSource(document);
        // El destino será un stream a un fichero
        Result destino = new StreamResult(fileDestino);
        transformador.transform(origen, destino);
    }

    /**
     * Lee un archivo XML de la ubicacion indicada en filePath
     * @param filePath ubicacion del archivo a leer
     * @return ArrayList<Hotel> con la representación de la información contenida en el fichero
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static ArrayList<Hotel> leerXML(String filePath) throws ParserConfigurationException, SAXException, IOException {
        Integer id = 0;
       
        String nombre;
        String telefono;
        String detalles;
        Double latitud;
        Double longitud;
        Hotel hotel = null;
        Direccion direccion = null;

        ArrayList<Hotel> hoteles = new ArrayList<>();
       

        File inputFile = new File(filePath);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        // elimina hijos con texto vacío y fusiona en un único nodo de texto varios
        // adyacentes.
        doc.getDocumentElement().normalize();

        System.out.println("Root element :"
                + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName(HotelXMLHelper.HOTEL_TAG);

        // Por cada nodo hotel:
        System.out.println("----------------------------");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eHotel = (Element) nNode;

                id = Integer.parseInt(eHotel.getAttribute(HotelXMLHelper.HOTEL_ATT_ID));
                nombre = eHotel.getElementsByTagName(HotelXMLHelper.HOTEL_NOMBRE_TAG).item(0).getTextContent();
                telefono = eHotel.getElementsByTagName(HotelXMLHelper.HOTEL_TEL_TAG).item(0).getTextContent();

                // obtenemos datos de direccion
                Element eDireccion = (Element) eHotel.getElementsByTagName(HotelXMLHelper.DIRECCION_TAG).item(0);
                detalles = eDireccion.getElementsByTagName(HotelXMLHelper.DIRECCION_DETALLES_TAG).item(0)
                        .getTextContent();
                latitud = Double.parseDouble(
                        eDireccion.getElementsByTagName(HotelXMLHelper.DIRECCION_LAT_TAG).item(0).getTextContent());
                longitud = Double.parseDouble(
                        eDireccion.getElementsByTagName(HotelXMLHelper.DIRECCION_LONG_TAG).item(0).getTextContent());

                direccion = new Direccion(detalles, latitud, longitud);
                hotel = new Hotel(id, nombre, direccion, telefono);

                hoteles.add(hotel);
            }
        }

        return hoteles;
    }

}
