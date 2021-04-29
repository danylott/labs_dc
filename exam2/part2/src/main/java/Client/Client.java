package Client;

import DTO.CitizenTypeDTO;
import DTO.CityDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private static final String split = "#";

    Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public List<CitizenTypeDTO> findCitizenTypeByCityIdAndLanguage(Long cityId, String language) {
        String query = "findCitizenTypeByCityIdAndLanguage" + split + cityId.toString() + split + language;
        out.println(query);
        String response;
        try {
            response = in.readLine();
            return getCitizenTypeDTOS(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CitizenTypeDTO> findCitizenTypeOldest() {
        String query = "findCitizenTypeOldest";
        out.println(query);
        String response;
        try {
            response = in.readLine();
            return getCitizenTypeDTOS(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CityDTO> findByTotalPopulationDetailed(Long totalPopulation) {
        String query = "findByTotalPopulationDetailed" + split + totalPopulation.toString();
        out.println(query);
        String response;
        try {
            response = in.readLine();
            return getCities(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CityDTO> findCityByCitizenTypeName(String citizenTypeName) {
        String query = "findCityByCitizenTypeName" + split + citizenTypeName;
        out.println(query);
        String response;
        try {
            response = in.readLine();
            return getCities(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<CitizenTypeDTO> getCitizenTypeDTOS(String response) {
        List<CitizenTypeDTO> list = new ArrayList<>();
        String[] fields = response.split(split);
        for (int i = 0; i < fields.length; i += 5) {
            long id = Long.parseLong(fields[i]);
            long cityId = Long.parseLong(fields[i + 1]);
            String name = fields[i + 2];
            String language = fields[i + 3];
            long population = Long.parseLong(fields[i + 4]);
            list.add(new CitizenTypeDTO(id, cityId, name, language, population));
        }
        return list;
    }

    private List<CityDTO> getCities(String response) {
        List<CityDTO> list = new ArrayList<>();
        String[] fields = response.split(split);
        for (int i = 0; i < fields.length; i += 4) {
            long id = Long.parseLong(fields[i]);
            String name = fields[i + 1];
            long foundationYear = Long.parseLong(fields[i + 2]);
            long area = Long.parseLong(fields[i + 3]);
            CityDTO city = new CityDTO();
            city.setId(id);
            city.setName(name);
            city.setFoundationYear(foundationYear);
            city.setArea(area);
            list.add(city);
        }
        return list;
    }
//
//    public void disconnect() {
//        try {
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
