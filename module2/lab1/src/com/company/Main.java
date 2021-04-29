package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static ArrayList<Polygon> parseXML(Document document) {
        NodeList polygonsList = document.getElementsByTagName("polygon");
        ArrayList<Polygon> allPolygons = new ArrayList<>();

        for (int i = 0; i < polygonsList.getLength(); i++){
            if (polygonsList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element polygonElement = (Element) polygonsList.item(i);

                Polygon newPolygon = new Polygon();
                newPolygon.setName(polygonElement.getAttribute("name"));

                NodeList vertexNodes = polygonElement.getChildNodes();
                for (int j = 0; j < vertexNodes.getLength(); j++) {
                    if (vertexNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element vertexElement = (Element) vertexNodes.item(j);

                        String name = vertexElement.getAttribute("name");

                        NodeList softwareNodes = vertexElement.getChildNodes();
                        for (int m = 0; m < softwareNodes.getLength(); m++){
                            if (softwareNodes.item(m).getNodeType() == Node.ELEMENT_NODE) {
                                Vertex newVertex = new Vertex();
                                newVertex.setName(name);
                                Element softwareElement = (Element) softwareNodes.item(m);

                                newVertex.setAngle(softwareElement.getTextContent());
                                newPolygon.addVertex(newVertex);
                            }
                        }
                    }
                }

                allPolygons.add(newPolygon);
            }
        }

        return allPolygons;
    }

    public static void soutInfo(ArrayList<Polygon> polygons) {
        for (Polygon polygon : polygons) {
            System.out.println(polygon.getName() + ":");
            for (int j = 0; j < polygon.getVertices().size(); j++) {
                System.out.println(" " + polygon.getVertices().get(j).getName() + " " + polygon.getVertices().get(j).getAngle());
            }
        }
    }

    public static void changePolygonName(ArrayList<Polygon> polygons, String polygon, String newName){
        for (Polygon value : polygons) {
            if (value.getName().equals(polygon)) {
                value.setName(newName);
            }
        }
    }

    public static void changeVertexName(ArrayList<Polygon> polygons, String polygon, String vertex, String newName){
        for (Polygon value : polygons) {
            if (value.getName().equals(polygon)) {
                for (int j = 0; j < value.getVertices().size(); j++) {
                    if (value.getVertices().get(j).getName().equals(vertex)) {
                        value.getVertices().get(j).setName(newName);
                    }
                }
            }
        }
    }

    public static void changeVertexAngle(ArrayList<Polygon> polygons, String polygon, String vertex, String newAngle){
        for (Polygon value : polygons) {
            if (value.getName().equals(polygon)) {
                for (int j = 0; j < value.getVertices().size(); j++) {
                    if (value.getVertices().get(j).getName().equals(vertex)) {
                        value.getVertices().get(j).setAngle(newAngle);
                    }
                }
            }
        }
    }

    public static void addPolygon(ArrayList<Polygon> polygons, String polygon) {
        Polygon newPolygon = new Polygon();
        newPolygon.setName(polygon);
        polygons.add(newPolygon);
    }

    public static void addVertex(ArrayList<Polygon> polygons, String polygon, String vertex, String angle) {
        for (Polygon value : polygons) {
            if (value.getName().equals(polygon)) {
                Vertex newVertex = new Vertex();
                newVertex.setName(vertex);
                newVertex.setAngle(angle);
                value.addVertex(newVertex);
            }
        }
    }

    public static void removeVertex(ArrayList<Polygon> polygons, String polygon, String vertex) {
        for (Polygon value : polygons) {
            if (value.getName().equals(polygon)) {
                for (int j = 0; j < value.getVertices().size(); j++) {
                    if (value.getVertices().get(j).getName().equals(vertex)) {
                        value.removeVertex(value.getVertices().get(j));
                    }
                }
            }
        }
    }

    public static void removePolygon(ArrayList<Polygon> polygons, String polygon) {
        for (int i = 0; i < polygons.size(); i++) {
            if (polygons.get(i).getName().equals(polygon)) {
                for (int j = 0; j < polygons.get(i).getVertices().size(); j++) {
                    removeVertex(polygons, polygon, polygons.get(i).getVertices().get(j).getName());
                }
                polygons.remove(polygons.get(i));
            }
        }
    }

    public static void createXML(ArrayList<Polygon> polygons) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc = factory.newDocumentBuilder().newDocument();

        Element list = doc.createElement("list");
        Element comp = doc.createElement("polygons");
        for (Polygon polygon : polygons) {
            Element newpolygon = doc.createElement("polygon");
            newpolygon.setAttribute("name", polygon.getName());

            for (Vertex vertex : polygon.getVertices()) {
                Element newSoftware = doc.createElement("vertex");
                newSoftware.setAttribute("name", vertex.getName());

                Element angle = doc.createElement("angle");
                angle.setTextContent(vertex.getAngle());

                newSoftware.appendChild(angle);
                newpolygon.appendChild(newSoftware);
            }
            comp.appendChild(newpolygon);
        }
        list.appendChild(comp);
        doc.appendChild(list);

        File file = new File("/home/danylott/university/course3/RO/module2/lab1/database.xml");

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(file));
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File file = new File("database.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        ArrayList<Polygon> polygons = parseXML(document);
        changePolygonName(polygons, "first", "fourth");
        changeVertexName(polygons, "fourth", "№1", "№3");
        changeVertexAngle(polygons, "fourth", "№3", "10101");
        addPolygon(polygons, "fifth");
        addVertex(polygons, "fifth", "№1", "120");
        removeVertex(polygons, "second", "№2");
        removePolygon(polygons, "third");
        soutInfo(polygons);

        createXML(polygons);
    }
}
