import DTO.VertexDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VertexDAO {
    public static VertexDTO findById(long id) {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT idvertices, vertexname, polygonname, angle "
                            + "FROM vertices "
                            + "WHERE idvertices = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            VertexDTO vertex = null;
            if (resultSet.next()) {
                vertex = new VertexDTO();
                vertex.setId(resultSet.getLong(1));
                vertex.setName(resultSet.getString(2));
                vertex.setPolygonName(resultSet.getLong(3));
                vertex.setAngle(resultSet.getLong(4));
            }
            preparedStatement.close();
            return vertex;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static VertexDTO findByName(String name) {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT idvertices, vertexname, polygonname, angle "
                            + "FROM vertices "
                            + "WHERE vertexname = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            VertexDTO vertex = null;
            if (resultSet.next()) {
                vertex = new VertexDTO();
                vertex.setId(resultSet.getLong(1));
                vertex.setName(resultSet.getString(2));
                vertex.setPolygonName(resultSet.getLong(3));
                vertex.setAngle(resultSet.getLong(4));
            }
            preparedStatement.close();
            return vertex;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean update(VertexDTO vertex) {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "UPDATE vertices "
                            + "SET vertexname = ?, polygonname = ?, angle = ? "
                            + "WHERE idvertices = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, vertex.getName());
            preparedStatement.setLong(2, vertex.getPolygonName());
            preparedStatement.setLong(3, vertex.getAngle());
            preparedStatement.setLong(4, vertex.getId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insert(VertexDTO vertex) {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "INSERT INTO vertices (vertexname,polygonname,angle) "
                            + "VALUES (?,?,?)";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, vertex.getName());
            preparedStatement.setLong(2, vertex.getPolygonName());
            preparedStatement.setLong(3, vertex.getAngle());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                Connection connection2 = DBConnection.getConnection();
                String sql2 = "SELECT LAST_INSERT_ID();";
                PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                if (resultSet2.next()) {
                    vertex.setId(resultSet2.getLong(1));
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

    public static boolean delete(VertexDTO vertex) {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "DELETE FROM vertices "
                            + "WHERE idvertices = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, vertex.getId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<VertexDTO> findAll() {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT idvertices, vertexname, polygonname, angle "
                            + "FROM vertices";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<VertexDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                VertexDTO vertex = new VertexDTO();
                vertex.setId(resultSet.getLong(1));
                vertex.setName(resultSet.getString(2));
                vertex.setPolygonName(resultSet.getLong(3));
                vertex.setAngle(resultSet.getLong(4));
                list.add(vertex);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<VertexDTO> findByPolygonId(Long id) {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT idvertices, vertexname, polygonname, angle "
                            + "FROM vertices "
                            + "WHERE polygonname = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<VertexDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                VertexDTO vertex = new VertexDTO();
                vertex.setId(resultSet.getLong(1));
                vertex.setName(resultSet.getString(2));
                vertex.setPolygonName(resultSet.getLong(3));
                vertex.setAngle(resultSet.getLong(4));
                list.add(vertex);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
