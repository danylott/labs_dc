package com.example.Servlet_OOP;

public class Vertex {
    private int id;
    private String name;
    private String angle;

    public Vertex(int id, String name, String angle) {
        this.id = id;
        this.name = name;
        this.angle = angle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
