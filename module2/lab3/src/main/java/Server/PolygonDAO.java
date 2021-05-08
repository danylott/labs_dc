package Server;

import DTO.PolygonDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PolygonDAO {
    public static PolygonDTO findById(long id) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql =
                    "SELECT * "
                            + "FROM polygons "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            PolygonDTO polygon = null;
            if (resultSet.next()) {
                polygon = new PolygonDTO();
                polygon.setId(resultSet.getLong(1));
                polygon.setName(resultSet.getString(2));
            }
            preparedStatement.close();
            return polygon;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PolygonDTO findByName(String name) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql =
                    "SELECT * "
                            + "FROM polygons "
                            + "WHERE name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            PolygonDTO polygon = null;
            if (resultSet.next()) {
                polygon = new PolygonDTO();
                polygon.setId(resultSet.getLong(1));
                polygon.setName(resultSet.getString(2));
            }
            preparedStatement.close();
            return polygon;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean update(PolygonDTO polygon) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql =
                    "UPDATE polygons "
                            + "SET name = ? "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, polygon.getName());
            preparedStatement.setLong(2, polygon.getId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insert(PolygonDTO polygon) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql =
                    "INSERT INTO polygons (name) "
                            + "VALUES (?) ";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, polygon.getName());
            int res = preparedStatement.executeUpdate();
            if (res > 0) {
                Connection connection2 = DBConnection.getConnection();
                String sql2 = "SELECT LAST_INSERT_ID();";
                PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                if (resultSet2.next()) {
                    polygon.setId(resultSet2.getLong(1));
                }
            } else
                return false;
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(PolygonDTO polygon) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql =
                    "DELETE FROM polygons "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, polygon.getId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<PolygonDTO> findAll() {
        try (Connection connection = DBConnection.getConnection()) {
            String sql =
                    "SELECT * "
                            + "FROM polygons";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PolygonDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                PolygonDTO polygon = new PolygonDTO();
                polygon.setId(resultSet.getLong(1));
                polygon.setName(resultSet.getString(2));
                list.add(polygon);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
