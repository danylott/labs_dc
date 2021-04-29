package Server;

import DTO.CitizenTypeDTO;
import DTO.CityDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDAO {
    public static List<CityDTO> findByCitizenTypeName(String citizenTypeName) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql =
                    "SELECT * "
                            + "FROM city "
                            + "INNER JOIN citizen_type "
                            + "ON city.id = citizen_type.city_id "
                            + "WHERE citizen_type.name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, citizenTypeName);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CityDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                CityDTO city = new CityDTO();
                city.setId(resultSet.getLong(1));
                city.setName(resultSet.getString(2));
                city.setFoundationYear(resultSet.getLong(3));
                city.setArea(resultSet.getLong(4));
                list.add(city);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CityDTO findByTotalPopulationDetailed(long totalPopulation) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql =
                    "SELECT city.id, city.name, city.foundation_year, city.area "
                            + "FROM city "
                            + "INNER JOIN citizen_type "
                            + "ON city.id = citizen_type.city_id "
                            + "GROUP BY city.id "
                            + "HAVING SUM(citizen_type.population) = ?";

            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, totalPopulation);
            ResultSet resultSet = preparedStatement.executeQuery();
            CityDTO city = new CityDTO();
            if (resultSet.next()) {
                city.setId(resultSet.getLong(1));
                city.setName(resultSet.getString(2));
                city.setFoundationYear(resultSet.getLong(3));
                city.setArea(resultSet.getLong(4));
            }
            preparedStatement.close();

            String sql2 = "SELECT * FROM citizen_type WHERE city_id = ?";

            Connection connection2 = DBConnection.getConnection();
            PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);
            preparedStatement2.setLong(1, city.getId());
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            List<CitizenTypeDTO> citizenTypes = new ArrayList<>();
            while (resultSet2.next()) {
                CitizenTypeDTO newCitizenType = new CitizenTypeDTO();
                newCitizenType.setId(resultSet2.getLong(1));
                newCitizenType.setName(resultSet2.getString(2));
                newCitizenType.setLanguage(resultSet2.getString(3));
                newCitizenType.setCityId(resultSet2.getLong(4));
                newCitizenType.setPopulation(resultSet2.getLong(5));
                citizenTypes.add(newCitizenType);

            }
            city.setCitizenTypes(citizenTypes);
            preparedStatement.close();
            return city;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
