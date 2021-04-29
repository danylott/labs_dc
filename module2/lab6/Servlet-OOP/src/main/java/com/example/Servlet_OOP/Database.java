package com.example.Servlet_OOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {
    private ConnectionPool connectionPool;

    public Database(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public ArrayList<Polygon> getInformation() {
        ArrayList<Polygon> companies = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();){
            String sql = "select * from polygons;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = Integer.parseInt(resultSet.getString(1));
                String name = resultSet.getString(2);
                Polygon polygon = new Polygon(id, name);
                companies.add(polygon);
            }
            statement.close();
            connectionPool.releaseConnection(connection);
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }

        for (Polygon polygon : companies) {
            ArrayList<Vertex> vertices = new ArrayList<>();
            try (Connection connection = connectionPool.getConnection();){
                String sql = "select * from vertices where polygonname=?;";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, polygon.getName());
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()){
                    int id = Integer.parseInt(resultSet.getString(1));
                    String name = resultSet.getString(3);
                    String angle = resultSet.getString(4);

                    Vertex vertex = new Vertex(id, name, angle);
                    vertices.add(vertex);
                }
                statement.close();
                connectionPool.releaseConnection(connection);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
            polygon.setVertices(vertices);
        }

        return companies;
    }
}
