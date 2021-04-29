package com.company;

import java.util.ArrayList;

public class Polygon {
    private int id;
    private String name;
    private ArrayList<Vertex> vertices;

    public Polygon(int id, String name) {
        this.id = id;
        this.name = name;
        vertices = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public void removeVertex(Vertex vertex){
        this.vertices.removeIf(value -> value.getName().equals(vertex.getName()));
    }
}
