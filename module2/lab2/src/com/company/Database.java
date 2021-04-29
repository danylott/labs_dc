package com.company;

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
        ArrayList<Polygon> polygons = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();){
            String sql = "select * from polygons;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = Integer.parseInt(resultSet.getString(1));
                String name = resultSet.getString(2);
                Polygon polygon = new Polygon(id, name);
                polygons.add(polygon);
                System.out.println("Get Polygon: " + name + " " + id);

                try (Connection connection1 = connectionPool.getConnection();){
                    String sql1 = "delete from polygons where id=?;";
                    PreparedStatement statement1 = connection1.prepareStatement(sql1);
                    statement1.setInt(1, id);
                    statement1.executeUpdate();
                    statement1.close();
                    connectionPool.releaseConnection(connection1);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }

            }
            statement.close();
            connectionPool.releaseConnection(connection);
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }

        for (Polygon polygon : polygons) {
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

                    System.out.println("Get vertex: (" + polygon.getName() + ") " + name + " " + id + " " + angle);

                    try (Connection connection1 = connectionPool.getConnection();){
                        String sql1 = "delete from vertices where idvertices=?;";
                        PreparedStatement statement1 = connection1.prepareStatement(sql1);
                        statement1.setInt(1, id);
                        statement1.executeUpdate();
                        statement1.close();
                        connectionPool.releaseConnection(connection1);
                    } catch (InterruptedException | SQLException e) {
                        e.printStackTrace();
                    }

                }
                statement.close();
                connectionPool.releaseConnection(connection);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
            polygon.setVertices(vertices);
        }

        return polygons;
    }

    public void setInformation(ArrayList<Polygon> polygons) {
        for (Polygon polygon : polygons) {
            try (Connection connection = connectionPool.getConnection();){
                String sql = "insert into polygons (id, name) values(?, ?);";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, 0);
                statement.setString(2, polygon.getName());
                statement.executeUpdate();

                statement.close();
                connectionPool.releaseConnection(connection);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }

            for (Vertex vertex : polygon.getVertices()) {
                try (Connection connection = connectionPool.getConnection();){
                    String sql = "insert into vertices (idvertices, polygonname, vertexname, angle) values(?, ?, ?, ?);";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, 0);
                    statement.setString(2, polygon.getName());
                    statement.setString(3, vertex.getName());
                    statement.setString(4, vertex.getAngle());
                    statement.executeUpdate();

                    statement.close();
                    connectionPool.releaseConnection(connection);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
