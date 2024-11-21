package edu.repaso.xml.sax;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.repaso.xml.modelo.Direccion;
import edu.repaso.xml.modelo.Hotel;

public class HotelesHandler extends DefaultHandler {

    private ArrayList<Hotel> hoteles = new ArrayList();
    private Hotel hotel;
    private Direccion direccion;
    private StringBuilder buffer = new StringBuilder();



    

    public ArrayList<Hotel> getHoteles() {
        return hoteles;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        buffer.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "nombre":
                hotel.setNombre(buffer.toString());
                break;
            case "telefono":
                hotel.setTelefono(buffer.toString());
                break;
            case "detalles":
                direccion.setDetalles(buffer.toString());
                break;
            case "latitud":
                direccion.setLatitud(Double.parseDouble(buffer.toString()));
                break;
            case "longitud":
                direccion.setLongitud(Double.parseDouble(buffer.toString()));
                break;

        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {

            case "hotel":
                hotel = new Hotel();
                hoteles.add(hotel);
                hotel.setId(Integer.parseInt(attributes.getValue("id")));
                break;
            case "nombre":
            case "telefono":
            case "detalles":
            case "latitud":
            case "longitud":
                buffer.delete(0, buffer.length());
                break;
            case "direccion":
                direccion = new Direccion();
                hotel.setDireccion(direccion);
                
                break;

        }
    }
}
