package com.company;

import java.util.ArrayList;

public class Main {
    public static int getNextIDPolygon(ArrayList<Polygon> polygons) {
        int id = 0;
        if (polygons != null) {
            for (Polygon polygon : polygons) {
                if (polygon.getId() >= id) { id = polygon.getId() + 1; }
            }
        }
        return id;
    }

    public static int getNextIDVertex(ArrayList<Polygon> polygons) {
        int id = 0;
        if (polygons != null) {
            for (Polygon polygon : polygons) {
                ArrayList<Vertex> vertices = polygon.getVertices();
                if (vertices != null) {
                    for (Vertex vertex : vertices) {
                        if (vertex.getId() > id) { id = vertex.getId() + 1; }
                    }
                }
            }
        }
        return id;
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
        Polygon newPolygon = new Polygon(getNextIDPolygon(polygons), polygon);
        polygons.add(newPolygon);
    }

    public static void addVertex(ArrayList<Polygon> polygons, String polygon, String vertex, String angle) {
        for (Polygon value : polygons) {
            if (value.getName().equals(polygon)) {
                Vertex newVertex = new Vertex(getNextIDVertex(polygons), vertex, angle);
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

    public static void main(String[] args) {
        ConnectionPool connectionPool = new ConnectionPool();
        Database database = new Database(connectionPool);
        ArrayList<Polygon> polygons = database.getInformation();

        for (Polygon polygon : polygons) {
            System.out.println(polygon.getName() + "{" + polygon.getId() + "}");
            for (int i = 0; i < polygon.getVertices().size(); i++) {
                System.out.println(" " + polygon.getVertices().get(i).getName() + " " + polygon.getVertices().get(i).getAngle() + " {" + polygon.getVertices().get(i).getId() + "}");
            }
        }

        changePolygonName(polygons, "First", "Fourth");
        changeVertexName(polygons, "Fourth", "num1", "num3");
        changeVertexAngle(polygons, "Fourth", "num3", "200");
        removePolygon(polygons, "Third");
        addPolygon(polygons, "Fifth");
        addVertex(polygons, "Fifth", "num1", "100");
        removeVertex(polygons, "Second", "num1");

        System.out.println("\n\n\n");

        for (Polygon polygon : polygons) {
            System.out.println(polygon.getName() + "{" + polygon.getId() + "}");
            for (int i = 0; i < polygon.getVertices().size(); i++) {
                System.out.println(" " + polygon.getVertices().get(i).getName() + " " + polygon.getVertices().get(i).getAngle() + " {" + polygon.getVertices().get(i).getId() + "}");
            }
        }

        database.setInformation(polygons);
    }
//First{1}
// num1 100 {1}
// num2 23 {2}
//Second{2}
// num1 89 {3}
// num2 123 {4}
// num3 84 {5}
//Third{3}
// num1 230 {6}
}
