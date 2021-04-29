package Server;

import DTO.CitizenTypeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitizenTypeDAO {
    public static List<CitizenTypeDTO> findByCityIdAndLanguage(long cityId, String language) {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT *"
                            + "FROM citizen_type "
                            + "WHERE city_id = ?"
                            + "AND language = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, cityId);
            preparedStatement.setString(2, language);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CitizenTypeDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                CitizenTypeDTO citizenType = new CitizenTypeDTO();
                citizenType.setId(resultSet.getLong(1));
                citizenType.setName(resultSet.getString(2));
                citizenType.setLanguage(resultSet.getString(3));
                citizenType.setCityId(resultSet.getLong(4));
                citizenType.setPopulation(resultSet.getLong(5));
                list.add(citizenType);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CitizenTypeDTO findOldest() {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT id, foundation_year "
                            + "FROM city "
                            + "ORDER BY foundation_year ASC "
                            + "LIMIT 1";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            long cityId = 0;
            if (resultSet.next()) {
                cityId = resultSet.getLong(1);
            }


            String sql2 = "SELECT * FROM citizen_type WHERE city_id = ?";

            Connection connection2 = DBConnection.getConnection();
            PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);
            preparedStatement2.setLong(1, cityId);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            CitizenTypeDTO citizenType = new CitizenTypeDTO();
            if (resultSet2.next()) {

                citizenType.setId(resultSet2.getLong(1));
                citizenType.setName(resultSet2.getString(2));
                citizenType.setLanguage(resultSet2.getString(3));
                citizenType.setCityId(resultSet2.getLong(4));
                citizenType.setPopulation(resultSet2.getLong(5));
            }
            preparedStatement.close();
            return citizenType;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
