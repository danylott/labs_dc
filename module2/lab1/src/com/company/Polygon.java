package com.company;

import java.util.ArrayList;

public class Polygon {
    private ArrayList<Vertex> vertices;
    private String name;

    public Polygon() {
        vertices = new ArrayList<>();
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public void removeVertex(Vertex vertex){
        this.vertices.removeIf(value -> value.getName().equals(vertex.getName()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
